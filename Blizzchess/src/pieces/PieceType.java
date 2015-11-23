package pieces;

//Enum zu den verschiedenen Piece-Typen
public enum PieceType 
{
	PEASANT (1,1,1,2, "Peasant", "Peon", null, null),
	KING (1,4,2,10, "Mountain King", "Blademaster", Ability.AVATAR, Ability.WHIRLWIND),
	ARCHER(8,3,1,3, "Archer", "Hunter", null, Ability.SAVAGERY),
	TAUNT(8,2,1,6, "Mountain Giant", "Abomination", Ability.TAUNT, Ability.HOOK),
	KNIGHT (0,5,2,3, "Paladin", "Wolf Rider", null, null),
	SPECIAL (3,4,1,3, "Dryad", "Shaman", Ability.POISON, Ability.HEAL),
	QUEEN (4,4,3,4, "Chimera", "Frostwyrm", Ability.FREEZE, Ability.FREEZE);
	
	private final int range; //Attribut zur Bestimmung der Bewegungsreichweite in alle Richtungen in Feldern (Bauern und Springer sind Sonderfälle)
	private final int directions; //Zugelassene Zugrichtungen: 1. Bauer 2. Nur gerade Linien 3. Nur diagonale Linien 4. Alle Richtungen 5. Springer
	private int attackValue, maxHealth;
	private String nameGood, nameEvil;
	private Ability abilityGood, abilityEvil;
	
	//Konstruktor
	private PieceType(int range, int directions,int attackValue, int maxHealth, String nameGood, String nameEvil, Ability abilityGood, Ability abilityEvil) 
	{
		this.range = range;
		this.directions = directions;
		this.attackValue=attackValue;
		this.maxHealth=maxHealth;
		this.nameGood = nameGood;
		this.nameEvil = nameEvil;
		this.abilityGood = abilityGood;
		this.abilityEvil = abilityEvil;
	}
	
	//Getter
	public int getRange(){ return range;}
	public int getDirections(){ return directions;}
	public String getNameGood() { return nameGood;}
	public String getNameEvil() { return nameEvil;}
	public Ability getAbilityGood() { return abilityGood;}
	public Ability getAbilityEvil() { return abilityEvil;}
}
