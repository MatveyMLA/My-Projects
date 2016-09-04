package hello;

import javax.ejb.Remote;

@Remote
public interface IHello
{
	public String sayHello(String name);
}