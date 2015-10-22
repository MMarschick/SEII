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
	
	public Piece(Alliance pathFrakt, int pathIndex){
		if (pathFrakt==Alliance.GOOD)
		{
			piece = new Image(pathsG[pathIndex]);
			icon = new ImageView(piece);
		}
		if (pathFrakt==Alliance.EVIL)
		{
			piece = new Image(pathsE[pathIndex]);
			icon = new ImageView(piece);
		}
	}
	
	//Get-Methode fuer icon (Image) von Piece
	public ImageView getIcon(){
		return icon;
	}
	
//	public int attack(int thisX, int thisY, int targetX, int targetY);
//	public int receiveAttack(int damageReceived);
//	public int freeze(int freezeDuration);
//	public int poison(int poisonDuration);
	
	
}
