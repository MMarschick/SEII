package pieces;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import pieces.Alliance;
import pieces.StatusEffect;

public class Piece 
{
	int health, abilityCooldown;
	private Alliance characterAlliance;
	PieceType pieceT;
	
	ImageView icon;
	Image piece;
	Image freeze=new Image("state\\frozen.png");
	ImageView freezeView= new ImageView(freeze);
	Image poison=new Image("state\\poison.png");
	ImageView poisonView= new ImageView(poison);
	Image avatar=new Image("state\\avatar.png");
	ImageView avatarView= new ImageView(avatar);
	Image taunting=new Image("state\\taunting.png");
	ImageView tauntingView= new ImageView(taunting);
	Image savagery=new Image("state\\savagery.png");
	ImageView savageryView= new ImageView(savagery);
	Image healthImg=new Image("state\\health.png");
	ImageView healthView= new ImageView(healthImg);
	
	Label healthLabel;
	
	List<StatusEffect> statusEffects =new ArrayList<StatusEffect>();
	ObservableList<StatusEffect> obsStatusEffects; 	
	
	//Konstruktor; erstellt ein Piece aus allen übergebenen Daten und erstellt ein Image und eine ImageView aus dem übergebenen iconPath 
	public Piece(int health, int abilityCooldown, Alliance characterAlliance, ObservableList<StatusEffect> statusEffects, PieceType pieceT, String iconPath) 
	{
		this.health = health;
		this.abilityCooldown = abilityCooldown;
		this.characterAlliance = characterAlliance;
		this.pieceT = pieceT;
		this.piece = new Image(iconPath);
		this.icon = new ImageView(piece);
		
		healthLabel=new Label(health>=10?""+health/10+"\n"+health%10:""+health);
		healthLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		healthLabel.setStyle("-fx-line-spacing: -0.4em;");
		healthLabel.setTextFill(Color.BLACK);
		healthLabel.setVisible(true);
		
		freezeView.setVisible(false);
		poisonView.setVisible(false);
		avatarView.setVisible(false);
		tauntingView.setVisible(false);
		savageryView.setVisible(false);
		
		
		obsStatusEffects = FXCollections.observableList(statusEffects);		
		obsStatusEffects.addListener(new ListChangeListener<StatusEffect>() {
			@Override
			public void onChanged(Change change) {
				for (StatusEffect stE : obsStatusEffects) {
					if (stE.getAbility().getStatusName().equals("Frozen"))
						freezeView.setVisible(true);
					else if (stE.getAbility().getStatusName().equals("Poisoned"))
						poisonView.setVisible(true);
					else if (stE.getAbility().getStatusName().equals("Avatar"))
						avatarView.setVisible(true);
					else if (stE.getAbility().getStatusName().equals("Taunting"))
						tauntingView.setVisible(true);
					else if (stE.getAbility().getStatusName().equals("Savage"))
						savageryView.setVisible(true);
				}
			}
		});
		
		//Fehler wird geworfen, aber Programm funktioniert, KP wieso...
		try {
			for (StatusEffect stE : statusEffects) {
				obsStatusEffects.add(new StatusEffect(stE.getAbility(), stE.getRemainingDuration()));
			}
		} catch (Exception e) {
//			System.out.println(e.getStackTrace());
		}
	}
	
	public void flushStatusEffects()
	{
//		System.out.println("lets flush "+obsStatusEffects.size()+" statuseffects");
		for(int i=0;i<obsStatusEffects.size();i++)
		{
			StatusEffect sEffect=obsStatusEffects.get(i);
			if(sEffect.getRemainingDuration()>1)
			{
				switch(sEffect.getAbility())
				{
				case POISON: health-=sEffect.getAbility().getDamage();
				default: sEffect.diminishRemainingDurationByOne(); break;
				}
			}
			else
			{
				switch(sEffect.getAbility())
				{
				case POISON: health-=sEffect.getAbility().getDamage(); break;
				case AVATAR: if(this.health>this.getPieceT().getMaxHealth())this.health=this.getPieceT().getMaxHealth();
				}
				System.out.println(sEffect.getAbility().getStatusName());
				obsStatusEffects.remove(i--);
			}
		}
	}

public void attack(Piece attackingPiece) {
		int dmg=attackingPiece.getPieceT().getAttackValue();
		if(attackingPiece.checkForStatusEffect(Ability.SAVAGERY))dmg+=Ability.SAVAGERY.getDamage();
		health=health-dmg;
		if(health<0)health=0;
	}

public boolean checkForStatusEffect(Ability status)
	{
		boolean contains=false;
		for(StatusEffect sEffect : this.statusEffects)
		{
			if(sEffect.getAbility()==status)
			{
				contains=true;
			}
		}
		return contains;
	}


	//Getter
	public ImageView getIcon(){return icon;}
	public PieceType getPieceT(){return pieceT;}
	public Image getImage(){return piece;}
	public Alliance getCharacterAlliance(){return characterAlliance;}
	public int getHealth() {return health;}
	public int getAbilityCooldown() {return abilityCooldown;}
	public ObservableList<StatusEffect> getObsStatusEffects() {return obsStatusEffects;}
	public Image getPiece() {return piece;}
	public ImageView getFreezeView() {return freezeView;}
	public ImageView getPoisonView() {return poisonView;}
	public ImageView getAvatarView() {return avatarView;}
	public ImageView getTauntingView() {return tauntingView;}
	public ImageView getSavageryView() {return savageryView;}
	public ImageView getHealthView() {return healthView;}
	public Label getHealthLabel() {return healthLabel;}
	public void setHealth(int health) {this.health = health;}

	public void setHealthLabel(int h) {
		healthLabel.setText(h>=10?""+h/10+"\n"+h%10:""+h);
		
	}
}







