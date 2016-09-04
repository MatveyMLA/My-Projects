package devices;
import java.util.Map;


public class SystemController 
{
	private static Map<String, IDevice> devicesMap = null;
	private static SystemController systemController = null;

	public static void init(Map<String, IDevice> devicesInitMap) throws Exception
	{
		if (devicesMap==null)
		{
			devicesMap = devicesInitMap;
		}
		else{
			// This line is a protection against programmer errors, because devicesMap should only be called once
			throw new Exception("You can only init the System controller once");
		}
	}

	private SystemController()
	{
	}

	public static SystemController getInstance() throws Exception
	{
		// If the following condition succedes, means that this is the first time getInstance is being called
		if (systemController == null)
		{
			// If the following condition succedes, means that the programmer forgot to call init() before
			// calling getInstance() (protection).
			if (devicesMap == null)
			{
				throw new Exception("Please initialize the system controller before calling it, forgot to call init()");
			}
			
			// This is the first time getInstance has been called, and init had been called
			systemController = new SystemController();
		}

		return systemController;
	}

	// A generic method which enables "clients" (other devices) to extract Devices from the devicesMap
	// And by that - enables them to interact with each other
	public IDevice getDevice(String type){
		IDevice device = devicesMap.get(type);
		return device;
	}

	// A method of exposing the devices to "clients" (other devices)
//	public AutopilotDevice getAutoPilot(){
//		AutopilotDevice autopilot = (AutopilotDevice) devicesMap.get("AutoPilot");
//		return autopilot;
//	}
//	
//	public CameraDevice getCamera(){
//		CameraDevice camera = (CameraDevice) devicesMap.get("Camera");
//		return camera;
//	}
//	
//	public IDevice getSecret(){
//		return null;
//	}

}
