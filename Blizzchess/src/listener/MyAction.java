package listener;
import connection.Client;
import game.Board;
import game.TurnHandling;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import pieces.Alliance;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import stages.GameSelect;
import stages.Info;
import stages.Login;
import stages.Menu;
import stages.View;
import tools.GameParser;

public class MyAction implements EventHandler<ActionEvent>
{
	private Login login;
	private GameSelect gameSelect;
	private Client player;
	private Stage primaryStage;
	private BorderPane root;
	private static Board board;
	private Menu menu;
	private Info info;
	
	public static void setBoard(Board board){MyAction.board=board;}
	
	public MyAction(Stage primaryStage, BorderPane root, Board board, Login login, Menu menu, Info info, GameSelect gameSelect, Client player )
	{
		this.primaryStage=primaryStage;
		this.root=root;
		MyAction.board=board;
		this.login=login;
		this.menu=menu;
		this.info=info;
		this.gameSelect=gameSelect;
		this.player=player;
	}
	
	public void handle(ActionEvent ae) 
	{
		String typeObject=" ";
		ComboBox chooseGame = new ComboBox();
		if(ae.getSource().getClass().toString().endsWith("Button"))
		{
			typeObject = ae.getTarget().toString().substring(ae.getTarget().toString().indexOf('\''));
			typeObject=typeObject.substring(1, typeObject.length()-1);
		}
		else if(ae.getSource().getClass().toString().endsWith("ComboBox"))
		{
			chooseGame = (ComboBox)ae.getSource();
			typeObject = "Get Game"; 
		}

		switch(typeObject)
		{
		case "Create":
			createPlayer();
			break;
		case "Cancel":
			cancelCreate();
			break;
		case "Logout":
			logout();
			break;
		case "New Game":
			searchGame();
			break;
		case "Login":
			login();
			break;
		case "Sign in":
			login.showAndWaitNewPlayerStage();
			break;
		case "Get Game":
			getGame(chooseGame);
			break;
		}
	}
	
	private void getGame(ComboBox chooseGame)
	{
		String[] gameData = chooseGame.getSelectionModel().getSelectedItem().toString().split(" ");
		player.setPlayerOne(gameData[1]);
		player.setPlayerTwo(gameData[3]);
		if(gameData[5].equals(player.getPlayerName()))TurnHandling.whoseTurn=Board.getMyAlliance();
		else
		{
			TurnHandling.whoseTurn=Board.getMyAlliance();
			TurnHandling.switchWhoseTurn();
		}
		if(player.getPlayerName().equals(gameData[1])) {player.setOpponentName(gameData[3]); Board.setMyAlliance("GOOD");}
		else{player.setOpponentName(gameData[1]); Board.setMyAlliance("EVIL");}
		newGame();
	}
	
	private void login() 
	{
		login.getActiontarget().setFill(Color.YELLOW);
		login.getActiontarget().setText("connecting...");

		player.setPlayerName(login.getUserTextField().getText());
		player.setPlayerPW(login.getPwBox().getText());

		if(login.getUserTextField().getText().length()>0 && login.getPwBox().getText().length()>0)
		{
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
//				login.showAndWaitNewPlayerStage();
			}
		}
		else
		{
			login.getActiontarget().setFill(Color.RED);
			login.getActiontarget().setText("Some fields are empty");
		}
	}
	
	private void searchGame()
	{
		System.out.println(player.searchGame());
	}
	
	private void newGame() 
	{
		// TODO Auto-generated method stub
		//suche hinzufuegen (neues spiel, neuer gegner)
		board = new Board(root, player.getGame()); //set-Methode
		gameSelect.closeStage();
		primaryStage.show();
		menu.setPosition(primaryStage);
		info.setPosition(primaryStage);	
		View.setEventBoards(board);
	}
	
	private void logout() 
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
	
	private void cancelCreate(){login.closeNewPlayerStage();}
	
	private void createPlayer() 
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
}