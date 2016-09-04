import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import devices.IDevice;
import devices.SystemController;

public class Main {
	
	public static void main(String[] args) {
		
		try {
			// Parsing the xml file, getting a list of elements in a Nodelist structure
			NodeList list = ConfigParser.parseXmlFile("c:/tmp/UAVConfig.xml");
			
			Map<String, IDevice> devicesMap = new HashMap<String, IDevice>();
			
			// Iterating all elements in order to create a list of devices 
			for (int index = 0; index < list.getLength(); index++) {
				
				// item() returns a pointer of type Node.
				// Node is a base class which hasn't got the getAttribute() method that we need, and so
				// we cast it to Element, which extends Node and HAS the getAttribute() method.
				Element currentDeviceElement = (Element) list.item(index); 
				
				// Extracting the device name
				String name = currentDeviceElement.getAttribute("Name");
				
				// Calling a factory method in order to create the device which is described in the current
				// element.
				IDevice device = Factory.create(name, currentDeviceElement);
				
				
				devicesMap.put(name, device);
			}
			
			// Initializing the system controller, so it'll have all the data (Devices) it needs
			// in order to provide service
			SystemController.init(devicesMap);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
