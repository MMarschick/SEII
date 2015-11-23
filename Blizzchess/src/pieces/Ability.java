package pieces;

public enum Ability 
{
	HOOK(0, 3, 4, 0), POISON(1, 0, 1, 4), HEAL(100, 3, 1, 0), FREEZE(0, 2, 1, 2), AVATAR(5, 5, 0, 3), /*ROOT(0, 2, 2, 2),*/ WHIRLWIND(3, 1, 1, 0), TAUNT(0, 2, 1, 1), SAVAGERY(2, 2, 4, 2), DEAD(0, 0, 0, 0);
	
	private int damage;
	private int cooldown;
	private int range;
	private int duration;
	
	
	private Ability(int damage, int cooldown, int range, int duration)
	{
		this.damage=damage;
		this.cooldown=cooldown;
		this.range=range;
		this.duration=duration;
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
}
