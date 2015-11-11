package stages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Menu 
{
	
	Stage menuStage;
	GridPane grid;
	Scene menuScene;
	Button btn;
	
	//Getter
	public Button getBtn()
	{
		return btn;
	}
	
	//Methoden zum Schließen und Oeffnen der Stage
	public void closeStage()
	{
		menuStage.close();
	}
	public void showStage()
	{
		menuStage.show();
	}
	
	public boolean isVisible(){
		return menuStage.isShowing();
	}
	
	//wechselt die Farbe des Buttons Rot/Grün
	public void changeButton(){
		if (btn.getStyle().equals("-fx-background-color: RED;")){
			btn.setStyle("-fx-background-color: GREEN;");
		}
		else
			btn.setStyle("-fx-background-color: RED;");
	}
	
	//gibt zurück ob Button gerade grün ist
	public boolean getButtonGreen(){
		return btn.getStyle().equals("-fx-background-color: GREEN;");
	}
	
	public Menu ()
	{
		//Definierung der Stage
		menuStage = new Stage();
		menuStage.setResizable(false);
		menuStage.initStyle(StageStyle.UNDECORATED);
		menuStage.setX(457);
		menuStage.setY(560);
		
		//Definierung der Scene/Pane; Setzen der Grund Stage
    	grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
//		grid.setStyle("-fx-background-image: url('alliances.png');");
		grid.setStyle("-fx-background-color: #C0C0C0;");
		menuScene = new Scene(grid, 450, 60);
		menuStage.setScene(menuScene);
		
		btn = new Button("INFO");
		btn.setStyle("-fx-background-color: RED;");
		grid.getChildren().add(btn);
	}
}
