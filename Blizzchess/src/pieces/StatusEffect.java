package pieces;

//Enum Statuseffekte; enthält die verantwortliche Ability und die verbleibende Dauer des Effekts
public class StatusEffect 
{
	private Ability ability;
	private int remainingDuration;

	//Konstruktor; wird aufgerufen wenn ein neuer Statuseffekt ausgelöst wird.
	public StatusEffect(Ability ability)
	{
		this.ability=ability;
		this.remainingDuration=ability.getDuration();
	}
	
	//Konstruktor; wird vom Parser aufgerufen, da die verbleibende Dauer gesetzt werden muss
	public StatusEffect(Ability ability, int remainingDuration)
	{
		this.ability=ability;
		this.remainingDuration=remainingDuration;
	}

	//Getter
	public Ability getAbility(){return ability;}
	public int getRemainingDuration(){return remainingDuration;}
	
}
