package stages;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import pieces.Alliance;
import pieces.StatusEffect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
<<<<<<< HEAD
import java.util.regex.Pattern;

import connection.Client;
import game.Board;
=======
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import game.*;
>>>>>>> 7eb61cee2ea59eea78963a810b0e8f7c6ad29541

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class View extends Application 
{
	//StartUp
	public static final CountDownLatch latch = new CountDownLatch(1);
	public static View startUpTest = null;
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
	
<<<<<<< HEAD
//	Media sound;
//	public Media getSound()
//	{
//		return sound;
//	}
//	public void setSound(String track)
//	{
//		sound=new Media(new File(track).toURI().toString());
//	}
=======
	//Klassen der Stages
	Menu menu = new Menu();
	Info info = new Info();

	
	IntegerProperty turnState = new SimpleIntegerProperty();
	
		
	
	Alliance whoseTurn;
	int x,y,xNew,yNew;	//x/y: Koordinaten von Event/Piece; xNew/yNew: Neue Koordinaten von Piece (durch Event)
	int xT,yT;			//MouseMovedEvent; change names of var

	ArrayList<Integer> possibleMove;
	ArrayList<Integer> possibleTarget;
	
	
	

>>>>>>> 7eb61cee2ea59eea78963a810b0e8f7c6ad29541
	@Override
	public void start(Stage primaryStage) {
		turnState.addListener(new ChangeListener() {
		      @Override
		      public void changed(ObservableValue observableValue, Object oldValue,
		          Object newValue) {
		        menu.setButtonImage(turnState.getValue());
		      }
		   });
		turnState.setValue(0);


		//wird Hauptstage verschoben, verschieben sich beide Leisten entsprechend mit
		primaryStage.xProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				menu.setPosition(primaryStage);
				info.setPosition(primaryStage);
			}
		});
		
		primaryStage.yProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				menu.setPosition(primaryStage);
				info.setPosition(primaryStage);
			}
		});
		
		//Zunächst wird der Login behandelt
		Login login = new Login();
<<<<<<< HEAD
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
=======
		
		//EventHandler: wenn Login-Button betätigt wird
>>>>>>> 7eb61cee2ea59eea78963a810b0e8f7c6ad29541
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
<<<<<<< HEAD
					//login.clearStrings();
					gameSelect.setOpenGames(gameSelect.createOpenGamesList(player.getOpenGames()));
					gameSelect.showStage();
=======
					primaryStage.show();
					menu.setPosition(primaryStage);
					menu.showStage();
					info.setPosition(primaryStage);
					info.showStage();
>>>>>>> 7eb61cee2ea59eea78963a810b0e8f7c6ad29541
				}
				else
				{
					login.getActiontarget().setFill(Color.RED);
					login.getActiontarget().setText("Could not login");
					login.showAndWaitNewPlayerStage();
				}
			}
		});
<<<<<<< HEAD

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
=======
		login.showStage();
>>>>>>> 7eb61cee2ea59eea78963a810b0e8f7c6ad29541


		//Definierung der primaryStage + Pane + Scene
		primaryStage.setTitle("Blizzchess - Savants of Warcraft");
		primaryStage.setResizable(false);
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 440, 440);
		primaryStage.setScene(scene);
		Board board = new Board(root, GameParser.DEFAULT_STRING);
		

<<<<<<< HEAD
		
		
		//koX/Y: Nullkoordinaten
		//final int koX = (int)root.getLayoutX();
		//final int koY = (int)root.getLayoutY();
=======
		Arrays.toString(board.getFelder());
>>>>>>> 7eb61cee2ea59eea78963a810b0e8f7c6ad29541

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
		
		//Setzen des Canvas (Zeichner) für angeklickets Piece
		final Canvas canvasOrchid = new Canvas (700,700);
		canvasOrchid.setStyle("-fx-border-color: darkorchid;");
		GraphicsContext gcOrchid = canvasOrchid.getGraphicsContext2D();
		gcOrchid.setStroke(Color.DARKORCHID);
		gcOrchid.setLineWidth(3);
		
		root.getChildren().add(canvasOrchid);
		root.getChildren().add(canvasGreen);
		root.getChildren().add(canvasRed);
		
		
		//Event um die infoStage zu befuellen und anzuzeigen
		scene.setOnMouseMoved(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				xT = (int)(event.getX()-board.getIcon().getX())/50;
				yT = (int)(event.getY()-board.getIcon().getY())/50;

				if(board.isPiece(xT,yT)) //befuellen, wenn piece
				{
					info.removeAbilityLabels();
					info.showInfo(board.getPiece(xT, yT));
				}
			}
		});
		

		scene.setOnMousePressed(new EventHandler<MouseEvent>() 
		{
			@Override
			//Aufbau/Kontrolle eines Spielzuges
			public void handle(MouseEvent event) 
			{
				whoseTurn=Alliance.GOOD;
				if(whoseTurn==board.getMyAlliance())
				{
					handleTurn(board, canvasGreen, gcGreen, canvasRed, gcRed, event);
				}
			}

			private void handleTurn(Board board, final Canvas canvasGreen, GraphicsContext gcGreen,
					final Canvas canvasRed, GraphicsContext gcRed, MouseEvent event) {
				//Switch-Case in Game
				switch(turnState.getValue())
				{
				case 0: //Spieler ist in einem neuen Spielzug
					x = (int)(event.getX()-board.getIcon().getX())/50;
					y = (int)(event.getY()-board.getIcon().getY())/50;
					//if(board.isMyPiece(x,y))
					if(board.isPiece(x,y))
					{
						board.calculateMovement(x, y);
						board.calculateTargets(x, y);

						possibleMove=board.getPossibleMove();
						possibleTarget=board.getPossibleTarget();
						turnState.setValue(1);
						
						
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
						gcOrchid.strokeRect(x*50, y*50, 50, 50);
					}
					break;
				case 1: //Spieler hat ein Piece angeklickt

					xNew = (int)(event.getX()-board.getIcon().getX())/50; //neue x-Koordinate
					yNew = (int)(event.getY()-board.getIcon().getY())/50; //neue y-Koordinate
					int koordInt=(xNew*10+yNew);
					turnState.setValue(3);;

					for(Integer movementInt : possibleMove)
					{
						if(koordInt==movementInt)
						{
							board.setPiece(x, y, xNew, yNew);
							x=xNew;
							y=yNew;
							turnState.setValue(2);
							board.calculateTargets(x, y);
							possibleTarget=board.getPossibleTarget();
							if(possibleTarget.isEmpty())
							{
								gcRed.clearRect(canvasRed.getLayoutX(),canvasRed.getLayoutY(), canvasRed.getWidth(), canvasRed.getHeight());
								turnState.setValue(0);
								if(whoseTurn==Alliance.GOOD)whoseTurn=Alliance.EVIL;
								else whoseTurn=Alliance.GOOD;
								break;
							}

							gcRed.clearRect(canvasRed.getLayoutX(),canvasRed.getLayoutY(), canvasRed.getWidth(), canvasRed.getHeight());

							for(Integer targetInt : possibleTarget)
							{
								int redX = targetInt/10;
								int redY = targetInt%10;
								gcRed.strokeRect(redX*50, redY*50, 50, 50);
							}
						}
					}
					for(Integer targetInt : possibleTarget)
					{
						if(koordInt==targetInt)
						{
							gcRed.clearRect(canvasRed.getLayoutX(),canvasRed.getLayoutY(), canvasRed.getWidth(), canvasRed.getHeight());
							
							//attacke
							board.getPiece(xNew,yNew).attack();
//							attack(board);
							
							turnState.setValue(0);
							//gameState
							if(whoseTurn==Alliance.GOOD)whoseTurn=Alliance.EVIL;
							else whoseTurn=Alliance.GOOD;
						}
					}

					if(turnState.getValue()==3)
					{
						gcRed.clearRect(canvasRed.getLayoutX(),canvasRed.getLayoutY(), canvasRed.getWidth(), canvasRed.getHeight());
						turnState.setValue(0);
					}

					//Sobald ein Piece sich bewegt hat, muessen die gezeichneten Quadrate entfernt werden
					//Canvas wird anschliessend entfernt
					gcGreen.clearRect(canvasGreen.getLayoutX(),canvasGreen.getLayoutY(), canvasGreen.getWidth(), canvasGreen.getHeight());
					gcOrchid.clearRect(canvasOrchid.getLayoutX(),canvasOrchid.getLayoutY(), canvasOrchid.getWidth(), canvasOrchid.getHeight());

					board.update(xNew,yNew,board.getPiece(xNew, yNew));
					break;

				case 2: //Piece hat sich bewegt
					xNew = (int)(event.getX()-board.getIcon().getX())/50; //neue x-Koordinate
					yNew = (int)(event.getY()-board.getIcon().getY())/50; //neue y-Koordinate
					koordInt=(xNew*10+yNew);
					for(Integer targetInt : possibleTarget)
					{
						if(koordInt==targetInt)
						{
							//attacke
							board.getPiece(xNew,yNew).attack();
							board.getPiece(xNew, yNew).setHealthLabel(board.getPiece(xNew, yNew).getHealth());
							turnState.setValue(0);
							if(whoseTurn==Alliance.GOOD)whoseTurn=Alliance.EVIL;
							else whoseTurn=Alliance.GOOD;
							gcRed.clearRect(canvasRed.getLayoutX(),canvasRed.getLayoutY(), canvasRed.getWidth(), canvasRed.getHeight());
						}
					}
					
					menu.setButtonImage(2);
					break;
				}
			}
			
		});
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				menu.closeStage();
				info.closeStage();
			}
		});    
	}
}






