package Blizzchess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Board {
	Piece felder[][] = new Piece[9][9]; //enthaelt Pieces mit der Koordinate als Index
	String pathsG [] = new String [10]; //enthaelt die Images der "guten" Fraktion
	String pathsE [] = new String [10]; //enthaelt die Images der "boesen" Fraktion
	Piece pieces[] = new Piece[18];     //enthaelt die Pieces
	

	ImageView icon;
	Image board;
	
	
	public Board(BorderPane root ){
		board = new Image("board.png"); //Spielbrett
		icon = new ImageView(board);
		
		//Befuellung der Image-Arrays
		pathsG[0] = "peasant.png";
		pathsG[1] = "mountaingiant.png";
		pathsG[2] = "archer.png";
		pathsG[3] = "knight.png";
		pathsG[4] = "chimaera.png";
		pathsG[5] = "mountainking.png";
		pathsG[6] = "driad.png";
		pathsG[7] = "knight.png";
		pathsG[8] = "archer.png";
		pathsG[9] = "mountaingiant.png";
		
		pathsE[0] = "peon.png";
		pathsE[1] = "abomination.png";
		pathsE[2] = "headhunter.png";
		pathsE[3] = "wolfrider.png";
		pathsE[4] = "frostwyrm.png";
		pathsE[5] = "blademaster.png";
		pathsE[6] = "shaman.png";
		pathsE[7] = "wolfrider.png";
		pathsE[8] = "headhunter.png";
		pathsE[9] = "abomination.png";
		
		root.getChildren().add(getIcon()); //Aenderung, da sonst Brett ueber Pieces
		
		for(int j=0;j<2;j++){              //Ausgangspositionen der Pieces 
			for (int i=0;i<9;i++)
			{
				//Aufbau untere Seite
				if(j==1){pieces[i] = new Piece(pathsG[0]);}  //Reihe: Bauern
				else {pieces[i] = new Piece(pathsG[i+1]);}   //Reihe: Andere Figuren
				root.getChildren().add(pieces[i].getIcon()); //Image von neuem Piece der Scene hinzufuegen
				
				//Image von Piece ausrichten
				pieces[i].getIcon().setX((i)*50+5);
				pieces[i].getIcon().setY((8-j)*50+5);
			
				felder[i][8-j] = pieces[i]; //Piece dem Array "felder" hinzufuegen
				
				//Aufbau obere Seite
				if(j==1){pieces[i+9] = new Piece(pathsE[0]);}  //Reihe: Bauern
				else {pieces[i+9] = new Piece(pathsE[i+1]);}   //Reihe: Andere Figuren
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
