import java.util.Map;

import devices.IDevice;


public class SystemController 
{
	private static Map<String, IDevice> devicesMap = null;
	private static SystemController systemController = null;
	
	public static void init(Map<String, IDevice> devicesInitMap)
	{
		if (devicesMap==null)
		{
			devicesMap = devicesInitMap;
		}
	}
	
	private SystemController()
	{
	}
	
	public static SystemController getInstance() throws Exception
	{
		if (systemController == null)
		{
			if (devicesMap == null)
			{
				throw new Exception("Please initialize the system controller before calling it");
			}
			systemController = new SystemController();
		}
		
		return systemController;
	}
	
	
	
	

}
