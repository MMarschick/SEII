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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.concurrent.CountDownLatch;
 
public class View extends Application {
	
	public static final CountDownLatch latch = new CountDownLatch(1);
    public static View startUpTest = null;
   
	
	int turnState=0;
	int x,y,xNew,yNew;
	int xT,yT;///MouseMovedEvent; change names of var
	
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
    	
    	//TEST2
    	//TEST2 beinhaltet das Login Fenster 
    	Stage loginStage = new Stage();
    	loginStage.setTitle("Login for Blizzchess");
		loginStage.setResizable(false);
    	GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		final Text actiontarget = new Text();
		grid.add(actiontarget, 0, 16);
		
		Scene sceneLogin = new Scene(grid, 400, 340);
		grid.setStyle("-fx-background-image: url('alliances.png');");
		loginStage.setScene(sceneLogin);
		
		Text scenetitle = new Text("Welcome to Blizzchess");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setFill(Color.RED);
		grid.add(scenetitle, 0, 2);
		
		Label enemyName = new Label("Enemy (IP):");
		enemyName.setTextFill(Color.WHITE);
		grid.add(enemyName, 0, 6);
		
		TextField enemyTextField = new TextField();
		grid.add(enemyTextField, 3, 6);
		
		Label serverName = new Label("Server (IP):");
		serverName.setTextFill(Color.WHITE);
		grid.add(serverName, 0, 7);
		
		TextField serverTextField = new TextField();
		grid.add(serverTextField, 3, 7);
		
		Label userName = new Label("User Name:");
		userName.setTextFill(Color.WHITE);
		grid.add(userName, 0, 14);
		
		TextField userTextField = new TextField();
		grid.add(userTextField, 3, 14);
		
		Label pw = new Label("Password:");
		pw.setTextFill(Color.WHITE);
		grid.add(pw, 0, 15);
		
		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 3, 15);
		
		
		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 3, 16);
		
		btn.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
			{
				actiontarget.setFill(Color.RED);
				actiontarget.setText("Could not connect, please check Input");
				//Daten muessen an Server geschickt werden
				//Danach wird dort ueberprueft, ob Name und Passwort uebereinstimmt
				//Boolean wird zurueckgegeben und in der if-Anweisung geprueft
				if(userTextField.getText().equals("") && pwBox.getText().equals(""))
				{
					loginStage.close();
					primaryStage.show();
				}
				
			}
		});
		loginStage.show();
    	//ENDTEST2
		
        primaryStage.setTitle("Blizzchess - Savants of Warcraft");
		primaryStage.setResizable(false);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 440, 440);
        Board board = new Board(root);
        
        
       // root.getChildren().add(board.getIcon());  Wird in Board.java verlegt
        
        //TEST1
        //TEST1 beinhaltet die farbliche Umrandung um ein Piece zur Darstellung Reichweite (Movement)
        //koX/Y: Nullkoordinaten
		final int koX = (int)root.getLayoutX();
		final int koY = (int)root.getLayoutY();
		
		//Setzen des Canvas (Zeichner)
		final Canvas canvas = new Canvas (700,700);
		canvas.setStyle("-fx-border-color: green;");
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.rgb(0, 255, 0, 0.9));
		gc.setLineWidth(3);
        //ENDTEST1
        
        primaryStage.setScene(scene);
        
        //TEST3
        
        Stage infoStage = new Stage();
        infoStage.setTitle("Info");
       //infoStage.setX(loginStage.getX()+434); old
       //infoStage.setY(loginStage.getY()-33); old
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
            			infoStage.setX(xT*50+508);
            			infoStage.setY(yT*50+12);
            			ImageView aktPiece = new ImageView(board.getImage(xT, yT));
            			aktPiece.setScaleX(2.5);
            			aktPiece.setScaleY(2.5);
            			aktPiece.setX(29);
            			aktPiece.setY(29);
            			infoPane.getChildren().add(aktPiece);
            			infoStage.show();
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
        //ENDTEST3
        
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
            			System.out.println(turnState);
            			
            			//TEST1
            			//Block zum Zeichnen des Movementpattern
            			//Muss das Movementpattern spaeter verarbeiten koennen
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
                	xNew = (int)(event.getX()-board.getIcon().getX())/50;
            		yNew = (int)(event.getY()-board.getIcon().getY())/50;
            		board.setField(x, y, xNew, yNew);
            		
            		//TEST1
            		//Sobald ein Piece sich bewegt hat, muessen die gezeichneten Quadrate entfernt werden
            		//Canvas wird anschliessend entfernt
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
        
        
        
        //primaryStage.show();
        

    }
    

}
