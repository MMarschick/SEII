package pieces;

public enum Ability 
{
	HOOK(0, 3, 4, 0, ""), POISON(1, 0, 1, 4, "Poisoned"), HEAL(100, 3, 1, 0, ""), 
	FREEZE(0, 2, 1, 2, "Frozen"), AVATAR(5, 5, 0, 3, "Avatar"),WHIRLWIND(3, 1, 1, 0, ""),
	TAUNT(0, 2, 1, 1,"Taunting"), SAVAGERY(2, 2, 4, 2, "Savage");
	
	private int damage;
	private int cooldown;
	private int range;
	private int duration;
	private String statusName;
	
	
	private Ability(int damage, int cooldown, int range, int duration, String statusName)
	{
		this.damage=damage;
		this.cooldown=cooldown;
		this.range=range;
		this.duration=duration;
		this.statusName=statusName;
	}
	
	public int getRange() {
		return range;
	}

	public int getDuration() {
		return duration;
	}

	
	public int getDamage() {
		return damage;
	}

	public int getCooldown() {
		return cooldown;
	}

	public String getStatusName() {
		return statusName;
	}
	
}
