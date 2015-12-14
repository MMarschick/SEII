//handlet switch-case

package game;

import connection.Client;
import stages.View;

public class Game{

	private static Thread game;
	
	public static void waitTurn(Client player) throws InterruptedException
	{
		player.waitForTurn();
		System.out.println("its rolling");
		TurnHandling.setWhoseTurnGood();
	}
	
	public static void main(String[] args) 
	{
       game = new Thread(){public void run() {
           javafx.application.Application.launch(View.class);
       }};
       game.start();    
    }
}
