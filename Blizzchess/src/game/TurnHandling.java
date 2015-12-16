package game;

import pieces.Alliance;
import stages.Menu;
import stages.View;
import tools.GameParser;
import tools.RectTool;

import java.util.ArrayList;

import connection.Client;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseEvent;

public class TurnHandling 
{
	private RectTool green;
	private RectTool red;
	private RectTool orchid;
	private Menu menu;
	private int koordInt;
	ArrayList<Integer> possibleMove;
	ArrayList<Integer> possibleTarget;
	
	public static Alliance whoseTurn=Alliance.GOOD;
	IntegerProperty turnState = new SimpleIntegerProperty();
	
	public static void setWhoseTurnEvil(){whoseTurn=Alliance.EVIL;}
	public static void setWhoseTurnGood(){whoseTurn=Alliance.GOOD;}
	public Alliance getWhoseTurn(){return whoseTurn;}
	public IntegerProperty getTurnState(){return turnState;}
	
	public TurnHandling(RectTool green, RectTool red, RectTool orchid, Menu menu,ArrayList<Integer> possibleMove, ArrayList<Integer> possibleTarget)
	{
		this.green=green;
		this.red=red;
		this.orchid=orchid;
		this.menu=menu;
		this.possibleMove=possibleMove;
		this.possibleTarget=possibleTarget;
		
		turnState.addListener(new ChangeListener() 
		{
		      public void changed(ObservableValue observableValue, Object oldValue,
		          Object newValue) {menu.setButtonImage(turnState.getValue());}
		});
		
		turnState.setValue(0);
	}
	
	public void state0(MouseEvent event, Board board)
	{
		System.out.println("case 0    " + whoseTurn);
		View.setX((int) (event.getX() - board.getIcon().getX()) / 50);
		View.setY((int) (event.getY() - board.getIcon().getY()) / 50);
		if (board.isPiece(View.getX(), View.getY())) {
			board.calculateMovement(View.getX(), View.getY());
			board.calculateTargets(View.getX(), View.getY());

			possibleMove = board.getPossibleMove();
			possibleTarget = board.getPossibleTarget();
			turnState.setValue(1);

			for (Integer movementInt : possibleMove) {
				int greenX = movementInt / 10;
				int greenY = movementInt % 10;
				green.drawRect(greenX*50, greenY*50);
			}
			for (Integer targetInt : possibleTarget) {
				int redX = targetInt / 10;
				int redY = targetInt % 10;
				red.drawRect(redX * 50, redY * 50);
			}
			orchid.drawRect(View.getX() * 50, View.getY() * 50);
		}
	}
	
	
	public void state1(MouseEvent event, Board board, Client player)
	{
		View.setxNew((int) (event.getX() - board.getIcon().getX()) / 50); // neue
																	// x-Koordinate
		View.setyNew((int) (event.getY() - board.getIcon().getY()) / 50); // neue
																	// y-Koordinate
		koordInt = (View.getxNew() * 10 + View.getyNew());
		turnState.setValue(3);
		

		for (Integer movementInt : possibleMove) {
			if (koordInt == movementInt) {
				board.setPiece(View.getX(), View.getY(),View.getxNew(),View.getyNew());
				View.setX(View.getxNew());
				View.setY(View.getyNew());
				turnState.setValue(2);
				board.calculateTargets(View.getX(), View.getY());
				possibleTarget = board.getPossibleTarget();
				if (possibleTarget.isEmpty()) {
					red.clearRect();
					turnState.setValue(0);
					break;
				}
				red.clearRect();
				for (Integer targetInt : possibleTarget) {
					int redX = targetInt / 10;
					int redY = targetInt % 10;
					red.drawRect(redX * 50, redY * 50);
				}
			}
		}

		for (Integer targetInt : possibleTarget) {
			if (koordInt == targetInt) {
				red.clearRect();

				// attacke
				board.getPiece(View.getxNew(),View.getyNew()).attack();
				turnState.setValue(0);
//				board.update(View.getxNew(),View.getyNew(), board.getPiece(View.getxNew(),View.getyNew()));
			}
		}

		if (turnState.getValue() == 3) {
			red.clearRect();
			turnState.setValue(0);
		}

		// Sobald ein Piece sich bewegt hat, muessen die
		// gezeichneten Quadrate entfernt werden
		// Canvas wird anschliessend entfernt
		green.clearRect();
		orchid.clearRect();

//		board.update(View.getxNew(),View.getyNew(), board.getPiece(View.getxNew(),View.getyNew()));
//		try {board.flush(player);} 
//		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public void state2(MouseEvent event, Board board, Client player)
	{
		View.setxNew((int) (event.getX() - board.getIcon().getX()) / 50); // neue
		// x-Koordinate
		View.setyNew((int) (event.getY() - board.getIcon().getY()) / 50); // neue
		// y-Koordinate
		koordInt = (View.getxNew() * 10 + View.getyNew());
		for (Integer targetInt : possibleTarget) {
			if (koordInt == targetInt) {
				// attacke
				board.getPiece(View.getxNew(),View.getyNew()).attack();
				board.getPiece(View.getxNew(),View.getyNew()).setHealthLabel(board.getPiece(View.getxNew(),View.getyNew()).getHealth());
				turnState.setValue(0);
				if (whoseTurn == Alliance.GOOD)
					whoseTurn = Alliance.EVIL;
				else
					whoseTurn = Alliance.GOOD;
				red.clearRect();
				
				try {
					player.synchBoard(GameParser.parseString(board));
					board.flush(player);} 
				catch (InterruptedException e) {e.printStackTrace();}
				System.out.println("case 2");
			}
		}
		board.update(View.getxNew(),View.getyNew(), board.getPiece(View.getxNew(),View.getyNew()));
		menu.setButtonImage(2);
	}
}
