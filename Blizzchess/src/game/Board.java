//Führt ein Array aller Figuren auf dem Feld
//Baut das Spielfeld auf
//Enthält die Syntax zum Bewegen der Felder (wird von der View aufgerufen)

package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import pieces.*;

public class Board {
	Piece felder[][] = new Piece[9][9]; //enthaelt Pieces mit der Koordinate als Index
	Piece pieces[] = new Piece[18];     //enthaelt die Pieces
	
	ImageView icon;
	Image board;
	
	public Board(BorderPane root ){
		board = new Image("board.png"); //Spielbrett
		icon = new ImageView(board);
		
		root.getChildren().add(getIcon()); //Aenderung, da sonst Brett ueber Pieces
		Piece.setPaths();                  //Pfade der Images setzen
		Piece.setMovePatterns();
		
		for(int j=0;j<2;j++){              //Ausgangspositionen der Pieces 
			for (int i=0;i<9;i++)
			{
				//Aufbau untere Seite
				if(j==1){pieces[i] = new Piece(Alliance.GOOD, 0, 0);}  //Reihe: Bauern
				else {pieces[i] = new Piece(Alliance.GOOD, i+1, i+1);}   //Reihe: Andere Figuren
				root.getChildren().add(pieces[i].getIcon()); //Image von neuem Piece der Scene hinzufuegen
				
				//Image von Piece ausrichten
				pieces[i].getIcon().setX((i)*50+5);
				pieces[i].getIcon().setY((8-j)*50+5);
			
				felder[i][8-j] = pieces[i]; //Piece dem Array "felder" hinzufuegen
				
				//Aufbau obere Seite
				if(j==1){pieces[i+9] = new Piece(Alliance.EVIL, 0, 0);}  //Reihe: Bauern
				else {pieces[i+9] = new Piece(Alliance.EVIL, i+1, i+1);}   //Reihe: Andere Figuren
				root.getChildren().add(pieces[i+9].getIcon()); //Image von neuem Piece der Scene hinzufuegen
				pieces[i+9].getIcon().setRotate(180);;
			
				//Image von Piece ausrichten
				pieces[i+9].getIcon().setX((i)*50+5);
				pieces[i+9].getIcon().setY((j)*50+5);
			
				felder[i][j] = pieces[i+9]; //Piece dem Array "felder" hinzufuegen
			}
		}
	}
	
	//eigene Methode zum Berechnen der möglichen Felder(als int array[]) in Piece
	// Methode gibt dieses array zurück
	//wird benutzt von View zum zeichnen, von Board zum bewegen (movement-, attack- und abilitypattern)
	
	//Methode zum Bewegen eines Pieces auf der View und im Array "felder"
	public void setField(int x, int y, int kx, int ky){
		
		if (Math.abs(kx-x) <= felder[x][y].getMovementPattern().getX() && //Berechnung, ob Bewegung  
    		Math.abs(ky-y) <=												 //innerhalb des MovementPatterns
    			(y==7?felder[x][y].getMovementPattern().getY2():felder[x][y].getMovementPattern().getY()) && 
    		!(kx-x == 0 && ky-y == 0) &&
			felder[kx][ky]==null 
			){  
    			
				//in alle Richtungen
				if(felder[x][y].getMovementPattern().getSpecial() == 0){
					if ((kx==x || ky==y) || (Math.abs(kx-x) == Math.abs(ky-y))) //diagonal möglich, aber nicht Pferdmove
						setPiece(x,y,kx,ky);
    			}
    			
    			// Bauern-Movement
    			else if ((felder[x][y].getMovementPattern().getSpecial() == 1)){		
    				if ((y-ky)>0){
    					setPiece(x,y,kx,ky);
    				}
    			}
				
				//Diagonal
    			else if ((felder[x][y].getMovementPattern().getSpecial() == 2)){
    			
    				if((Math.abs(kx-x) == Math.abs(ky-y)))
    					setPiece(x,y,kx,ky);
    				
    			}
				
				//Straight
    			else if ((felder[x][y].getMovementPattern().getSpecial() == 3)){
        			
    				if(kx==x || ky==y)
    					setPiece(x,y,kx,ky);
    				
    			}
				
				//Pferd
    			else if ((felder[x][y].getMovementPattern().getSpecial() == 4)){
        			
    				if((Math.abs(kx-x)==1 && (Math.abs(ky-y)==2)) || ((Math.abs(kx-x)==2 && (Math.abs(ky-y)==1))))
    					setPiece(x,y,kx,ky);
    				
    			}
    				
    		}
		
	}
	
	
	
	//Methode zum Setzen leerer Elemente im Array "felder"
	public boolean isPiece(int x, int y){
    	return !(felder[x][y] == null);
    }

	
	public void setPiece(int x, int y, int kx, int ky){
		felder[x][y].getIcon().setX(kx*50+5);
		felder[x][y].getIcon().setY(ky*50+5);
		felder[kx][ky]=felder[x][y];
		felder[x][y]=null;
		
	}
	
	//Getter
	public Piece getPiece(int x, int y)	{return felder[x][y];}
	public ImageView getIcon(){return icon;}
	public Image getImage(int x, int y){return felder[x][y].getImage();
	}
}








