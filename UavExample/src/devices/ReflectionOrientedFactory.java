package devices;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

public class ReflectionOrientedFactory {
	
	private static Map<String, Class> devicesClassMap = new HashMap<String, Class>();
	
	public static void register(String deviceType, Class deviceClass){
		devicesClassMap.put(deviceType, deviceClass);
	}
	
	public static IDevice create(String name, Element deviceElement){
		IDevice device = null;
		try {
			
			// Getting a class object from the devices class map
			Class cls = devicesClassMap.get(name);
			
			// Creating a new instance of the extracted device, based on a Reflection method called "newInstance()"			
			// THAT'S THE CAUSE WHY YOU WERE ASKED TO PROVIDE A DEFAULT CTOR IN YOUR PROJECT BEANS
			// JACKSON WORKS ON THE SAME SAME PRINICIPLE
			device = (IDevice) cls.newInstance();
			
			// We've added an init method which gives us a FULLY INITIALIZED DEVICE
			device.init(deviceElement);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return device;
	}

}
