package client;



import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import param.IParam;


public class Test {

	public static void main(String[] args){
		InitialContext ctx=getInitialContext();
		Object ref=null;
		try {
			ref = ctx.lookup("ParamEJB/remote");
		} catch (NamingException e) {
			System.out.println("Lookup Failed");
			e.printStackTrace();
		}
		IParam stub=(IParam)PortableRemoteObject.narrow(ref, IParam.class);
		int times=stub.getTimes();
		for(int i=0;i<times;i++)
			System.out.println(stub.getMessage());
	}
	
	public static InitialContext getInitialContext(){
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
