package listener;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import stages.Info;
import stages.Menu;

public class MyWindow implements EventHandler<WindowEvent>
{
	private Menu menu;
	private Info info;

	public MyWindow(Menu menu, Info info)
	{
		this.menu=menu;
		this.info=info;
	}
	
	public void handle(WindowEvent we)
	{
		switch(we.getEventType().toString())
		{
		case "WINDOW_CLOSE_REQUEST":
			closingPrimary();
			break;
		}
	}

	private void closingPrimary() 
	{
		// TODO Auto-generated method stub
		menu.closeStage();
		info.closeStage();
	}
}
