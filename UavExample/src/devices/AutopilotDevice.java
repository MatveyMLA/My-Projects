package devices;

import org.w3c.dom.Element;

public class AutopilotDevice implements IDevice{

	static {
		ReflectionOrientedFactory.register("Autopilot", AutopilotDevice.class);
	}

	private int speed;

	public AutopilotDevice(int speed) {
		this.speed = speed;
	}
	@Override
	public void init(Element initElement) {
		this.speed =Integer.parseInt(initElement.getAttribute("Speed"));
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}


}
