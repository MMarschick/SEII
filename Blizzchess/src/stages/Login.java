package stages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login 
{
	Button btn;                   //btn: Button fuer die Ausloesung des Logins
	Stage loginStage, newPlayerStage;
	GridPane grid, newPlayerGrid;
	Scene sceneLogin, newPlayerScene;
	//newPlayer
	Label l1,l2,l3, l4;
	TextArea newPlayerInformation;
	TextField newPlayerName;
	PasswordField newPlayerPW, newPlayerPWConfirm;
	Button createBtn, cancelBtn;
	//
	Text actiontarget,scenetitle; //actiontarget: Beinhaltet Hinweisnachricht beim Login
								  //scenetitle: Beinhaltet den Titel der Scene
	Label opponentIP,serverName,userName, pw;  //einfache Labels fuer die dazugehoerigen TextFields
	TextField opponentTextField, serverTextField, userTextField; //opponentIP: Beinhaltet IP des Opponents
	 														  //serverName: Beinhaltet IP des Servers
															  //userName: Beinhaltet die eigene IP
	PasswordField pwBox;		  //pwBox: Beinhaltet das Password des Users
	HBox hbBtn;
	
	//Getter-Methoden
	public Button getBtn()
	{
		return btn;
	}
	public HBox getHbBtn()
	{
		return hbBtn;
	}
	public PasswordField getPwBox()
	{
		return pwBox;
	}
	public Text getActiontarget()
	{
		return actiontarget;
	}
	public TextField getUserTextField()
	{
		return userTextField;
	}
	
	//Getter-Methoden(newPlayer)
	public Label getL4()
	{
		return l4;
	}
	public void setL4()
	{
		l4.setText("Failed to create");
	}
	public Button getCreateBtn()
	{
		return createBtn;
	}
	public Button getCancelBtn()
	{
		return cancelBtn;
	}
	public TextField getNewPlayerName()
	{
		return newPlayerName;
	}
	public PasswordField getNewPlayerPW()
	{
		return newPlayerPW;
	}
	public PasswordField getNewPlayerPWConfirm()
	{
		return newPlayerPWConfirm;
	}
	
	//Methoden zum Schliessen und Oeffnen der Stage
	public void closeStage()
	{
		loginStage.close();
		clearStrings();
	}
	public void showStage()
	{
		loginStage.show();
	}
	
	//Methoden zum Schliessen und Oeffnen der Stage(newPlayer)
	public void closeNewPlayerStage()
	{
		newPlayerStage.close();
		clearStringsNewPlayer();
	}
	public void showAndWaitNewPlayerStage()
	{
		newPlayerStage.showAndWait();
	}

	public boolean isVisible()
	{
		return loginStage.isShowing();
	}
	
	public boolean newPlayerIsVisible()
	{
		return newPlayerStage.isShowing();
	}
	
	public void clearStrings()
	{
		getUserTextField().setText("");
		getPwBox().setText("");
		getActiontarget().setText("");
	}
	
	public void clearStringsNewPlayer()
	{
		if(!newPlayerIsVisible())
		{
			getNewPlayerName().setText("");
			getL4().setText("");
		}
		getNewPlayerPW().setText("");
		getNewPlayerPWConfirm().setText("");
	}
	
	public Login ()
	{
		//Definierung der Stage
		loginStage = new Stage();
    	loginStage.setTitle("Login for Blizzchess");
		loginStage.setResizable(false);
		
		//Definierung der Stage; Modales Fenster
		newPlayerStage = new Stage();
		newPlayerStage.setTitle("New Challenger found");
		newPlayerStage.setResizable(false);
		newPlayerStage.initModality(Modality.APPLICATION_MODAL);
		newPlayerStage.initStyle(StageStyle.UNDECORATED);
		
		//Definierung der Scene/Pane; Setzen der Grund Stage
    	grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		grid.setStyle("-fx-background-image: url('boards//alliances.png');");
		sceneLogin = new Scene(grid, 400, 340);
		scenetitle = new Text("Welcome to Blizzchess");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setFill(Color.CORNFLOWERBLUE);
		grid.add(scenetitle, 0, 3);
		loginStage.setScene(sceneLogin);
		
		//Definierung der Scene/Pane(newPlayer)
		newPlayerGrid = new GridPane();
		newPlayerGrid.setAlignment(Pos.CENTER);
		newPlayerGrid.setHgap(5);
		newPlayerGrid.setVgap(5);
		newPlayerGrid.setPadding(new Insets(25,25,25,25));
		newPlayerGrid.setStyle("-fx-background-image: url('newPlayer.png');");
		newPlayerScene = new Scene(newPlayerGrid, 400, 150);
		newPlayerStage.setScene(newPlayerScene);
		
		
		//Fuellen der Pane
		actiontarget = new Text();
		grid.add(actiontarget, 0, 16);
		
		opponentIP = new Label("Enemy (IP):");
		opponentIP.setTextFill(Color.WHITE);
		grid.add(opponentIP, 0, 6);
		
		opponentTextField = new TextField();
		grid.add(opponentTextField, 3, 6);
		
		serverName = new Label("Server (IP):");
		serverName.setTextFill(Color.WHITE);
		grid.add(serverName, 0, 7);
		
		serverTextField = new TextField();
		grid.add(serverTextField, 3, 7);
		
		userName = new Label("User Name:");
		userName.setTextFill(Color.WHITE);
		grid.add(userName, 0, 14);
		
		userTextField = new TextField();
		grid.add(userTextField, 3, 14);
		
		pw = new Label("Password:");
		pw.setTextFill(Color.WHITE);
		grid.add(pw, 0, 15);
		
		
		
		pwBox = new PasswordField();
		grid.add(pwBox, 3, 15);
			
		btn = new Button("Sign in");
		hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 3, 16);
		
		//Fuellen der Pane(newPlayer)
		l1=new Label("Hello there!");
		l2=new Label("It seems that this is your first visit, Welcome to Blizzchess!");
		l3=new Label("Would you like to join the battle?");
		l4=new Label();
		
		l1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		l1.setTextFill(Color.WHITE);
		l2.setTextFill(Color.WHITE);
		l3.setTextFill(Color.WHITE);
		l4.setTextFill(Color.RED);
		
		newPlayerGrid.add(l1, 1, 0);
		newPlayerGrid.add(l2, 0, 1, 3, 1);
		newPlayerGrid.add(l3, 0, 2, 3, 1);
		newPlayerGrid.add(l4, 1, 6);
		
		createBtn = new Button("Create");
		newPlayerGrid.add(createBtn, 0, 6);
		
		cancelBtn = new Button("Cancel");
		newPlayerGrid.add(cancelBtn, 2, 6);
		
		newPlayerName = new TextField();
		newPlayerName.setPromptText("Your Name");
		newPlayerGrid.add(newPlayerName, 0, 4);
		
		newPlayerPW = new PasswordField();
		newPlayerPW.setPromptText("Your Password");
		newPlayerGrid.add(newPlayerPW, 1, 4);
		
		newPlayerPWConfirm = new PasswordField();
		newPlayerPWConfirm.setPromptText("Confirm Password");
		newPlayerGrid.add(newPlayerPWConfirm, 2, 4);
	}
}
