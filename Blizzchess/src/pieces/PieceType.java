package pieces;

public enum PieceType 
{
	PEASANT (1,1),
	KING (1,4),
	ARCHER(8,3),
	TAUNT(8,2),
	KNIGHT (0,5),
	SPECIAL (3,4),
	QUEEN (4,4);
	
	
	private final int range; //Attribut zur Bestimmung der Bewegungsreichweite in alle Richtungen in Feldern (Bauern und Springer sind Sonderfälle)
	private final int directions; //Zugelassene Zugrichtungen: 1. Bauer 2. Nur gerade Linien 3. Nur diagonale Linien 4. Alle Richtungen 5. Springer
	
	private PieceType(int range, int directions)
	{
		this.range=range;
		this.directions=directions;
	}
	
	public int getRange(){ return range;}
	public int getDirections(){ return directions;}
	
}
