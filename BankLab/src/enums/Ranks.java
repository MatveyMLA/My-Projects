package enums;

public enum Ranks {
	
	REGULAR("regular"),
	GOLD("gold"),
	PLATINUM("platinum");
	
	
	private final String rank;
	
	Ranks(String rank){
		this.rank = rank;
	}
	
	public String getValue(){
		return this.rank;
	}

}
