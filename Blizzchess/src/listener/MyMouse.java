package listener;

import connection.Client;
import game.Board;
import game.Game;
import game.TurnHandling;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import pieces.Alliance;
import stages.Info;
import stages.Menu;
import stages.View;

public class MyMouse implements EventHandler<MouseEvent>
{	
	private static Board board;
	private Info info;
	private Menu menu;
	private TurnHandling turn;
	private Client player;
	
	public static void setBoard(Board board){MyMouse.board = board;}

	public MyMouse(Board board, Info info, Menu menu, TurnHandling turn, Client player)
	{
		MyMouse.board=board;
		this.info=info;
		this.menu=menu;
		this.turn=turn;
		this.player=player;
	}
	
	public void handle(MouseEvent me)
	{
		switch(me.getEventType().toString())
		{
		case "MOUSE_MOVED":
			updateInfo(me);
			break;
		case "MOUSE_PRESSED":
			if(me.getSource().getClass().toString().endsWith("ImageView")){getTurn();}
			else{turnExecution(me);System.out.println("handle turn");}
			break;
		}
	}
	
	private void updateInfo(MouseEvent event) 
	{
		View.setxT((int)(event.getX()-board.getIcon().getX())/50);
		View.setyT((int)(event.getY()-board.getIcon().getY())/50);

		if(board.isPiece(View.getxT(), View.getyT())) //befuellen, wenn piece
		{
			info.removeAbilityLabels();
			info.showInfo(board.getPiece(View.getxT(), View.getyT()));
		}
	}
	
	private void getTurn()
	{
		try {
			if(player.waitingTurn()) 
				{
					TurnHandling.switchWhoseTurn();
					board.buildNewBoard(player);
				}
		}catch (Exception e) {e.printStackTrace();}
		if(TurnHandling.whoseTurn == Board.getMyAlliance()){menu.setUpdateTurnMine();}
		else{menu.setUpdateTurnOpponent();}
	}
	
	private void turnExecution(MouseEvent me) 
	{
		// TODO Auto-generated method stub
		//Alliance abfragen und zuweisung anpassen
		if(turn.getWhoseTurn()==board.getMyAlliance())
		{
			try {handleTurn(board, me);} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
		// TODO Auto-generated method stub
//		else
//		{
////			try {Game.waitTurn(player);}
//			try {
//				if(player.waitingTurn()) 
//					{
//						TurnHandling.switchWhoseTurn();
//						board.buildNewBoard(player);
//					}
//			}catch (Exception e) {e.printStackTrace();}
//		}
	}
	
	private void handleTurn(Board board, MouseEvent event) throws InterruptedException 
	{
//		if (turn.getWhoseTurn() == Alliance.EVIL) {System.out.println("evil");}
		if(turn.getWhoseTurn() == Board.getMyAlliance())
		{
			// Switch-Case in Game
			switch (turn.getTurnState().getValue()) {
			case 0: // Spieler ist in einem neuen Spielzug
				turn.state0(event, board);
				break;
			case 1: // Spieler hat ein Piece angeklickt
				turn.state1(event, board, player);
				break;
			case 2: // Piece hat sich bewegt
				turn.state2(event, board, player);
				break;
			}
		} 
	}
}
