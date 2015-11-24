package stages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameSelect 
{
	Button newGameBtn, logoutBtn;
	ComboBox<String> openGames;
	Stage selectStage;
	GridPane grid;
	Scene selectScene;
	Text actiontarget; 
	Label opponentIP;
	TextField opponentTextField;														  //userName: Beinhaltet die eigene IP
	HBox hbBtn;
	
	//Getter-Methoden
	public Button getNewGameBtn()
	{
		return newGameBtn;
	}
	public Button getLogoutBtn()
	{
		return logoutBtn;
	}
	public ComboBox<String> getOpenGames()
	{
		return openGames;
	}
	public void setOpenGames(List<String> openGamesList)
	{
		getOpenGames().getItems().clear();
		getOpenGames().getItems().addAll(openGamesList);
	}
	public List<String> createOpenGamesList(String openGamesString)
	{
		List<String> openGamesList = new ArrayList<String>();
		String[] openGames = openGamesString.split(Pattern.quote("|"));
		for(String openGame : openGames)
		{
			openGamesList.add(openGame);
		}
		return openGamesList;
	}
	public HBox getHbBtn()
	{
		return hbBtn;
	}
	public Text getActiontarget()
	{
		return actiontarget;
	}
	//Methoden zum Schlieﬂen und Oeffnen der Stage
	public void closeStage()
	{
		selectStage.close();
	}
	public void showStage()
	{
		selectStage.show();
	}
	
	public boolean isVisible(){
		return selectStage.isShowing();
	}
	
	public GameSelect ()
	{
		//Definierung der Stage
		selectStage = new Stage();
    	selectStage.setTitle("Enter the Battle");
		selectStage.setResizable(false);
		
		//Definierung der Scene/Pane; Setzen der Grund Stage
    	grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		grid.setStyle("-fx-background-image: url('battleSelect.png');");
		selectScene = new Scene(grid, 400, 340);
		selectStage.setScene(selectScene);
		selectStage.setScene(selectScene);
		
		//Fuellen der Pane
		actiontarget = new Text();
		grid.add(actiontarget, 0, 16);

		newGameBtn = new Button("New Game");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_LEFT);
		hbBtn.getChildren().add(newGameBtn);
		grid.add(hbBtn, 0, 15);
		
		openGames = new ComboBox<String>();
		openGames.setPromptText("Select old Game");
		openGames.setVisibleRowCount(5);
		grid.add(openGames, 0, 0);
		
		logoutBtn = new Button("Sign out");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(logoutBtn);
		grid.add(hbBtn, 1, 15);
	}
}
