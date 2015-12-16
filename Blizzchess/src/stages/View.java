package stages;

import java.util.concurrent.CountDownLatch;

import basal.ViewBasal;
import game.Board;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import listener.MyAction;
import listener.MyMouse;

public class View extends ViewBasal 
{
	//StartUp
	public static final CountDownLatch latch = new CountDownLatch(1);
	public static View startUpTest = null;
	public static View waitForStartUpTest() 
	{
		try {latch.await();} 
		catch (InterruptedException e) {e.printStackTrace();}
		return startUpTest;
	}
	public static void setStartUpTest(View startUpTest0) 
	{
		startUpTest = startUpTest0;
		latch.countDown();
	}
	public View() {setStartUpTest(this);}
	
	public void start(Stage primaryStage) 
	{
		//Definierung der primaryStage + Pane + Scene
		primaryStage.setTitle("Blizzchess - Savants of Warcraft");
		primaryStage.setResizable(false);
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 440, 440);
		primaryStage.setScene(scene);
		
		login.showStage();
			
		//Add Canvas/GraphicsContext
		root.getChildren().addAll(canvasRed, canvasGreen, canvasOrchid);

		//Events hinzufuegen
		this.setEvents(primaryStage, scene, root);
	}
	
}