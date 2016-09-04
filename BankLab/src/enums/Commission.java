package enums;

public enum Commission {
	
	REGULAR(0.03f),
	GOLD(0.02f),
	PLATINUM(0.01f);
	
	private final float value;
	
	Commission(float value){
		this.value = value;
	}
	public float getValue(){
		return value;
	}
}
