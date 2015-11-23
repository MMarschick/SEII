package pieces;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piece 
{
	int health, abilityCooldown;
	private Alliance characterAlliance;
	ArrayList<StatusEffect> statusEffects;
	PieceType pieceT;
	ImageView icon;
	Image piece;
	
	//Konstruktor; erstellt ein Piece aus allen übergebenen Daten und erstellt ein Image und eine ImageView aus dem übergebenen iconPath 
	public Piece(int health, int abilityCooldown, Alliance characterAlliance, ArrayList<StatusEffect> statusEffects, PieceType pieceT, String iconPath) 
	{
		this.health = health;
		this.abilityCooldown = abilityCooldown;
		this.characterAlliance = characterAlliance;
		this.statusEffects = statusEffects;
		this.pieceT = pieceT;
		this.piece = new Image(iconPath);
		this.icon = new ImageView(piece);
	}

	//Getter
	public ImageView getIcon(){return icon;}
	public PieceType getPieceT(){return pieceT;}
	public Image getImage(){return piece;}
	public Alliance getCharacterAlliance(){return characterAlliance;}
	public int getHealth() {return health;}
	public int getAbilityCooldown() {return abilityCooldown;}
	public ArrayList<StatusEffect> getStatusEffects() {return statusEffects;}
	public Image getPiece() {return piece;}

	//	public int attack(int thisX, int thisY, int targetX, int targetY);
	//	public int receiveAttack(int damageReceived);
}







