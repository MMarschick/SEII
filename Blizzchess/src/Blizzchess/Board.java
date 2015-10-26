package Blizzchess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

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
		
		for(int j=0;j<2;j++){              //Ausgangspositionen der Pieces 
			for (int i=0;i<9;i++)
			{
				//Aufbau untere Seite
				if(j==1){pieces[i] = new Piece(Alliance.GOOD, 0);}  //Reihe: Bauern
				else {pieces[i] = new Piece(Alliance.GOOD, i+1);}   //Reihe: Andere Figuren
				root.getChildren().add(pieces[i].getIcon()); //Image von neuem Piece der Scene hinzufuegen
				
				//Image von Piece ausrichten
				pieces[i].getIcon().setX((i)*50+5);
				pieces[i].getIcon().setY((8-j)*50+5);
			
				felder[i][8-j] = pieces[i]; //Piece dem Array "felder" hinzufuegen
				
				//Aufbau obere Seite
				if(j==1){pieces[i+9] = new Piece(Alliance.EVIL, 0);}  //Reihe: Bauern
				else {pieces[i+9] = new Piece(Alliance.EVIL, i+1);}   //Reihe: Andere Figuren
				root.getChildren().add(pieces[i+9].getIcon()); //Image von neuem Piece der Scene hinzufuegen
				pieces[i+9].getIcon().setRotate(180);;
			
				//Image von Piece ausrichten
				pieces[i+9].getIcon().setX((i)*50+5);
				pieces[i+9].getIcon().setY((j)*50+5);
			
				felder[i][j] = pieces[i+9]; //Piece dem Array "felder" hinzufuegen
			}
		}
	}
	
	//Get-Methode fuer icon (Image) von Board
	public ImageView getIcon(){
		return icon;
	}
	//Get-Methode fuer icon (Image) von einem Piece
	public Image getImage(int x, int y)
	{
		return felder[x][y].getImage();
	}
	
	//Methode zum Bewegen eines Pieces auf der View und im Array "felder"
	public void setField(int x, int y, int kx, int ky){
		felder[x][y].getIcon().setX(kx*50+5);
		felder[x][y].getIcon().setY(ky*50+5);
		felder[kx][ky]=felder[x][y];
		felder[x][y]=null;
	}
	
	//Methode zum Setzen leerer Elemente im Array "felder"
	public boolean isPiece(int x, int y){
    	return !(felder[x][y] == null);
    }
}
