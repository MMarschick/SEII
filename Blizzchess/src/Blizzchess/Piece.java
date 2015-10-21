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
	
	ImageView icon;
	Image peasant;
	
	
	public Piece(String path){
		peasant = new Image(path);
		icon = new ImageView(peasant);
	}
	
	public ImageView getIcon(){
		return icon;
	}
	
//	public int attack(int thisX, int thisY, int targetX, int targetY);
//	public int receiveAttack(int damageReceived);
//	public int freeze(int freezeDuration);
//	public int poison(int poisonDuration);
	
	
}
