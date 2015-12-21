package basal;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import listener.MyAction;
import listener.MyChange;
import listener.MyMouse;
import listener.MyWindow;
import connection.Client;
import game.Board;
import game.TurnHandling;
import stages.GameSelect;
import stages.Info;
import stages.Login;
import stages.Menu;
import tools.RectTool;

public abstract class ViewBasal extends Application
{	
	//Variablen der Stages
	static int x,y,xNew,yNew;	//x/y: Koordinaten von Event/Piece; xNew/yNew: Neue Koordinaten von Piece (durch Event)
	static int xT,yT;			//MouseMovedEvent; change names of var
	protected ArrayList<Integer> possibleMove;
	protected ArrayList<Integer> possibleTarget;
	
	public static int getX() {return x;}
	public static void setX(int x) {ViewBasal.x = x;}
	public static int getY() {return y;}
	public static void setY(int y) {ViewBasal.y = y;}
	public static int getxNew() {return xNew;}
	public static void setxNew(int xNew) {ViewBasal.xNew = xNew;}
	public static int getyNew() {return yNew;}
	public static void setyNew(int yNew) {ViewBasal.yNew = yNew;}
	public static int getxT() {return xT;}
	public static void setxT(int xT) {ViewBasal.xT = xT;}
	public static int getyT() {return yT;}
	public static void setyT(int yT) {ViewBasal.yT = yT;}
	
	//Setzen des Canvas (Zeichner) für Felder auf die eine Figur bewegt werden kann
	protected final RectTool canvasGreen = new RectTool("GREEN");
	
	//Setzen des Canvas (Zeichner) für angreifbare Felder
	protected final RectTool canvasRed = new RectTool("RED");
	
	//Setzen des Canvas (Zeichner) für angeklickets Piece
	protected final RectTool canvasOrchid = new RectTool("DARKORCHID");
	
	//Klassen der Stages
	protected Login login = new Login();
	protected Client player = new Client();
	protected GameSelect gameSelect = new GameSelect();
	protected Menu menu = new Menu();
	protected Info info = new Info();
	protected Board board;
	protected TurnHandling turn = new TurnHandling(canvasGreen, canvasRed, canvasOrchid, menu, possibleMove, possibleTarget);
	
	//Events der Stages
	protected MyAction actionEvent;
	protected MyChange changeEvent;
	protected MyMouse mouseEvent;
	protected MyWindow windowEvent;
	
	public static void setEventBoards(Board board)
	{
		MyAction.setBoard(board);
		MyMouse.setBoard(board);
	}
	
	//Events hinzufuegen zu den Stages
	protected void setEvents(Stage primaryStage, Scene scene, BorderPane root)
	{
		actionEvent = new MyAction(primaryStage, root, board, login, menu, info, gameSelect, player);
		changeEvent = new MyChange(primaryStage, menu, info);
		mouseEvent = new MyMouse(board, info, turn, player);
		windowEvent = new MyWindow(menu, info, player);
		
		//wird Hauptstage verschoben, verschieben sich beide Leisten entsprechend mit
		primaryStage.xProperty().addListener(changeEvent);
		primaryStage.yProperty().addListener(changeEvent);
		
		//Event um die infoStage zu befuellen und anzuzeigen
		scene.setOnMouseMoved(mouseEvent);
		
		//Event Turn procedure
		scene.setOnMousePressed(mouseEvent);
		
		//Stage Close Event
		primaryStage.setOnCloseRequest(windowEvent);
		gameSelect.getSelectStage().setOnCloseRequest(windowEvent);
		
		//new Stage Close
		primaryStage.setOnHidden(windowEvent);
		primaryStage.setOnShown(windowEvent);
//		primaryStage.iconifiedProperty().addListener(changeEvent);
//		primaryStage.maximizedProperty().addListener(changeEvent);
		
		//EventHandler: wenn Login-Button betätigt wird
		login.getLoginBtn().setOnAction(actionEvent);
		
		//Event sign in
		login.getSignInBtn().setOnAction(actionEvent);
		
		//Event create player
		login.getCreateBtn().setOnAction(actionEvent);
		
		//Event cancel creating player
		login.getCancelBtn().setOnAction(actionEvent);
		
		//Event logout player
		gameSelect.getLogoutBtn().setOnAction(actionEvent);
		
		//Event create new Game
		gameSelect.getNewGameBtn().setOnAction(actionEvent);
		
		//Event catch open games
		gameSelect.getOpenGames().setOnAction(actionEvent);
	}
}