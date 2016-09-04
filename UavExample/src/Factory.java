import org.w3c.dom.Element;

import devices.CameraDevice;
import devices.IDevice;
import jdk.nashorn.internal.runtime.logging.Logger;

public class Factory {
	
	public static IDevice create(String name, Element deviceElement){
		if (name.equals("Camera")){
			return new CameraDevice(deviceElement); 
		} else if(name.equals("Autopilot")){
			
		}
		else{
			
		}
		return null;
	}

}
