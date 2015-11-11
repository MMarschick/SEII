package pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piece {

	int attackValue, hitPoints, maxHitPoints, abilityCooldown;
	Image characterImage;
	String charactername;
	private Alliance characterAlliance;

	boolean frozen, poisoned, taunt, stealth, splash, rooted, drain, dead;
	Ability ability;
	PieceType pieceT;

	ImageView icon;
	Image piece;

	public Piece(Alliance alliance, PieceType pieceT, String iconPath)
	{
		this.characterAlliance=alliance;
		this.piece = new Image(iconPath);
		this.icon=new ImageView(piece);
		this.pieceT=pieceT;
	}
	//Get-Methode fuer icon (Image) von Piece
	public ImageView getIcon()
	{
		return icon;
	}

	public PieceType getPieceT(){return pieceT;}

	public Image getImage()
	{
		return piece;
	}

	public Alliance getCharacterAlliance()
	{
		return characterAlliance;
	}



	//	public int attack(int thisX, int thisY, int targetX, int targetY);
	//	public int receiveAttack(int damageReceived);
	//	public int freeze(int freezeDuration);
	//	public int poison(int poisonDuration);
}







