package param;

import javax.annotation.Resource;
import javax.ejb.Stateless;

@Stateless(name = "ParamEJB")
public class Param implements IParam{
	
		@Resource(name = "Message")
		public String text;

		@Resource(name = "Times")
		public Integer times;

		public String getMessage() {
			return text;
		}

		public int getTimes() {
			return times;
		}

	
}
