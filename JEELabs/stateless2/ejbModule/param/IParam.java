package param;

import javax.ejb.Remote;

@Remote
public interface IParam{
	
	public String getMessage();
	public int getTimes();
}