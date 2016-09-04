package utilities;
import java.util.*;
import com.sun.jmx.snmp.Timestamp;

public final class  TimeUtilities {
	
	public static Timestamp getTimeStemp() {
		
		Calendar calendar = Calendar.getInstance();		
		Date now = calendar.getTime();		
		Timestamp currentTimestamp = new Timestamp(now.getTime());
		
		return currentTimestamp;
	}
}
