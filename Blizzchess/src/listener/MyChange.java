package listener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import stages.Info;
import stages.Menu;
import javafx.stage.Stage;

public class MyChange implements ChangeListener<Number>
{
	private Stage primaryStage;
	private Menu menu;
	private Info info;
	
	public MyChange(Stage primaryStage, Menu menu, Info info)
	{
		this.primaryStage=primaryStage;
		this.menu=menu;
		this.info=info;
	}
	
	public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) 
	{
		menu.setPosition(primaryStage);
		info.setPosition(primaryStage);
	}
}