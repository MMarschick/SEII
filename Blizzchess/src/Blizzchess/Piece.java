

package Blizzchess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piece {
	
	
	
//	int attackValue, hitPoints, maxHitPoints, abilityCooldown;
//	String charactername;
//	Alliance characterAlliance;
//	boolean frozen, poisoned, taunt, stealth, splash, rooted, drain;
//	Ability ability;
//	Movementpattern movementPattern;
	int attackValue, hitPoints, maxHitPoints, abilityCooldown;
	Image characterImage;
	String charactername;
	Alliance characterAlliance;
	
	boolean frozen, poisoned, taunt, stealth, splash, rooted, drain, dead;
	Ability ability;
	Movementpattern movementPattern;
	
	ImageView icon;
	Image piece;
	static String pathsG [] = new String [10]; //enthaelt die Images der "guten" Fraktion
	static String pathsE [] = new String [10]; //enthaelt die Images der "boesen" Fraktion
	static Movementpattern moveG [] = new Movementpattern [10]; //enthaelt die MovementPatterns der "guten" Fraktion
	static Movementpattern moveE [] = new Movementpattern [10]; //enthaelt die MovementPatterns der "boesen" Fraktion
	
	
	
	public Piece(Alliance pathFrakt, int pathIndex, int moveIndex){	//Konstruktor
		if (pathFrakt==Alliance.GOOD)
		{
			piece = new Image(pathsG[pathIndex]);
			icon = new ImageView(piece);
			movementPattern = moveG[moveIndex];
		}
		if (pathFrakt==Alliance.EVIL)
		{
			piece = new Image(pathsE[pathIndex]);
			icon = new ImageView(piece);
			movementPattern = moveE[moveIndex];
		}
	}
	
	//Befuellung der Image-Arrays
		public static void setPaths()
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
		}
		
		public static void setMovePatterns()
		{
			moveG[0] = Movementpattern.PEASANT;
			moveG[1] = Movementpattern.STRAIGHTLINES;
			moveG[2] = Movementpattern.DIAGONALLINES;
			moveG[3] = Movementpattern.KNIGHT;
			moveG[4] = Movementpattern.QUEEN;
			moveG[5] = Movementpattern.KING;
			moveG[6] = Movementpattern.SPECIAL;
			moveG[7] = Movementpattern.KNIGHT;
			moveG[8] = Movementpattern.DIAGONALLINES;
			moveG[9] = Movementpattern.STRAIGHTLINES;;
		
			moveE[0] = Movementpattern.PEASANT;
			moveE[1] = Movementpattern.STRAIGHTLINES;
			moveE[2] = Movementpattern.DIAGONALLINES;
			moveE[3] = Movementpattern.KNIGHT;
			moveE[4] = Movementpattern.QUEEN;
			moveE[5] = Movementpattern.KING;
			moveE[6] = Movementpattern.SPECIAL;
			moveE[7] = Movementpattern.KNIGHT;
			moveE[8] = Movementpattern.DIAGONALLINES;
			moveE[9] = Movementpattern.STRAIGHTLINES;
		}
		
	
	//Get-Methode fuer icon (Image) von Piece
	public ImageView getIcon(){
		return icon;
	}
	
	public Movementpattern getMovementPattern(){return movementPattern;}

	public Image getImage()
	{
		return piece;
	}

	
//	public int attack(int thisX, int thisY, int targetX, int targetY);
//	public int receiveAttack(int damageReceived);
//	public int freeze(int freezeDuration);
//	public int poison(int poisonDuration);
}







