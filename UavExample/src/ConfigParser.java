import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigParser {
	
	public static NodeList parseXmlFile(String fullFilePathName) throws Exception{
		File file = new File(fullFilePathName);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
				
		NodeList devicesList = doc.getElementsByTagName("Device");
		return devicesList;
	}

}
