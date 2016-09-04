package hello;

import javax.ejb.Stateless;

@Stateless(name="Yossi")
public class HelloBean implements IHello
{

	public String sayHello(String name) 
	{
		return "Hello " + name;
	}
	
}
//ejb name - HelloEJB



