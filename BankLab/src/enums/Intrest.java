package enums;

public enum Intrest {
	
	REGULAR(0.001f),
	GOLD(0.003f),
	PLATINUM(0.005f);
	
	private final float value;
	
	Intrest(float value){
		this.value = value;
	}
	
	public float getValue(){
		
		return value;
	}
}
