package devices;

import org.w3c.dom.Element;

public interface IDevice {
	
	// A method, used in order to initialize the object during its creation
	public void init(Element initElement);
}
