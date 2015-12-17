//Führt ein Array aller Figuren auf dem Feld
//Baut das Spielfeld auf
//Enthält die Syntax zum Bewegen der Felder (wird von der View aufgerufen)

package game;

import java.util.ArrayList;

import basal.BoardBasal;
import connection.Client;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import pieces.*;
import tools.GameParser;
import tools.RectTool;

public class Board extends BoardBasal
{
	private Piece felder[][] = new Piece[9][9]; //enthaelt Pieces mit der Koordinate als Index
	private ArrayList<Integer> possibleMove;
	private ArrayList<Integer> possibleTarget;
	private static Alliance myAlliance;
	private ImageView icon;
	private Image board;
	private Pane pane = new Pane();
	
	public static void setMyAlliance(String alliance)
	{
		if(alliance.equals("GOOD")) myAlliance=Alliance.GOOD;
		else myAlliance=Alliance.EVIL;
	}
	public static Alliance getMyAlliance(){return myAlliance;}
	//Getter
	public Piece getPiece(int x, int y){return felder[x][y];}
	public Piece[][] getFelder(){return felder;}
	public Image getBoard(){return board;}
	public ImageView getIcon(){return icon;}
	public Image getImage(int x, int y){return felder[x][y].getImage();}
	public ArrayList<Integer> getPossibleMove(){return possibleMove;}
	public ArrayList<Integer> getPossibleTarget(){return possibleTarget;}
	
	//Konstruktor; erstellt aus einem parseString ein Bord; fügt das Board-Icon der View hinzu
	public Board(BorderPane root, String parseString)
	{
		
		board = new Image("boards\\Board.png"); //Spielbrett
		icon = new ImageView(board);
//		root.getChildren().add(icon); //Aenderung, da sonst Brett ueber Pieces
		this.possibleMove=new ArrayList<Integer>();
		this.possibleTarget=new ArrayList<Integer>();

//		Aufbau der Views
		GameParser.parseBoard(parseString, felder);
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				if(felder[i][j]!=null)
				{
					//PieceViews
					Piece currPiece=felder[i][j];
					root.getChildren().add(currPiece.getIcon());
					
					//AvatarViews
					root.getChildren().add(currPiece.getAvatarView());
					
					//SavageryViews
					root.getChildren().add(currPiece.getSavageryView());
					
					//PoisonViews
					root.getChildren().add(currPiece.getPoisonView());
					
					//TauntingViews
					root.getChildren().add(currPiece.getTauntingView());
					
					//FrozenViews
					root.getChildren().add(currPiece.getFreezeView());
					
					//HealthViews
					root.getChildren().add(currPiece.getHealthView());
					
					pane.getChildren().add(currPiece.getHealthLabel());
					update();
				}
			}
		}
		root.getChildren().add(pane);
	}

	public void update() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (felder[i][j] != null) {
					Piece currPiece = felder[i][j];
					System.out.println("update " + i + " " + j);
					currPiece.getIcon().setX(i * 50 + 5);
					currPiece.getIcon().setY(j * 50 + 5);
					currPiece.getAvatarView().setX(i * 50);
					currPiece.getAvatarView().setY(j * 50);
					currPiece.getSavageryView().setX(i * 50);
					currPiece.getSavageryView().setY(j * 50);
					currPiece.getPoisonView().setX(i * 50 - 5);
					currPiece.getPoisonView().setY(j * 50 + 20);
					currPiece.getTauntingView().setX(i * 50);
					currPiece.getTauntingView().setY(j * 50);
					currPiece.getFreezeView().setX(i * 50);
					currPiece.getFreezeView().setY(j * 50);
					currPiece.setHealthLabel(currPiece.getHealth());
					currPiece.getHealthView().setX(i * 50 + 25);
					currPiece.getHealthView().setY(j * 50 + 25);

					if (currPiece.getHealth() < 10) {
						currPiece.getHealthLabel().setTranslateX(i * 50 + 34);
						currPiece.getHealthLabel().setTranslateY(j * 50 + 30);
					} else {
						currPiece.getHealthLabel().setTranslateX(i * 50 + 34);
						currPiece.getHealthLabel().setTranslateY(j * 50 + 27);
					}
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
	
	//Überprüft ob an der Stelle x,y im Array felder ein Piece der Gegner-Fraktion des Spielers steht
	public boolean isEnemyPiece(int x, int y)
	{
		if(isPiece(x,y) && !(felder[x][y].getCharacterAlliance()==myAlliance))return true;
		else return false;
	}


	//Methode zum Verschieben eines Pieces im Array "felder"
	public void setPiece(int x, int y, int kx, int ky)
	{
		//PieceViews
//		felder[x][y].getIcon().setX(kx*50+5);
//		felder[x][y].getIcon().setY(ky*50+5);
		
		//weitere Views wie Poisened
		//...
		
		//Arryanpassung
		felder[kx][ky]=felder[x][y];
		felder[x][y]=null;
		
	}

	//Berechnet alle Ziele die dem Piece an der Stelle x,y im Array Felder zur Verfügung stehen
	public void calculateTargets(int x, int y)
	{

		this.possibleTarget=new ArrayList<Integer>();
		Piece currP=getPiece(x, y);
		switch(currP.getPieceT())
		{
		case PEASANT: 
			if(currP.getCharacterAlliance()==Alliance.GOOD)
			{
				if(inBoundary(x-1, y-1) && isEnemyPiece(x-1, y-1)) possibleTarget.add((x-1)*10+y-1);
				if(inBoundary(x+1, y-1) && isEnemyPiece(x+1, y-1)) possibleTarget.add((x+1)*10+y-1);
			}
			else
			{
				if(inBoundary(x-1, y+1) && isEnemyPiece(x-1, y+1)) possibleTarget.add((x-1)*10+y+1);
				if(inBoundary(x+1, y+1) && isEnemyPiece(x+1, y+1)) possibleTarget.add((x+1)*10+y+1);
			}
			break;
		case ARCHER:
			for(int i=-3;i<4;i++)
			{
				if(i==0) continue;
				if(inBoundary(x+i, y) && isEnemyPiece(x+i, y)) possibleTarget.add((x+i)*10+y);
			}
			for(int i=-3;i<4;i++)
			{
				if(i==0) continue;
				if(inBoundary(x, y+i) && isEnemyPiece(x, y+i)) possibleTarget.add((x)*10+y+i);
			}
			break;
		default:
			for(int i=-1;i<2;i++)
			{
				for(int j=-1;j<2;j++)
				{
					if(i==0 && j==0) continue;
					if(inBoundary(x+i, y+j) && isEnemyPiece(x+i, y+j)) possibleTarget.add((x+i)*10+y+j);
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

	
	public void flush(Client player) throws InterruptedException 
	{
		TurnHandling.switchWhoseTurn();
		if(player.synchGame())
		{
			Game.waitTurn(player);
			felder = new Piece[9][9];
			GameParser.parseBoard(player.getGame(), felder);
			update();
		}
		else
		{
			System.out.println("Turn nicht gewechselt");
		}
	//Alle Pieces aktualisieren:
	//check death; Statuseffekte; remaining duration; trigger effekt; check death again
	}
	
	
	
	
}








