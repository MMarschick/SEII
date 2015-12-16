package listener;

import connection.Client;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import stages.Info;
import stages.Menu;

public class MyWindow implements EventHandler<WindowEvent>
{
	private Menu menu;
	private Info info;
	private Client player;

	public MyWindow(Menu menu, Info info, Client player)
	{
		this.menu=menu;
		this.info=info;
		this.player=player;
	}
	
	public void handle(WindowEvent we)
	{
		switch(we.getEventType().toString())
		{
		case "WINDOW_CLOSE_REQUEST":
			closingPrimary();
			break;
			//Showing/Hiding-Event
		}
	}

	private void closingPrimary() 
	{
		// TODO Auto-generated method stub
		menu.closeStage();
		info.closeStage();
		player.checkLogout();
	}
}
