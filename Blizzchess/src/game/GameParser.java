package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pieces.*;


//Spiel Parser, kann sowohl den aktuellen Spielstand in einen String parsen, als auch aus einem solchen String für ein neues Board dessen Arrays füllen
public class GameParser 
{
	//Standart-ParseString; wird für den Aufbau eines neuen Boards zu Beginn eines neuen Spiels verwendet
	public static final String DEFAULT_STRING=""
			+ "~0-0-t-e-6-0-f_3+p_2~0-1-p-e-2-0-f_7~0-7-p-g-2-0-~0-8-t-g-6-0-"
			+ "~1-0-n-e-3-0-a_4+t_8~1-1-p-e-2-0-f_2+p_3+t_5~1-7-p-g-2-0-~1-8-n-g-3-0-"
			+ "~2-0-a-e-3-0-f_3+s_5+t_4~2-1-p-e-2-0-~2-7-p-g-2-0-~2-8-a-g-3-0-"
			+ "~3-0-q-e-4-0-~3-1-p-e-2-0-~3-7-p-g-2-0-~3-8-q-g-4-0-"
			+ "~4-0-k-e-10-0-~4-1-p-e-2-0-~4-7-p-g-2-0-~4-8-k-g-10-0-"
			+ "~5-0-s-e-3-0-~5-1-p-e-2-0-~5-7-p-g-2-0-~5-8-s-g-3-0-"
			+ "~6-0-a-e-3-0-s_3~6-1-p-e-2-0-~6-7-p-g-2-0-~6-8-a-g-3-0-"
			+ "~7-0-n-e-3-0-~7-1-p-e-2-0-~7-7-p-g-2-0-~7-8-n-g-3-0-"
			+ "~8-0-t-e-6-0-~8-1-p-e-2-0-~8-7-p-g-2-0-~8-8-t-g-6-0-";

	public static String parsePiece(Piece currPiece)
	{
		String parseString="";
		switch(currPiece.getPieceT())
		{
		case PEASANT: parseString+="p";break;
		case KING: parseString+="k";break;
		case ARCHER: parseString+="a";break;
		case TAUNT: parseString+="t";break;
		case SPECIAL: parseString+="s";break;
		case QUEEN: parseString+="q";break;
		case KNIGHT: parseString+="n";break;
		}
		parseString+="-";
		switch(currPiece.getCharacterAlliance())
		{
		case GOOD: parseString+="g";break;
		case EVIL: parseString+="e";break;
		}
		parseString+="-"+currPiece.getHealth();
		parseString+="-"+currPiece.getAbilityCooldown();
		parseString+="-";
		if(!(currPiece.getStatusEffects().isEmpty()))
		{
			for(StatusEffect se : currPiece.getStatusEffects())
			{
				switch(se.getAbility())
				{
				case POISON: parseString+="p"+"_"+se.getRemainingDuration();break;
				case FREEZE: parseString+="f"+"_"+se.getRemainingDuration();
				case AVATAR: parseString+="a"+"_"+se.getRemainingDuration();
				case TAUNT: parseString+="t"+"_"+se.getRemainingDuration();
				case SAVAGERY: parseString+="s"+"_"+se.getRemainingDuration();
				}
				parseString+="+";
			}
		}
		return parseString;
	}

	public static String parseString(Board board)
	{
		Piece[][] felder=board.getFelder();
		String parseString="";
		Piece currPiece;
		for(int x=0;x<9;x++)
		{
			for(int y=0;y<9;y++)
			{
				if((currPiece=felder[x][y])!=null)
				{
					parseString+="~"+x+"-"+y+"-";
					parseString+=parsePiece(currPiece);
				}
			}	
		}
		return parseString;
	}

	public static void parseBoard(String parseString, Piece[][] felder)
	{
		String[] pieces = parseString.split(Pattern.quote("~"));

		for(String pieceString : pieces)
		{
			if(pieceString!=pieces[0])
			{
				String[] attributes=pieceString.split(Pattern.quote("-"));
				int xk=Integer.parseInt(attributes[0]);
				int yk=Integer.parseInt(attributes[1]);
				PieceType pt=null;
				switch(attributes[2])
				{
				case "p": pt=PieceType.PEASANT;break;
				case "k": pt=PieceType.KING;break;
				case "a": pt=PieceType.ARCHER;break;
				case "t": pt=PieceType.TAUNT;break;
				case "s": pt=PieceType.SPECIAL;break;
				case "q": pt=PieceType.QUEEN;break;
				case "n": pt=PieceType.KNIGHT;break;
				}
				Alliance characterAlliance=null;
				switch(attributes[3])
				{
				case "g": characterAlliance= Alliance.GOOD;break;
				case "e": characterAlliance=Alliance.EVIL;break;
				}
				int health=Integer.parseInt(attributes[4]);
				int abilityCooldown=Integer.parseInt(attributes[5]);
				List<StatusEffect> statusEffects=new ArrayList<StatusEffect>();
				ObservableList<StatusEffect> obsStatusEffects=FXCollections.observableList(statusEffects);
				if(attributes.length>6)
				{
					String[] effectList=attributes[6].split(Pattern.quote("+"));
					for(int si=0;si<effectList.length;si++)
					{
						String[] effectAttributes=effectList[si].split(Pattern.quote("_"));
						int remainingDuration=Integer.parseInt(effectAttributes[1]);
						switch(effectAttributes[0])
						{
						case "p": obsStatusEffects.add(new StatusEffect(Ability.POISON, remainingDuration));break;
						case "f": obsStatusEffects.add(new StatusEffect(Ability.FREEZE, remainingDuration));break;
						case "a": obsStatusEffects.add(new StatusEffect(Ability.AVATAR, remainingDuration));break;
						case "t": obsStatusEffects.add(new StatusEffect(Ability.TAUNT, remainingDuration));break;
						case "s": obsStatusEffects.add(new StatusEffect(Ability.SAVAGERY, remainingDuration));break;
						}
						// (.Y.)

					}
				}
				PieceFactory pf=new PieceFactory();
				felder[xk][yk]=pf.getPiece(pt, characterAlliance, health, abilityCooldown, obsStatusEffects);
			}
		}
	}
}
