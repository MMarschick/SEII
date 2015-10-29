//Verwaltet alle Stages
// enth‰lt Grafische Elemente wie Rekt

package stages;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.concurrent.CountDownLatch;

import game.Board;
 
public class View extends Application {
	
	public static final CountDownLatch latch = new CountDownLatch(1);
    public static View startUpTest = null;
   
	
	int turnState=0;
	int x,y,xNew,yNew;	//x/y: Koordinaten von Event/Piece; xNew/yNew: Neue Koordinaten von Piece (durch Event)
	int xT,yT;			//MouseMovedEvent; change names of var
	
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
    	//Zunaechst wird der Login behandelt
    	Login login = new Login();
    	Menu menu = new Menu();
    	//EventHandler: wenn Login-Button betaetigt wird
		login.getBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				//Daten muessen an Server geschickt werden
				//Danach wird dort ueberprueft, ob Name und Passwort uebereinstimmt
				//Boolean wird zurueckgegeben und in der if-Anweisung geprueft
				if(login.getUserTextField().getText().equals("") && login.getPwBox().getText().equals(""))
				{
					login.closeStage();
					primaryStage.show();
					menu.showStage();
				}
				else
				{
					login.getActiontarget().setFill(Color.RED);
					login.getActiontarget().setText("Could not connect, please check Input");
				}
			}
		});
		login.showStage();
		
		
		menu.getBtn().setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e)
			{
				menu.changeButton();
			}
			
		});
		
		
		//Definierung der primaryStage + Pane + Scene
        primaryStage.setTitle("Blizzchess - Savants of Warcraft");
		primaryStage.setResizable(false);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 440, 440);
        primaryStage.setScene(scene);
        Board board = new Board(root);
        
        //koX/Y: Nullkoordinaten
		final int koX = (int)root.getLayoutX();
		final int koY = (int)root.getLayoutY();
		
		//Setzen des Canvas (Zeichner)
		final Canvas canvas = new Canvas (700,700);
		canvas.setStyle("-fx-border-color: green;");
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.rgb(0, 255, 0, 0.9));
		gc.setLineWidth(3);
                
		
		
		//info stage in eigene Klasse!
     
		//Definierung der infoStage + Scene + Pane
        Stage infoStage = new Stage();
        infoStage.initStyle(StageStyle.UNDECORATED);
        BorderPane infoPane = new BorderPane();
        Scene infoScene = new Scene(infoPane, 100, 250);
        infoStage.setScene(infoScene);
        
        
        //Event um die infoStage zu befuellen und anzuzeigen
        scene.setOnMouseMoved(new EventHandler<MouseEvent>()
       		{
        		public void handle(MouseEvent event)
        		{
            		xT = (int)(event.getX()-board.getIcon().getX())/50;
            		yT = (int)(event.getY()-board.getIcon().getY())/50;
            		
            		if(board.isPiece(xT,yT)) //befuellen, wenn piece
            		{
            			if (menu.getButtonGreen() == true){
						// Bild des Piece holen, neu skalieren und infoStage
						// setzen
            				infoStage.setX(xT * 50 + 508);
            				infoStage.setY(yT * 50 + 11);
            				ImageView aktPiece = new ImageView(board.getImage(xT, yT));
            				aktPiece.setScaleX(2.5);
            				aktPiece.setScaleY(2.5);
            				aktPiece.setX(29);
            				aktPiece.setY(29);
            				infoPane.getChildren().add(aktPiece);
            				infoStage.show();
            			}
            		}
            		else
            		{
            			infoStage.close(); //schlieﬂen, wenn nicht piece
            		}
        		}
       		}
        );
        //Event um infoStage zu schlieﬂen beim verlassen der scene
        scene.setOnMouseExited(new EventHandler<MouseEvent>()
        	{
        		public void handle(MouseEvent event)
        		{
        			if(infoStage.isShowing())
        			{
        				infoStage.close();
        			}
        		}
        	}
        );
        
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            //Aufbau/Kontrolle eines Spielzuges
            public void handle(MouseEvent event) {
            	//Switch-Case in Game
                switch(turnState){
                case 0: //Spieler ist in einem neuen Spielzug
            		x = (int)(event.getX()-board.getIcon().getX())/50;
            		y = (int)(event.getY()-board.getIcon().getY())/50;
            		if(board.isPiece(x,y)){
            			turnState=1;
            			
            			
            			//TEST1
            			//Block zum Zeichnen des Movementpattern
            			//benutzt neue possibleMove Methode
            			gc.strokeRect(koX+50*x,koY+50*y, 50, 50);
            			
            			int xMovePat = board.getPiece(x, y).getMovementPattern().getX();
            			int yMovePat = board.getPiece(x, y).getMovementPattern().getY();
            			int specialMovePat = board.getPiece(x, y).getMovementPattern().getSpecial();
            			int y2MovePat = board.getPiece(x, y).getMovementPattern().getY2();
            				
            			if (specialMovePat == 1)
            			{
            				gc.strokeRect(x*50, (y-yMovePat)*50, 50, 50);
            				if(y==7)
            				{
            					gc.strokeRect(x*50, (y-y2MovePat)*50, 50, 50);
            				}
            			}
            			
            			if (specialMovePat == 2 || specialMovePat == 0)
            			{
            				for (int i=x-xMovePat, j=y-yMovePat; i<x+xMovePat+1; i++,j++)
            				{
            					gc.strokeRect(i*50, j*50, 50, 50);
            				}
            				for (int i=x-xMovePat, j=y+yMovePat; i<x+xMovePat+1; i++,j--)
            				{
            					gc.strokeRect(i*50, j*50, 50, 50);
            				}
            			}
            			
            			if (specialMovePat == 3 || specialMovePat == 0)
            			{
            				for(int i=x-xMovePat, j=y-yMovePat; i<x+xMovePat+1; i++, j++)
            				{
            					gc.strokeRect(i*50, y*50, 50, 50);
            					gc.strokeRect(x*50, j*50, 50, 50);
            				}
            			}
            			
            			if (specialMovePat == 4)
            			{
            				for(int i=x-xMovePat, j=1; j<3; i++, j++)
            				{
            					gc.strokeRect(i*50, (y+j)*50, 50, 50);
            				}
            				for(int i=1, j=2; j>0; i++, j--)
            				{
            					gc.strokeRect((i+x)*50, (y+j)*50, 50, 50);
            				}
            				for(int i=x-xMovePat, j=1; j<3; i++, j++)
            				{
            					gc.strokeRect(i*50, (y-j)*50, 50, 50);
            				}
            				for(int i=1, j=2; j>0; i++, j--)
            				{
            					gc.strokeRect((i+x)*50, (y-j)*50, 50, 50);
            				}
            			}
            			
            			root.getChildren().add(canvas);
            		}
            		break;
                case 1: //Spieler hat ein Piece angeklickt

                	
                	xNew= (int)(event.getX()-board.getIcon().getX())/50; //neue x-Koordinate
            		yNew = (int)(event.getY()-board.getIcon().getY())/50; //neue y-Koordinate
            		
            		
            		
            		board.setField(x, y, xNew, yNew); // Bewegung auf neues Feld
            			


            		
            		//Sobald ein Piece sich bewegt hat, muessen die gezeichneten Quadrate entfernt werden
            		//Canvas wird anschliessend entfernt
            		gc.clearRect(canvas.getLayoutX(),canvas.getLayoutY(), canvas.getWidth(), canvas.getHeight());
            		root.getChildren().remove(canvas);

            		turnState=0;
                	break;
                case 2: break;
                case 3: break;
                }
            }
        });
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                menu.closeStage();
            }
        });    
    }
}






