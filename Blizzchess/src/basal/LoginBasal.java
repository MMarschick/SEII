package basal;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class LoginBasal 
{
	protected Button btn;                   //btn: Button fuer die Ausloesung des Logins
	protected Stage loginStage, newPlayerStage;
	protected GridPane grid, newPlayerGrid;
	protected Scene sceneLogin, newPlayerScene;
	

	//newPlayer
	protected Label l1,l2,l3, l4;
	protected TextArea newPlayerInformation;
	protected TextField newPlayerName;
	protected PasswordField newPlayerPW, newPlayerPWConfirm;
	protected Button createBtn, cancelBtn;
	
	protected Text actiontarget,scenetitle; //actiontarget: Beinhaltet Hinweisnachricht beim Login
								  //scenetitle: Beinhaltet den Titel der Scene
	protected Label opponentIP,serverName,userName, pw;  //einfache Labels fuer die dazugehoerigen TextFields
	protected TextField opponentTextField, serverTextField, userTextField; //opponentIP: Beinhaltet IP des Opponents
	 														  //serverName: Beinhaltet IP des Servers
															  //userName: Beinhaltet die eigene IP
	protected PasswordField pwBox;		  //pwBox: Beinhaltet das Password des Users
	protected HBox hbBtn;
	
	//Getter-Methoden
	public Button getBtn(){return btn;}
	public HBox getHbBtn(){return hbBtn;}
	public PasswordField getPwBox(){return pwBox;}
	public Text getActiontarget(){return actiontarget;}
	public TextField getUserTextField(){return userTextField;}
	
	//Getter-Methoden(newPlayer)
	public Label getL4(){return l4;}
	public void setL4(){l4.setText("Failed to create");}
	public Button getCreateBtn(){return createBtn;}
	public Button getCancelBtn(){return cancelBtn;}
	public TextField getNewPlayerName(){return newPlayerName;}
	public PasswordField getNewPlayerPW(){return newPlayerPW;}
	public PasswordField getNewPlayerPWConfirm(){return newPlayerPWConfirm;}
	
	//Methoden zum Schliessen und Oeffnen der Stage
	public void closeStage()
	{
		loginStage.close();
		clearStrings();
	}
	public void showStage(){loginStage.show();}
	
	//Methoden zum Schliessen und Oeffnen der Stage(newPlayer)
	public void closeNewPlayerStage()
	{
		newPlayerStage.close();
		clearStringsNewPlayer();
	}
	public void showAndWaitNewPlayerStage(){newPlayerStage.showAndWait();}
	public boolean isVisible(){return loginStage.isShowing();}
	public boolean newPlayerIsVisible(){return newPlayerStage.isShowing();}
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
}
