package stages;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Info {

	private Stage infoStage;
	private BorderPane border;
	private Scene infoScene;

	public Info() {
		infoStage = new Stage();
		infoStage.setResizable(false);		
		infoStage.initStyle(StageStyle.UNDECORATED);
		
		BorderPane infoPane = new BorderPane();
		infoScene = new Scene(infoPane, 100, 450);
		infoStage.setScene(infoScene);
		infoStage.setX(1000);
	}
	
	
	// Methoden zum Schlieﬂen und ÷ffnen der Stage
		public void closeStage() {infoStage.close();}
		public void showStage() {infoStage.show();}
		public boolean isVisible() {return infoStage.isShowing();}


}
