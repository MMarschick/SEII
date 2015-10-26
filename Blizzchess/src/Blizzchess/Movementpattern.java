
package Blizzchess;

public enum Movementpattern 
{
	PEASANT (0,1,2,1),
	KING (1,1,1,0),
	DIAGONALLINES (2,2,2,2),
	STRAIGHTLINES (8,8,8,3),
	KNIGHT (2,2,2,4),
	SPECIAL (3,3,3,0),
	QUEEN (4,4,4,0);
	
	
	private final int x; //m�gliche waagrechte Bewegung in Feldern
	private final int y; //m�gliche senkrechte Bewegung in Feldern
	private final int y2; // Bewegung f�r Bauern auf Feld 7
	private final int special; //Sonderbewegung
	
	Movementpattern (int x, int y,int x2, int special ){
		this.x=x;
		this.y=y;
		this.y2=x2;
		this.special=special;
		
	}
	
	int getX(){ return x;}
	int getY(){ return y;}
	int getY2(){ return y2;}
	int getSpecial(){ return special;}
	
}
