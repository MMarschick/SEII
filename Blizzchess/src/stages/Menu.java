package stages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Menu {

	private Stage menuStage;
	private BorderPane border;
	private Scene menuScene;
	private Button btn;
	private ImageView endTurn;
	private Image yellow;
	private Image green;
	private Image enemy;

	public Menu() {
		// Definierung der Stage
		menuStage = new Stage();
		menuStage.setResizable(false);
		menuStage.initStyle(StageStyle.UNDECORATED);

		// Definierung der Scene/Pane; Setzen der Grund Stage
		border = new BorderPane();
		border.setStyle("-fx-background-color: #C0C0C0;");
		menuScene = new Scene(border, 450, 60);
		menuStage.setScene(menuScene);
		
<<<<<<< HEAD
		btn = new Button("INFO");
		btn.setStyle("-fx-background-color: GREEN;");
		grid.getChildren().add(btn);
=======
		yellow = new Image("endTurn_yellow.png");
		green = new Image("endTurn_green.png");
		enemy = new Image("endTurn_enemy.png");
		
		endTurn = new ImageView();
		endTurn.setOnMouseClicked(e -> 
        System.out.println("From handler on ImageView: "));
		border.setCenter(endTurn);
		
		
	}

	public void setButtonImage(int x) {
		switch(x){
			case 0:
			case 1:
				endTurn.setImage(yellow);
				break;
			case 2:
				endTurn.setImage(green);
				break;
			case 3:
				endTurn.setImage(enemy);
				
			
		}
>>>>>>> 7eb61cee2ea59eea78963a810b0e8f7c6ad29541
	}

	// gibt zurück ob Button gerade grün ist
//	public boolean getButtonGreen() {
//		return btn.getStyle().equals("-fx-background-color: GREEN;");
//	}

	// wechselt die Farbe des Buttons Rot/Grün
//	public void changeButton() {
//		if (btn.getStyle().equals("-fx-background-color: RED;")) {
//			btn.setStyle("-fx-background-color: GREEN;");
//		} else
//			btn.setStyle("-fx-background-color: RED;");
//	}

	// Getter
	public Button getBtn() {return btn;}
	
	//Setter	
	public void setPosition(Stage stage){
		menuStage.setX((int) stage.getX()+3);
		menuStage.setY((int) stage.getY() + (int) stage.getHeight()-3);
	}

	// Methoden zum Schließen und Öffnen der Stage
	public void closeStage() {menuStage.close();}
	public void showStage() {menuStage.show();}
	public boolean isVisible() {return menuStage.isShowing();}
	
	
}
