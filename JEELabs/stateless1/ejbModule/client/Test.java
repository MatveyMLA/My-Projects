package client;


import hello.*;

import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;


public class Test {

	public static void main(String[] args){		

		try {
			// Getting a reference to the JNDI
			InitialContext ctx = getJndiReference();
			Object ref=null;
			
			// Getting a stub from the JNDI, which implements the IHello interface
			ref = ctx.lookup("Yossi/remote");			
			Object x = PortableRemoteObject.narrow(ref, hello.IHello.class);
			
			// Verifying that we successfully got the reference to the remote object 
			if (x!=null)
			{
				// We have a valid stub, which we know - implements IHello
				// And so in order to have access to its methods, we cast it to IHello
				IHello helloStub=(IHello) x;			
				
				// From this point onwards, we treat the "helloStub" object, EXACTLY like a normal
				// local object, call its methods etc
				System.out.println(helloStub.sayHello("Michal"));
			}
			
		} catch (NamingException e) 
		{
			System.out.println("Lookup Failed");
			e.printStackTrace();
		}
		
	}
	
	public static InitialContext getJndiReference(){
		Hashtable<String,String> h=new Hashtable<String,String>();
		h.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		h.put("java.naming.provider.url","localhost");
		try {
			return new InitialContext(h);
		} catch (NamingException e) {
			System.out.println("Cannot generate InitialContext");
			e.printStackTrace();
		}
		return null;
	}

}
