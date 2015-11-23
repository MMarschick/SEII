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
	private ArrayList<Piece> deadPieces;
	private ArrayList<Integer> possibleMove;
	private ArrayList<Integer> possibleTarget;
	private Alliance myAlliance=Alliance.GOOD;
	private ImageView icon;
	private Image board;

	//Konstruktor; erstellt aus einem parseString ein Bord; fügt das Board-Icon der View hinzu
	public Board(BorderPane root, String parseString)
	{
		board = new Image("Board.png"); //Spielbrett
		icon = new ImageView(board);
		this.deadPieces=new ArrayList<Piece>();
		this.possibleMove=new ArrayList<Integer>();
		this.possibleTarget=new ArrayList<Integer>();

		root.getChildren().add(getIcon()); //Aenderung, da sonst Brett ueber Pieces

		GameParser.parseBoard(parseString, felder, deadPieces);
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(felder[i][j]!=null)
				{
					Piece currPiece=felder[i][j];
					root.getChildren().add(currPiece.getIcon());
					currPiece.getIcon().setX(i*50+5);
					currPiece.getIcon().setY(j*50+5);
				}
			}
		}
	}
	
	//Überprüft ob an der Stelle x,y im Array felder ein Piece steht
	public boolean isPiece(int x, int y)
	{
		return !(felder[x][y] == null);
	}

	//Überprüft ob an der Stelle x,y im Array felder ein Piece der Fraktion des Spielers steht
	public boolean isMyPiece(int x, int y)
	{
		if(isPiece(x,y) && felder[x][y].getCharacterAlliance()==myAlliance)return true;
		else return false;
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
	public Piece[][] getFelder(){return felder;}
	public ArrayList<Piece> getDeadPieces(){return deadPieces;}
	public Alliance getMyAlliance(){return myAlliance;}
	public Image getBoard(){return board;}
	public ImageView getIcon(){return icon;}
	public Image getImage(int x, int y){return felder[x][y].getImage();}
	public ArrayList<Integer> getPossibleMove(){return possibleMove;}
	public ArrayList<Integer> getPossibleTarget(){return possibleTarget;}

	//Berechnet alle Ziele dem dem Piece an der Stelle x,y im Array Felder zur Verfügung stehen
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

	//Berechnet alle Bewegungsmöglichkeiten dem dem Piece an der Stelle x,y im Array Felder zur Verfügung stehen
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
		case 5: getKnight(x, y, possibleMove, possibleTarget);
		}
	}

	//Unterfunktion zur Berechnung der Ziele/Bewegungsmöglichkeiten des Pieces in diagonaler Richtung
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

	//Unterfunktion zur Berechnung der Ziele/Bewegungsmöglichkeiten des Pieces in gerader Richtung
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

	//Unterfunktion zur Berechnung der Ziele/Bewegungsmöglichkeiten des Knight-Pieces in diagonaler Richtung
	private void getKnight(int x, int y, ArrayList<Integer> possibleMove, ArrayList<Integer> possibleTarget)
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

	//Unterfunktion zur Berechnung der Ziele/Bewegungsmöglichkeiten des Peasant-Pieces
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

	//Gibt zurück, ob ein Koordinatenpaar in den Grenzen des Schachfeldes(9x9) liegt
	public static boolean inBoundary(int x, int y)
	{
		if(x<=8 && y<=8 && x>=0 && y>=0)return true;
		else return false;
	}
}








