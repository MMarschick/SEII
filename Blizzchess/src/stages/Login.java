package stages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login 
{
	Button btn;                   //btn: Button fuer die Ausloesung des Logins
	Stage loginStage;
	GridPane grid;
	Scene sceneLogin;
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
	//Methoden zum Schlieﬂen und Oeffnen der Stage
	public void closeStage()
	{
		loginStage.close();
	}
	public void showStage()
	{
		loginStage.show();
	}
	
	public boolean isVisible(){
		return loginStage.isShowing();
	}
	
	public Login ()
	{
		//Definierung der Stage
		loginStage = new Stage();
    	loginStage.setTitle("Login for Blizzchess");
		loginStage.setResizable(false);
		
		//Definierung der Scene/Pane; Setzen der Grund Stage
    	grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		grid.setStyle("-fx-background-image: url('alliances.png');");
		sceneLogin = new Scene(grid, 400, 340);
		loginStage.setScene(sceneLogin);
		scenetitle = new Text("Welcome to Blizzchess");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		scenetitle.setFill(Color.RED);
		grid.add(scenetitle, 0, 2);
		loginStage.setScene(sceneLogin);
		
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
	}
}
