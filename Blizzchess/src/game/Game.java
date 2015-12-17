//handlet switch-case

package game;

import connection.Client;
import stages.View;

public class Game{

	private static Thread game;
	
	public static void waitTurn(Client player) throws InterruptedException
	{
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.setTitle("Information Dialog");
//		alert.setHeaderText(null);
//		alert.setContentText("I have a great message for you!");
//
//		alert.showAndWait();
		
		player.waitForTurn();
		System.out.println("its my turn");
		TurnHandling.switchWhoseTurn();
	}
	
	public static void main(String[] args) 
	{
       game = new Thread(){public void run() {
           javafx.application.Application.launch(View.class);
       }};
       game.start();    
    }
}
