package pieces;
import java.util.ArrayList;


//Factory zum Erstellen von Pieces, wird verwendet um Pfade nicht in der Klasse Pieces speichern zu müssen
public class PieceFactory 
{
	private String pathsG [] = new String [10]; //enthaelt die Images der "guten" Fraktion
	private String pathsE [] = new String [10]; //enthaelt die Images der "boesen" Fraktion
	private PieceType pieceT [] = new PieceType[10]; //enthaelt die MovementPatterns

	//Konstruktor; füllt die Icon-Path-Arrays und das Piece-Type Arrays mit entsprechenden Daten
	public PieceFactory()
	{
		pathsG[0] = "peasant.png";
		pathsG[1] = "mountainking.png";
		pathsG[2] = "archer.png";
		pathsG[3] = "mountaingiant.png";
		pathsG[4] = "driad.png";
		pathsG[5] = "chimaera.png";
		pathsG[6] = "knight.png";

		pathsE[0] = "peon.png";
		pathsE[1] = "blademaster.png";
		pathsE[2] = "headhunter.png";
		pathsE[3] = "abomination.png";
		pathsE[4] = "shaman.png";
		pathsE[5] = "frostwyrm.png";
		pathsE[6] = "wolfrider.png";
		
		pieceT[0] = PieceType.PEASANT;
		pieceT[1] = PieceType.KING;
		pieceT[2] = PieceType.ARCHER;
		pieceT[3] = PieceType.TAUNT;
		pieceT[4] = PieceType.SPECIAL;
		pieceT[5] = PieceType.QUEEN;
		pieceT[6] = PieceType.KNIGHT;
	}
	
	//Erstellt ein Piece nach den übergebenen Daten und gibt es zurück
	public Piece getPiece(PieceType pieceType, Alliance characterAlliance, int health, int abilityCooldown, ArrayList<StatusEffect> statusEffects)
	{
		String imagePath="";
		switch(characterAlliance)
		{
		case GOOD:
			switch(pieceType)
			{
			case PEASANT: imagePath=pathsG[0];break;
			case KING: imagePath=pathsG[1];break;
			case ARCHER: imagePath=pathsG[2];break;
			case TAUNT: imagePath=pathsG[3];break;
			case SPECIAL: imagePath=pathsG[4];break;
			case QUEEN: imagePath=pathsG[5];break;
			case KNIGHT: imagePath=pathsG[6];break;
			}
		case EVIL:
			switch(pieceType)
			{
			case PEASANT: imagePath=pathsG[0];break;
			case KING: imagePath=pathsG[1];break;
			case ARCHER: imagePath=pathsG[2];break;
			case TAUNT: imagePath=pathsG[3];break;
			case SPECIAL: imagePath=pathsG[4];break;
			case QUEEN: imagePath=pathsG[5];break;
			case KNIGHT: imagePath=pathsG[6];break;
			}
		}
		return new Piece(health, abilityCooldown, characterAlliance, statusEffects, pieceType, imagePath);
	}

}
