
package pieces;

public enum Movementpattern 
{
	PEASANT (0,1,2,1),
	KING (1,1,1,0),
	DIAGONALLINES (2,2,2,2),
	STRAIGHTLINES (8,8,8,3),
	KNIGHT (2,2,2,4),
	SPECIAL (3,3,3,0),
	QUEEN (4,4,4,0);
	
	
	private final int x; //mögliche waagrechte Bewegung in Feldern
	private final int y; //mögliche senkrechte Bewegung in Feldern
	private final int y2; // Bewegung für Bauern auf Feld 7
	private final int special; //Sonderbewegung
	
	Movementpattern (int x, int y,int x2, int special ){
		this.x=x;
		this.y=y;
		this.y2=x2;
		this.special=special;
		
	}
	
	public int getX(){ return x;}
	public int getY(){ return y;}
	public int getY2(){ return y2;}
	public int getSpecial(){ return special;}
	
}
