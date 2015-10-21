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

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 450, 450);
        Board board = new Board(root);
        
        
        root.getChildren().add(board.getIcon());
        
        
        
        primaryStage.setScene(scene);
        
        
        
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                switch(turnState){
                case 0:
            		System.out.println("mouse click detected! "+event.getSource());
            		x = (int)(event.getX()-board.getIcon().getX())/50;
            		y = (int)(event.getY()-board.getIcon().getY())/50;
            		if(board.isPiece(x,y)){
            			turnState=1;
            			//showMoves();
            		}
            		break;
                case 1: 
                	
                	xNew= (int)(event.getX()-board.getIcon().getX())/50;
            		yNew = (int)(event.getY()-board.getIcon().getY())/50;
            		board.setField(x, y, xNew, yNew);
            		
            		turnState=0;
                	break;
                case 2: break;
                case 3: break;
                }
                
                
            }
        });
        
        
        
        primaryStage.show();
        
       
    }
    

}
