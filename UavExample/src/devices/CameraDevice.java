package devices;

import org.w3c.dom.Element;

public class CameraDevice implements IDevice {
	
	static {
		ReflectionOrientedFactory.register("Camera", CameraDevice.class);
	}
	
	private float fov;
	private int isColor;	
	
	public CameraDevice(Element deviceElement) {
		String strFov = deviceElement.getAttribute("FOV");
		this.fov = Float.parseFloat(strFov);
		
		String strIsColor = deviceElement.getAttribute("IsColor");
		this.isColor = Integer.parseInt(strIsColor);
	}
	
	public void execute(long ms) throws Exception{
		
		// This is an alternate method to get the device
		// Very explicit and clear, yet causes the SystemController to be too long
		// Can be very good if we have a limited amount of objects, and we prefer clarity of the code
		//AutopilotDevice autopilot = SystemController.getInstance().getAutoPilot();
		
		// This is a generic method to extract an object from the SystemController
		// We provide an identifier (currently a String but might as well and maybe.. preferably an Enum)
		// using which we extract the required device
		AutopilotDevice autopilot = (AutopilotDevice) SystemController.getInstance().getDevice("AutoPilot");
		
	}
	
	public float getFov() {
		return fov;
	}
	public void setFov(float fov) {
		this.fov = fov;
	}
	public int isColor() {
		return isColor;
	}
	public void setColor(int isColor) {
		this.isColor = isColor;
	}
	
	
	

}
