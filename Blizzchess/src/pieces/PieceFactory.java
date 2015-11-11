package pieces;

public class PieceFactory 
{
	private String pathsG [] = new String [10]; //enthaelt die Images der "guten" Fraktion
	private String pathsE [] = new String [10]; //enthaelt die Images der "boesen" Fraktion
	private PieceType pieceT [] = new PieceType[10]; //enthaelt die MovementPatterns
	
	public PieceFactory()
	{
		pathsG[0] = "peasant.png";
		pathsG[1] = "mountaingiant.png";
		pathsG[2] = "archer.png";
		pathsG[3] = "knight.png";
		pathsG[4] = "chimaera.png";
		pathsG[5] = "mountainking.png";
		pathsG[6] = "driad.png";
		pathsG[7] = "knight.png";
		pathsG[8] = "archer.png";
		pathsG[9] = "mountaingiant.png";
	
		pathsE[0] = "peon.png";
		pathsE[1] = "abomination.png";
		pathsE[2] = "headhunter.png";
		pathsE[3] = "wolfrider.png";
		pathsE[4] = "frostwyrm.png";
		pathsE[5] = "blademaster.png";
		pathsE[6] = "shaman.png";
		pathsE[7] = "wolfrider.png";
		pathsE[8] = "headhunter.png";
		pathsE[9] = "abomination.png";
		
		pieceT[0] = PieceType.PEASANT;
		pieceT[1] = PieceType.TAUNT;
		pieceT[2] = PieceType.ARCHER;
		pieceT[3] = PieceType.KNIGHT;
		pieceT[4] = PieceType.QUEEN;
		pieceT[5] = PieceType.KING;
		pieceT[6] = PieceType.SPECIAL;
		pieceT[7] = PieceType.KNIGHT;
		pieceT[8] = PieceType.ARCHER;
		pieceT[9] = PieceType.TAUNT;
	}
	
	public Piece getPiece(Alliance alliance, int index) 
	{
		if(alliance==Alliance.GOOD)
		{
			return new Piece(alliance, pieceT[index], pathsG[index]);
		}
		return new Piece(alliance, pieceT[index], pathsE[index]);
	}
}
