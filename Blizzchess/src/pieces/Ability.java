package pieces;

public enum Ability 
{
	HOOK(0,0), POISON(0,0), HEAL(0,0), AWARENESS(0,0), FREEZE(0,0), AVATAR(0,0), ROOT(0,0), WHIRLWIND(0,0);
	
	private int damage;
	private int cooldown;
	
	private Ability(int damage, int cooldown)
	{
		this.damage=damage;
		this.cooldown=cooldown;
	}
	
	public int getDamage() {
		return damage;
	}

	public int getCooldown() {
		return cooldown;
	}
}
