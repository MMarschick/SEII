package Blizzchess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Board {
	Piece felder[][] = new Piece[9][9];
	String pathsG [] = new String [10];
	String pathsE [] = new String [10];
	Piece pieces[] = new Piece[18];
	

	ImageView icon;
	Image board;
	
	
	public Board(BorderPane root ){
		board = new Image("file:D:\\SEII\\Blizzchess\\Pictures\\board.png");
		icon = new ImageView(board);
		
		pathsG[0] = "file:D:\\SEII\\Blizzchess\\Pictures\\peasant.png";
		pathsG[1] = "file:D:\\SEII\\Blizzchess\\Pictures\\mountaingiant.png";
		pathsG[2] = "file:D:\\SEII\\Blizzchess\\Pictures\\archer.png";
		pathsG[3] = "file:D:\\SEII\\Blizzchess\\Pictures\\knight.png";
		pathsG[4] = "file:D:\\SEII\\Blizzchess\\Pictures\\chimaera.png";
		pathsG[5] = "file:D:\\SEII\\Blizzchess\\Pictures\\mountainking.png";
		pathsG[6] = "file:D:\\SEII\\Blizzchess\\Pictures\\driad.png";
		pathsG[7] = "file:D:\\SEII\\Blizzchess\\Pictures\\knight.png";
		pathsG[8] = "file:D:\\SEII\\Blizzchess\\Pictures\\archer.png";
		pathsG[9] = "file:D:\\SEII\\Blizzchess\\Pictures\\mountaingiant.png";
		
		pathsE[0] = "file:D:\\SEII\\Blizzchess\\Pictures\\peon.png";
		pathsE[1] = "file:D:\\SEII\\Blizzchess\\Pictures\\abomination.png";
		pathsE[2] = "file:D:\\SEII\\Blizzchess\\Pictures\\headhunter.png";
		pathsE[3] = "file:D:\\SEII\\Blizzchess\\Pictures\\wolfrider.png";
		pathsE[4] = "file:D:\\SEII\\Blizzchess\\Pictures\\frostwyrm.png";
		pathsE[5] = "file:D:\\SEII\\Blizzchess\\Pictures\\blademaster.png";
		pathsE[6] = "file:D:\\SEII\\Blizzchess\\Pictures\\shaman.png";
		pathsE[7] = "file:D:\\SEII\\Blizzchess\\Pictures\\wolfrider.png";
		pathsE[8] = "file:D:\\SEII\\Blizzchess\\Pictures\\headhunter.png";
		pathsE[9] = "file:D:\\SEII\\Blizzchess\\Pictures\\abomination.png";
		
		root.getChildren().add(getIcon()); //Aenderung, da sonst Brett ueber Pieces
		
		for(int j=0;j<2;j++){
			for (int i=0;i<9;i++)
			{
				if(j==1){pieces[i] = new Piece(pathsG[0]);}
				else {pieces[i] = new Piece(pathsG[i+1]);}
				root.getChildren().add(pieces[i].getIcon());
			
				pieces[i].getIcon().setX((i)*50+5);
				pieces[i].getIcon().setY((8-j)*50+5);
			
				felder[i][8-j] = pieces[i];
				
				
				if(j==1){pieces[i+9] = new Piece(pathsE[0]);}
				else {pieces[i+9] = new Piece(pathsE[i+1]);}
				root.getChildren().add(pieces[i+9].getIcon());
				pieces[i+9].getIcon().setRotate(180);;
			
				pieces[i+9].getIcon().setX((i)*50+5);
				pieces[i+9].getIcon().setY((j)*50+5);
			
				felder[i][j] = pieces[i+9];
			}
		}
	}
	
	public ImageView getIcon(){
		return icon;
	}
	
	public void setField(int x, int y, int kx, int ky){
		felder[x][y].getIcon().setX(kx*50+5);
		felder[x][y].getIcon().setY(ky*50+5);
		felder[kx][ky]=felder[x][y];
		felder[x][y]=null;
	}
	
	public boolean isPiece(int x, int y){
    	return !(felder[x][y] == null);
    }

}
