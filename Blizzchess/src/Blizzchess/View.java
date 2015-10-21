package Blizzchess;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
    	//Aufbau Grundfenster; Stage + Scene
        primaryStage.setTitle("Blizzchess - Savants of Warcraft");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 450, 450);
        Board board = new Board(root);
        
        
       // root.getChildren().add(board.getIcon());  Wird in Board.java verlegt
        
        //TEST1
        //TEST1 beinhaltet die farbliche Umrandung um ein Piece zur Darstellung Reichweite (Movement)
		final int koX = (int)root.getLayoutX();
		final int koY = (int)root.getLayoutY();
		
		final Canvas canvas = new Canvas (700,700);
		canvas.setStyle("-fx-border-color: green;");
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.rgb(0, 255, 0, 0.9));
		gc.setLineWidth(3);
        //ENDTEST1
        
        primaryStage.setScene(scene);
        
        
        
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            //Aufbau/Kontrolle eines Spielzuges
            public void handle(MouseEvent event) {
                switch(turnState){
                case 0: //Spieler ist in einem neuen Spielzug
            		System.out.println("mouse click detected! "+event.getSource());
            		x = (int)(event.getX()-board.getIcon().getX())/50;
            		y = (int)(event.getY()-board.getIcon().getY())/50;
            		if(board.isPiece(x,y)){
            			turnState=1;
            			
            			//TEST1
            			gc.strokeRect(koX+50*x,koY+50*y, 50, 50);
            			
            			for(int i=0; i<3; i++)
            			{
            				if((koX+50*(x-i))/50>-1 && (koX+50*(x-i))/50<9)
            				gc.strokeRect(koX+50*(x-i),koY+50*y, 50, 50);
            			}
            			for(int i=1; i<3; i++)
            			{
            				if((koX+50*(x+i))/50>0 && (koX+50*(x+i))/50<9)
            				gc.strokeRect(koX+50*(x+i),koY+50*y, 50, 50);
            			}
            			for(int i=1; i<3; i++)
            			{
            				if((koX+50*(y-i))/50>0-1 && (koX+50*(y-i))/50<9)
            				gc.strokeRect(koX+50*x,koY+50*(y-i), 50, 50);
            			}
            			for(int i=1; i<3; i++)
            			{
            				if((koX+50*(y+i))/50>0 && (koX+50*(y+i))/50<9)
            				gc.strokeRect(koX+50*x,koY+50*(y+i), 50, 50);
            			}
            			
            			root.getChildren().add(canvas);
            			
            			//ENDTEST1
            			//showMoves();
            		}
            		break;
                case 1: //Spieler hat ein Piece angeklickt
                	
                	xNew= (int)(event.getX()-board.getIcon().getX())/50;
            		yNew = (int)(event.getY()-board.getIcon().getY())/50;
            		board.setField(x, y, xNew, yNew);
            		
            		//TEST1
            		gc.clearRect(canvas.getLayoutX(),canvas.getLayoutY(), canvas.getWidth(), canvas.getHeight());
            		root.getChildren().remove(canvas);
            		//ENDTEST1
            		
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
