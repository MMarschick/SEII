//Verwaltet alle Stages
// enthält Grafische Elemente wie Rekt

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import game.*;

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
	
	//Klassen der Stages
	Menu menu = new Menu();
	Info info = new Info();
	
	int turnState=0;
	Alliance whoseTurn=Alliance.GOOD;
	int x,y,xNew,yNew;	//x/y: Koordinaten von Event/Piece; xNew/yNew: Neue Koordinaten von Piece (durch Event)
	int xT,yT;			//MouseMovedEvent; change names of var

	ArrayList<Integer> possibleMove;
	ArrayList<Integer> possibleTarget;

	@Override
	public void start(Stage primaryStage) {

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
		
		//EventHandler: wenn Login-Button betätigt wird
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
					menu.setPosition(primaryStage);
					menu.showStage();
					info.setPosition(primaryStage);
					info.showStage();
				}
				else
				{
					login.getActiontarget().setFill(Color.RED);
					login.getActiontarget().setText("Could not connect, please check Input");
				}
			}
		});
		login.showStage();

		//wird später für Zug beenden benutzt
		menu.getBtn().setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent e)
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
		Board board = new Board(root, GameParser.DEFAULT_STRING);

		Arrays.toString(board.getFelder());

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
				System.out.println(turnState);
				System.out.println(whoseTurn);
				if(whoseTurn==board.getMyAlliance())
				{
					//Switch-Case in Game
					switch(turnState)
					{
					case 0: //Spieler ist in einem neuen Spielzug
						x = (int)(event.getX()-board.getIcon().getX())/50;
						y = (int)(event.getY()-board.getIcon().getY())/50;
						if(board.isMyPiece(x,y))
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
							//root.getChildren().add(canvasGreen);
							//root.getChildren().add(canvasRed);
						}
						break;
					case 1: //Spieler hat ein Piece angeklickt

						xNew = (int)(event.getX()-board.getIcon().getX())/50; //neue x-Koordinate
						yNew = (int)(event.getY()-board.getIcon().getY())/50; //neue y-Koordinate
						int koordInt=(xNew*10+yNew);
						turnState=3;

						for(Integer movementInt : possibleMove)
						{
							if(koordInt==movementInt)
							{
								board.setPiece(x, y, xNew, yNew);
								x=xNew;
								y=yNew;
								turnState=2;
								board.calculateTargets(x, y);
								possibleTarget=board.getPossibleTarget();
								if(possibleTarget.isEmpty())
								{
									gcRed.clearRect(canvasRed.getLayoutX(),canvasRed.getLayoutY(), canvasRed.getWidth(), canvasRed.getHeight());
									turnState=0;
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
								turnState=0;
								//gameState
								if(whoseTurn==Alliance.GOOD)whoseTurn=Alliance.EVIL;
								else whoseTurn=Alliance.GOOD;
							}
						}

						if(turnState==3)
						{
							gcRed.clearRect(canvasRed.getLayoutX(),canvasRed.getLayoutY(), canvasRed.getWidth(), canvasRed.getHeight());
							turnState=0;
						}

						//Sobald ein Piece sich bewegt hat, muessen die gezeichneten Quadrate entfernt werden
						//Canvas wird anschliessend entfernt
						gcGreen.clearRect(canvasGreen.getLayoutX(),canvasGreen.getLayoutY(), canvasGreen.getWidth(), canvasGreen.getHeight());
						break;

					case 2:
						xNew = (int)(event.getX()-board.getIcon().getX())/50; //neue x-Koordinate
						yNew = (int)(event.getY()-board.getIcon().getY())/50; //neue y-Koordinate
						koordInt=(xNew*10+yNew);
						for(Integer targetInt : possibleTarget)
						{
							if(koordInt==targetInt)
							{
								//attacke
								turnState=0;
								if(whoseTurn==Alliance.GOOD)whoseTurn=Alliance.EVIL;
								else whoseTurn=Alliance.GOOD;
								gcRed.clearRect(canvasRed.getLayoutX(),canvasRed.getLayoutY(), canvasRed.getWidth(), canvasRed.getHeight());
							}
						}

						break;
					}
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






