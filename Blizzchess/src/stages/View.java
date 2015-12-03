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

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

import connection.Client;
import game.Board;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class View extends Application 
{
	public static final CountDownLatch latch = new CountDownLatch(1);
	public static View startUpTest = null;


	int turnState=0;
	int gameState;
	int x,y,xNew,yNew;	//x/y: Koordinaten von Event/Piece; xNew/yNew: Neue Koordinaten von Piece (durch Event)
	int xT,yT;			//MouseMovedEvent; change names of var

	ArrayList<Integer> possibleMove;
	ArrayList<Integer> possibleTarget;

	public static View waitForStartUpTest() 
	{
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return startUpTest;
	}

	public static void setStartUpTest(View startUpTest0) 
	{
		startUpTest = startUpTest0;
		latch.countDown();
	}

	public View()
	{
		setStartUpTest(this);
	}
	
//	Media sound;
//	public Media getSound()
//	{
//		return sound;
//	}
//	public void setSound(String track)
//	{
//		sound=new Media(new File(track).toURI().toString());
//	}
	@Override
	public void start(Stage primaryStage) {
		//Zunaechst wird der Login behandelt
		Login login = new Login();
		Client player = new Client();
		GameSelect gameSelect = new GameSelect();
		Menu menu = new Menu();
		login.showStage();
		
//		setSound("sounds\\stages\\Mainscreen.mp3");
//		MediaPlayer audio = new MediaPlayer(getSound());
//	    audio.setVolume(0.05);
//		audio.play();
//		audio.setAutoPlay(true);

		login.getCreateBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent ae)
			{
				if(!login.getNewPlayerName().getText().equals("")&&
						!login.getNewPlayerPW().getText().equals("")&&
						login.getNewPlayerPWConfirm().getText().equals(login.getNewPlayerPW().getText()))
				{
					System.out.println("Spieler erstellen");
					player.setPlayerName(login.getNewPlayerName().getText());
					player.setPlayerPW(login.getNewPlayerPW().getText());
					if(player.createPlayer())
					{
						System.out.println("Erfolgreich");
						if(player.checkLogin())
						{
							login.closeNewPlayerStage();
							login.closeStage();
							gameSelect.showStage();
						}
						else
						{
							System.out.println("Failed to login");
						}
					}
					else
					{
						System.out.println("Fehlerhafte Eingabe");
						login.setL4();
						login.clearStringsNewPlayer();
					}
				}
				else
				{
					System.out.println("Fehlerhafte Eingabe");
					login.setL4();
					login.clearStringsNewPlayer();
				}
			}
		});
		login.getCancelBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent ae)
			{
				System.out.println("Erstellung abbrechen");
				login.closeNewPlayerStage();
			}
		});
		
		gameSelect.getLogoutBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent ae)
			{
				try
				{
					if(player.checkLogout())
					{
						gameSelect.closeStage();
						login.showStage();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		gameSelect.getNewGameBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent ae)
			{
				String[] newGame = player.searchGame().split(Pattern.quote("|"));
				switch(newGame[0])
				{
					case "wait":break;
					case "false":break;
					case "defaultString":System.out.println("Baue Board auf");break;
				}
				
				
//				gameSelect.closeStage();
//				primaryStage.show();
//				menu.showStage();
				
			}
		});
		
		//EventHandler: wenn Login-Button betaetigt wird
		login.getBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			
			public void handle(ActionEvent ae)
			{
				login.getActiontarget().setFill(Color.YELLOW);
				login.getActiontarget().setText("connecting...");
				//New
				player.setPlayerName(login.getUserTextField().getText());
				player.setPlayerPW(login.getPwBox().getText());
				//Daten muessen an Server geschickt werden
				//Danach wird dort ueberprueft, ob Name und Passwort uebereinstimmt
				//Boolean wird zurueckgegeben und in der if-Anweisung geprueft
				if(player.checkLogin())
				{
					login.closeStage();
					//login.clearStrings();
					gameSelect.setOpenGames(gameSelect.createOpenGamesList(player.getOpenGames()));
					gameSelect.showStage();
				}
				else
				{
					login.getActiontarget().setFill(Color.RED);
					login.getActiontarget().setText("Could not login");
					login.showAndWaitNewPlayerStage();
				}
			}
		});

		menu.getBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent ae)
			{
				menu.changeButton();
				if(turnState>1)
				{
					turnState=0;
				}
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
		//final int koX = (int)root.getLayoutX();
		//final int koY = (int)root.getLayoutY();

		//Setzen des Canvas (Zeichner) für Felder auf die eine Figur bewegt werden kann
		final Canvas canvasGreen = new Canvas (700,700);
		canvasGreen.setStyle("-fx-border-color: green;");
		GraphicsContext gcGreen = canvasGreen.getGraphicsContext2D();
		gcGreen.setStroke(Color.rgb(0, 255, 0, 0.9));
		gcGreen.setLineWidth(3);

		//Setzen des Canvas (Zeichner) für angreifbare Felder
		final Canvas canvasRed = new Canvas (700,700);
		canvasRed.setStyle("-fx-border-color: red;");
		GraphicsContext gcRed = canvasRed.getGraphicsContext2D();
		gcRed.setStroke(Color.rgb(255, 0, 0, 0.9));
		gcRed.setLineWidth(3);

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
					infoStage.close(); //schließen, wenn nicht piece
				}
			}
		}
				);
		//Event um infoStage zu schließen beim verlassen der scene
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

		scene.setOnMousePressed(new EventHandler<MouseEvent>() 
		{
			@Override
			//Aufbau/Kontrolle eines Spielzuges
			public void handle(MouseEvent event) 
			{
				//Switch-Case in Game
				switch(turnState)
				{
				case 0: //Spieler ist in einem neuen Spielzug
					x = (int)(event.getX()-board.getIcon().getX())/50;
					y = (int)(event.getY()-board.getIcon().getY())/50;
					if(board.isPiece(x,y))
					{
						board.calculateMovement(x, y);
						board.calculateTargets(x, y);

						possibleMove=board.getPossibleMove();
						possibleTarget=board.getPossibleTarget();
						turnState=1;

						for(Integer movementInt : possibleMove)
						{
							int greenX = movementInt/10;
							int greenY = movementInt%10;
							gcGreen.strokeRect(greenX*50, greenY*50, 50, 50);
						}
						for(Integer targetInt : possibleTarget)
						{
							int redX = targetInt/10;
							int redY = targetInt%10;
							gcRed.strokeRect(redX*50, redY*50, 50, 50);
						}
						root.getChildren().add(canvasGreen);
						root.getChildren().add(canvasRed);
					}
					break;
				case 1: //Spieler hat ein Piece angeklickt

					xNew = (int)(event.getX()-board.getIcon().getX())/50; //neue x-Koordinate
					yNew = (int)(event.getY()-board.getIcon().getY())/50; //neue y-Koordinate
					int koordInt=(xNew*10+yNew);
					turnState=0;

					for(Integer movementInt : possibleMove)
					{
						if(koordInt==movementInt)
						{
							board.setPiece(x, y, xNew, yNew);
							x=xNew;
							y=yNew;
							turnState=2;
						}
					}
					for(Integer targetInt : possibleMove)
					{
						if(koordInt==targetInt)
						{
							//attacke
							turnState=2;
						}
					}

					//Sobald ein Piece sich bewegt hat, muessen die gezeichneten Quadrate entfernt werden
					//Canvas wird anschliessend entfernt
					gcGreen.clearRect(canvasGreen.getLayoutX(),canvasGreen.getLayoutY(), canvasGreen.getWidth(), canvasGreen.getHeight());
					gcRed.clearRect(canvasRed.getLayoutX(),canvasRed.getLayoutY(), canvasRed.getWidth(), canvasRed.getHeight());
					root.getChildren().remove(canvasGreen);
					root.getChildren().remove(canvasRed);
					break;

				case 2: //Piece wurde bewegt
					
					xNew = (int)(event.getX()-board.getIcon().getX())/50; //neue x-Koordinate
					yNew = (int)(event.getY()-board.getIcon().getY())/50; //neue y-Koordinate

					if(x==xNew && y==yNew)
					{
						board.calculateTargets(x, y);
						turnState=3;
						possibleTarget=board.getPossibleTarget();
						for(Integer targetInt : possibleTarget)
						{
							int redX = targetInt/10;
							int redY = targetInt%10;
							gcRed.strokeRect(redX*50, redY*50, 50, 50);
						}
						root.getChildren().add(canvasRed);
					}
					//eve anzeige details rechte scene
					break;	//???????????????????????
				case 3:
					xNew = (int)(event.getX()-board.getIcon().getX())/50; //neue x-Koordinate
					yNew = (int)(event.getY()-board.getIcon().getY())/50; //neue y-Koordinate
					koordInt=(xNew*10+yNew);
					turnState=2;
					for(Integer targetInt : possibleTarget)
					{
						if(koordInt==targetInt)
						{
							//attacke
							turnState=0;
						}
					}

					gcRed.clearRect(canvasRed.getLayoutX(),canvasRed.getLayoutY(), canvasRed.getWidth(), canvasRed.getHeight());
					root.getChildren().remove(canvasRed);
					break;
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






