//weg, alliance attribut von Piece

package pieces;

public enum Alliance 
{
	GOOD("Humans/Elves"), EVIL("Orcs/Undead");
	
	private final String allianceName;
	
	private Alliance(String allianceName)
	{
		this.allianceName=allianceName;
	}

	public String getAllianceName() {return allianceName;}
}
