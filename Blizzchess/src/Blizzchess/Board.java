package Blizzchess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Board {
	Piece felder[][] = new Piece[9][9];
	String paths [] = new String [2];
	Piece pieces[] = new Piece[2];
	

	ImageView icon;
	Image board;
	
	
	public Board(BorderPane root ){
		board = new Image("file:C:\\Users\\Doured\\Desktop\\BlizzChess\\board.png");
		icon = new ImageView(board);
		paths[0] = "file:C:\\Users\\Doured\\Desktop\\BlizzChess\\peasant.png";
		paths[1] = "file:C:\\Users\\Doured\\Desktop\\BlizzChess\\peon.png";
		
		for(int i=0;i<2;i++){
			pieces[i] = new Piece(paths[i]);
			root.getChildren().add(pieces[i].getIcon());
			
			pieces[i].getIcon().setX(i*50+150);
			pieces[i].getIcon().setY(i*50+300);
		}
	}
	
	public ImageView getIcon(){
		return icon;
	}
	
	public void setField(int x, int y, int kx, int ky){
		felder[x][y].getIcon().setX(kx*50);
		felder[x][y].getIcon().setY(ky*50);
		felder[kx][ky]=felder[x][y];
		felder[x][y]=null;
	}
	
	public boolean isPiece(int x, int y){
    	return !(felder[x][y] == null);
    }

}
