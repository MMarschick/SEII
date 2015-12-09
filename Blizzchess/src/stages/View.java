package stages;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.WindowEvent;
import pieces.Alliance;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

import connection.Client;
import connection.Test;
import game.Board;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import game.*;

public class View extends Application 
{
	//StartUp
	public static final CountDownLatch latch = new CountDownLatch(1);
	public static View startUpTest = null;
	public static View waitForStartUpTest() 
	{
		try {latch.await();} 
		catch (InterruptedException e) {e.printStackTrace();}
		return startUpTest;
	}
	public static void setStartUpTest(View startUpTest0) 
	{
		startUpTest = startUpTest0;
		latch.countDown();
	}
	public View() {setStartUpTest(this);}
	
	//Klassen der Stages
	Login login = new Login();
	Client player;
	GameSelect gameSelect = new GameSelect();
	Menu menu = new Menu();
	Info info = new Info();
	Board board;
	

	//Variablen der Stages
	IntegerProperty turnState = new SimpleIntegerProperty();
	Alliance whoseTurn;
	int x,y,xNew,yNew;	//x/y: Koordinaten von Event/Piece; xNew/yNew: Neue Koordinaten von Piece (durch Event)
	int xT,yT;			//MouseMovedEvent; change names of var
	ArrayList<Integer> possibleMove;
	ArrayList<Integer> possibleTarget;
	//WARZM
	public void start(Stage primaryStage) 
	{
		//Definierung der primaryStage + Pane + Scene
		primaryStage.setTitle("Blizzchess - Savants of Warcraft");
		primaryStage.setResizable(false);
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 440, 440);
		primaryStage.setScene(scene);
		
		login.showStage();

		turnState.addListener(new ChangeListener() 
		{
		      public void changed(ObservableValue observableValue, Object oldValue,
		          Object newValue) {menu.setButtonImage(turnState.getValue());}
		});
		turnState.setValue(0);


		//wird Hauptstage verschoben, verschieben sich beide Leisten entsprechend mit
		primaryStage.xProperty().addListener(new ChangeListener<Number>() 
		{
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
			{
				menu.setPosition(primaryStage);
				info.setPosition(primaryStage);
			}
		});
		
		primaryStage.yProperty().addListener(new ChangeListener<Number>() 
		{
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) 
			{
				menu.setPosition(primaryStage);
				info.setPosition(primaryStage);
			}
		});

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
						else{System.out.println("Failed to login");}
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
				catch(Exception e){e.printStackTrace();}
			}
		});
		
		gameSelect.getNewGameBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent ae)
			{
//				String[] newGame = player.searchGame().split(Pattern.quote("|"));
//				switch(newGame[0])
//				{
//					case "wait":break;
//					case "false":break;
//					case "defaultString":System.out.println("Baue Board auf");break;
//				}	
				board = new Board(root, player.getGame());
				gameSelect.closeStage();
				primaryStage.show();
				menu.setPosition(primaryStage);
				menu.showStage();
				info.setPosition(primaryStage);
				info.showStage();
			}
		});

		//EventHandler: wenn Login-Button betätigt wird
		login.getBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			
			public void handle(ActionEvent ae)
			{
				login.getActiontarget().setFill(Color.YELLOW);
				login.getActiontarget().setText("connecting...");
				//New
				player = new Client();
				player.setPlayerName(login.getUserTextField().getText());
				player.setPlayerPW(login.getPwBox().getText());

				//ueberprueft, ob Name und Passwort uebereinstimmt
				if(player.checkLogin())
				{
					login.closeStage();
					login.clearStrings();
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
//		Board board = new Board(root, GameParser.DEFAULT_STRING);
		
//		Arrays.toString(board.getFelder());

		//Setzen des Canvas (Zeichner) für Felder auf die eine Figur bewegt werden kann
		final Test canvasGreen = new Test("GREEN");
		
		//Setzen des Canvas (Zeichner) für angreifbare Felder
		final Test canvasRed = new Test("RED");
		
		//Setzen des Canvas (Zeichner) für angeklickets Piece
		final Test canvasOrchid = new Test("DARKORCHID");
		
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
			//Aufbau/Kontrolle eines Spielzuges
			public void handle(MouseEvent event) 
			{
				if(player.waitForTurn()){whoseTurn=Alliance.GOOD;}

				if(whoseTurn==board.getMyAlliance()){handleTurn(board, canvasGreen, canvasGreen.getGcCanvas(), canvasRed, canvasRed.getGcCanvas(), canvasOrchid, canvasOrchid.getGcCanvas(), event);}
			}

			private void handleTurn(Board board, final Canvas canvasGreen, GraphicsContext gcGreen,
					final Canvas canvasRed, GraphicsContext gcRed, final Canvas canvasOrchid, GraphicsContext gcOrchid, MouseEvent event) 
			{
				//Switch-Case in Game
				switch(turnState.getValue()){
				case 0: //Spieler ist in einem neuen Spielzug
					x = (int)(event.getX()-board.getIcon().getX())/50;
					y = (int)(event.getY()-board.getIcon().getY())/50;
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
							
							turnState.setValue(0);
							//gameState
							if(whoseTurn==Alliance.GOOD)whoseTurn=Alliance.EVIL;
							else whoseTurn=Alliance.GOOD;
							board.flush(player);
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
							board.flush(player);
						}
					}
					menu.setButtonImage(2);
					break;
				}
			}
			
		});
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() 
		{
			public void handle(WindowEvent we) 
			{
				menu.closeStage();
				info.closeStage();
			}
		});    
	}
}






