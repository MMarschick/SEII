package stages;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Menu {

	private Stage menuStage;
	private BorderPane border;
	private Scene menuScene;
	private ImageView endTurn, updateTurn;
	private Image updateGray;
	private Image updateGreen;
	private Image yellow;
	private Image green;
	private Image enemy;

	public ImageView getUpdateTurn(){return updateTurn;}
	public void setUpdateTurnMine(){updateTurn.setImage(updateGreen);}
	public void setUpdateTurnOpponent(){updateTurn.setImage(updateGray);}
//	public Image getUpdateGray(){return updateGray;}
//	public Image getUpdateGreen()
	
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
		
		updateGray = new Image("boards\\endTurn_enemy.png");
		updateGreen = new Image("boards\\endTurn_green.png");
		
		updateTurn = new ImageView(updateGray);
		border.setLeft(updateTurn);
		
		yellow = new Image("boards\\endTurn_yellow.png");
		green = new Image("boards\\endTurn_green.png");
		enemy = new Image("boards\\endTurn_enemy.png");
		
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
	}

	//Setter	
	public void setPosition(Stage stage){
		menuStage.setX((int) stage.getX()+3);
		menuStage.setY((int) stage.getY() + (int) stage.getHeight()-3);
	}

	// Methoden zum Schlieﬂen und ÷ffnen der Stage
	public void closeStage() {menuStage.close();}
	public void showStage() {menuStage.show();}
	public boolean isVisible() {return menuStage.isShowing();}
	
}
