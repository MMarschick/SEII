package Blizzchess;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.concurrent.CountDownLatch;
 
public class View extends Application {
	
	public static final CountDownLatch latch = new CountDownLatch(1);
    public static View startUpTest = null;
   
	Piece felder[][] = new Piece[9][9];
	int turnState=0;
	int x,y,xNew,yNew;
	
	public static View waitForStartUpTest() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return startUpTest;
    }
	
	public static void setStartUpTest(View startUpTest0) {
        startUpTest = startUpTest0;
        latch.countDown();
    }

    public View() {
        setStartUpTest(this);
    }
//    public static void main(String[] args) {
//		launch(args);
//	}
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 450, 450);
        
        Piece peasant = new Piece("file:C:\\Users\\Doured\\Desktop\\BlizzChess\\peasant.png");
        felder[3][6]=peasant;
        Image board = new Image("file:C:\\Users\\Doured\\Desktop\\BlizzChess\\board.png");
        
        ImageView boardV = new ImageView(board);
        
        root.getChildren().add(boardV);
        root.getChildren().add(peasant.getIcon());
        peasant.getIcon().setX(150);
        peasant.getIcon().setY(300);
        
        
        primaryStage.setScene(scene);
        
        
        
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch(turnState){
                case 0:
            		System.out.println("mouse click detected! "+event.getSource());
            		x = (int)(event.getX()-boardV.getX())/50;
            		y = (int)(event.getY()-boardV.getY())/50;
            		if(isPiece(x,y)){
            			turnState=1;
            			//showMoves();
            		}
            		break;
                case 1: 
                	xNew= (int)(event.getX()-boardV.getX())/50;
            		yNew = (int)(event.getY()-boardV.getY())/50;
            		felder[x][y].getIcon().setX(xNew*50);
            		felder[x][y].getIcon().setY(yNew*50);
            		felder[xNew][yNew]=felder[x][y];
            		felder[x][y]=null;
            		turnState=0;
                	break;
                case 2: break;
                case 3: break;
                }
                
                
            }
        });
        
        
        
        primaryStage.show();
        
       
    }
    
    public boolean isPiece(int x, int y){
    	return !(felder[x][y] == null);
    }
}
