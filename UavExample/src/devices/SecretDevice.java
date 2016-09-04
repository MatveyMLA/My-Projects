package devices;

public class SecretDevice implements IDevice{
	
	private int specialFeature;

	public SecretDevice(int specialFeature) {
		this.specialFeature = specialFeature;
	}

	public int getSpecialFeature() {
		return specialFeature;
	}

	public void setSpecialFeature(int specialFeature) {
		this.specialFeature = specialFeature;
	}
	
	


	

}
