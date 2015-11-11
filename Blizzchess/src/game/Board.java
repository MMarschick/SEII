//Führt ein Array aller Figuren auf dem Feld
//Baut das Spielfeld auf
//Enthält die Syntax zum Bewegen der Felder (wird von der View aufgerufen)

package game;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import pieces.*;

public class Board 
{
	private Piece felder[][] = new Piece[9][9]; //enthaelt Pieces mit der Koordinate als Index
	private Piece pieces[] = new Piece[36];     //enthaelt die Pieces
	private ArrayList<Integer> possibleMove;
	private ArrayList<Integer> possibleTarget;

	private ImageView icon;
	private Image board;

	public Board(BorderPane root )
	{
		board = new Image("Board.png"); //Spielbrett
		icon = new ImageView(board);

		this.possibleMove=new ArrayList<Integer>();
		this.possibleTarget=new ArrayList<Integer>();

		root.getChildren().add(getIcon()); //Aenderung, da sonst Brett ueber Pieces
		PieceFactory piecefactory = new PieceFactory();

		for(int j=0;j<2;j++)
		{//Ausgangspositionen der Pieces 
			for (int i=0;i<9;i++)
			{
				//Aufbau untere Seite

				if(j==1)  //Reihe: Bauern
				{
					pieces[i] = piecefactory.getPiece(Alliance.GOOD, 0);
					root.getChildren().add(pieces[i].getIcon()); //Image von neuem Piece der Scene hinzufuegen	
					pieces[i].getIcon().setX((i)*50+5);		//Image von Piece ausrichten
					pieces[i].getIcon().setY((8-j)*50+5);
					felder[i][8-j] = pieces[i]; //Piece dem Array "felder" hinzufuegen
				}
				else   //Reihe: Andere Figuren
				{
					pieces[i+9] = piecefactory.getPiece(Alliance.GOOD, i+1);
					root.getChildren().add(pieces[i+9].getIcon()); //Image von neuem Piece der Scene hinzufuegen
					pieces[i+9].getIcon().setX((i)*50+5);		//Image von Piece ausrichten
					pieces[i+9].getIcon().setY((8-j)*50+5);
					felder[i][8-j] = pieces[i+9]; //Piece dem Array "felder" hinzufuegen
				}

				//Aufbau obere Seite

				if(j==1)  //Reihe: Bauern
				{
					pieces[i+18] = piecefactory.getPiece(Alliance.EVIL, 0);
					root.getChildren().add(pieces[i+18].getIcon()); //Image von neuem Piece der Scene hinzufuegen
					//pieces[i+18].getIcon().setRotate(180);	//Bild rotieren
					pieces[i+18].getIcon().setX((i)*50+5);	//Image von Piece ausrichten
					pieces[i+18].getIcon().setY((j)*50+5);
					felder[i][j] = pieces[i+18]; //Piece dem Array "felder" hinzufuegen
				}
				else   //Reihe: Andere Figuren
				{
					pieces[i+27] = piecefactory.getPiece(Alliance.EVIL, i+1);
					root.getChildren().add(pieces[i+27].getIcon()); //Image von neuem Piece der Scene hinzufuegen
					//pieces[i+27].getIcon().setRotate(180);	//Bild rotieren
					pieces[i+27].getIcon().setX((i)*50+5);	//Image von Piece ausrichten
					pieces[i+27].getIcon().setY((j)*50+5);
					felder[i][j] = pieces[i+27]; //Piece dem Array "felder" hinzufuegen
				}
			}
		}
	}

	public boolean isPiece(int x, int y)
	{
		return !(felder[x][y] == null);
	}

	//Methode zum Verschieben eines Pieces im Array "felder"
	public void setPiece(int x, int y, int kx, int ky)
	{
		felder[x][y].getIcon().setX(kx*50+5);
		felder[x][y].getIcon().setY(ky*50+5);
		felder[kx][ky]=felder[x][y];
		felder[x][y]=null;
	}

	//Getter
	public Piece getPiece(int x, int y){return felder[x][y];}
	public ImageView getIcon(){return icon;}
	public Image getImage(int x, int y){return felder[x][y].getImage();}
	public ArrayList<Integer> getPossibleMove(){return possibleMove;}
	public ArrayList<Integer> getPossibleTarget(){return possibleTarget;}

	public void calculateTargets(int x, int y)
	{

		this.possibleTarget=new ArrayList<Integer>();
		Piece currP=getPiece(x, y);
		switch(currP.getPieceT())
		{
		case PEASANT: 
			if(currP.getCharacterAlliance()==Alliance.GOOD)
			{
				if(inBoundary(x-1, y-1) && isPiece(x-1, y-1)) possibleTarget.add((x-1)*10+y-1);
				if(inBoundary(x+1, y-1) && isPiece(x+1, y-1)) possibleTarget.add((x+1)*10+y-1);
			}
			else
			{
				if(inBoundary(x-1, y+1) && isPiece(x-1, y+1)) possibleTarget.add((x-1)*10+y+1);
				if(inBoundary(x+1, y+1) && isPiece(x+1, y+1)) possibleTarget.add((x+1)*10+y+1);
			}
			break;
		case ARCHER:
			for(int i=-3;i<4;i++)
			{
					if(i==0) continue;
					if(inBoundary(x+i, y) && isPiece(x+i, y)) possibleTarget.add((x+i)*10+y);
			}
			for(int i=-3;i<4;i++)
			{
					if(i==0) continue;
					if(inBoundary(x, y+i) && isPiece(x, y+i)) possibleTarget.add((x)*10+y+i);
			}
		default:
			for(int i=-1;i<2;i++)
			{
				for(int j=-1;j<2;j++)
				{
					if(i==0 && j==0) continue;
					if(inBoundary(x+i, y+j) && isPiece(x+i, y+j)) possibleTarget.add((x+i)*10+y+j);
				}
			}
		}
	}

	public void calculateMovement(int x, int y)
	{
		this.possibleMove=new ArrayList<Integer>();
		PieceType pieceT = getPiece(x, y).getPieceT();	//Bauer
		switch(pieceT.getDirections())
		{
		//1. Bauer 2. Nur gerade Linien 3. Nur diagonale Linien 4. Alle Richtungen 5. Springer
		case 1: getPeasant(x, y, possibleMove, possibleTarget, pieceT); break;
		case 4: getStraight(x, y, possibleMove, possibleTarget, pieceT);
		case 3: getDiagonal(x, y, possibleMove, possibleTarget, pieceT); break;
		case 2:	getStraight(x, y, possibleMove, possibleTarget, pieceT); break;
		case 5: getKnight(x, y, possibleMove, possibleTarget, pieceT);
		}
	}

	private void getDiagonal(int x, int y, ArrayList<Integer> possibleMove, ArrayList<Integer> possibleTarget, PieceType pieceT)
	{
		int range = pieceT.getRange();
		//diagonal
		for(int i=1;i<=range;i++)
		{
			if (inBoundary(x+i, y+i))
			{
				if(!isPiece(x+i, y+i))
				{
					possibleMove.add((x+i)*10+(y+i));
				}
				else
					if(!(pieceT==PieceType.QUEEN))
					{
						break;
					}

			}
		}
		for(int i=1;i<=range;i++)
		{
			if (inBoundary(x-i, y+i))
			{
				if(!isPiece(x-i, y+i))
				{
					possibleMove.add((x-i)*10+(y+i));
				}
				else
					if(!(pieceT==PieceType.QUEEN))
					{
						break;
					}
			}
		}
		for(int i=1;i<=range;i++)
		{
			if (inBoundary(x+i, y-i))
			{
				if(!isPiece(x+i, y-i))
				{
					possibleMove.add((x+i)*10+(y-i));
				}
				else
					if(!(pieceT==PieceType.QUEEN))
					{
						break;
					}
			}
		}
		for(int i=1;i<=range;i++)
		{
			if (inBoundary(x-i, y-i))
			{
				if(!isPiece(x-i, y-i))
				{
					possibleMove.add((x-i)*10+(y-i));
				}
				else
					if(!(pieceT==PieceType.QUEEN))
					{
						break;
					}
			}
		}
	}

	private void getStraight(int x, int y, ArrayList<Integer> possibleMove, ArrayList<Integer> possibleTarget, PieceType pieceT)
	{
		int range = pieceT.getRange();

		//in x Richtung
		for(int i=1;i<=range;i++)
		{
			if (inBoundary(x+i, y))
			{
				if(!isPiece(x+i, y))
				{
					possibleMove.add((x+i)*10+y);
				}
				else
					if(!(pieceT==PieceType.QUEEN))
					{
						break;
					}
			}
		}
		for(int i=1;i<=range;i++)
		{
			if (inBoundary(x-i, y))
			{
				if(!isPiece(x-i, y))
				{
					possibleMove.add((x-i)*10+y);
				}
				else
					if(!(pieceT==PieceType.QUEEN))
					{
						break;
					}
			}
		}

		//in y Richtung
		for(int i=1;i<=range;i++)
		{
			if (inBoundary(x, y+i))
			{
				if(!isPiece(x, y+i))
				{
					possibleMove.add(x*10+(y+i));
				}
				else
					if(!(pieceT==PieceType.QUEEN))
					{
						break;
					}
			}
		}
		for(int i=1;i<=range;i++)
		{
			if (inBoundary(x, y-i))
			{
				if(!isPiece(x, y-i))
				{
					possibleMove.add(x*10+(y-i));
				}
				else
					if(!(pieceT==PieceType.QUEEN))
					{
						break;
					}
			}
		}
	}

	private void getKnight(int x, int y, ArrayList<Integer> possibleMove, ArrayList<Integer> possibleTarget, PieceType pieceT)
	{
		if(inBoundary(x+2, y+1))
		{
			if(!isPiece(x+2, y+1))
			{
				possibleMove.add((x+2)*10+y+1);
			}
		}
		if(inBoundary(x+2, y-1))
		{
			if(!isPiece(x+2, y-1))
			{
				possibleMove.add((x+2)*10+y-1);
			}
		}
		if(inBoundary(x-2, y+1))
		{
			if(!isPiece(x-2, y+1))
			{
				possibleMove.add((x-2)*10+y+1);
			}
		}
		if(inBoundary(x-2, y-1))
		{
			if(!isPiece(x-2, y-1))
			{
				possibleMove.add((x-2)*10+y-1);
			}
		}
		if(inBoundary(x+1, y+2))
		{
			if(!isPiece(x+1, y+2))
			{
				possibleMove.add((x+1)*10+y+2);
			}
		}
		if(inBoundary(x+1, y-2))
		{
			if(!isPiece(x+1, y-2))
			{
				possibleMove.add((x+1)*10+y-2);
			}
		}
		if(inBoundary(x-1, y+2))
		{
			if(!isPiece(x-1, y+2))
			{
				possibleMove.add((x-1)*10+y+2);
			}
		}
		if(inBoundary(x-1, y-2))
		{
			if(!isPiece(x-1, y-2))
			{
				possibleMove.add((x-1)*10+y-2);
			}
		}
	}

	private void getPeasant(int x, int y, ArrayList<Integer> possibleMove, ArrayList<Integer> possibleTarget, PieceType pieceT)
	{
		Alliance all=getPiece(x,y).getCharacterAlliance();

		if(all==Alliance.GOOD)
		{
			if(inBoundary(x, y-1) && !isPiece(x, y-1))
			{
				possibleMove.add((x*10)+(y-1));
			}
			else
			{
				//figurentausch am brettende
				return;
			}
			if(y==7)
			{
				if(!isPiece(x, y-2))
				{
					possibleMove.add((x*10)+(y-2));
				}
			}
		}
		else
		{
 			if(inBoundary(x, y+1) && !isPiece(x, y+1))
			{
				possibleMove.add((x*10)+(y+1));
			}
			else
			{
				//figurentausch am brettende
				return;
			}
			if(y==1)
			{
				if(!isPiece(x, y+2))
				{
					possibleMove.add((x*10)+(y+2));
				}
			}
		}
	}

	public static boolean inBoundary(int x, int y)
	{
		if(x<=8 && y<=8 && x>=0 && y>=0)return true;
		else return false;
	}
}








