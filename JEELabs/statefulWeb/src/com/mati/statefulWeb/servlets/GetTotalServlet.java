package com.mati.statefulWeb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mati.stateful.INumbers;

public class GetTotalServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter respWriter = response.getWriter();

		//		InitialContext ict = getJndiReference();

		Object lookup = null;
		try {

			InitialContext ict = new InitialContext();
			lookup = ict.lookup("Nums/local");

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object numbersManipulatorStab = PortableRemoteObject.narrow(lookup, com.mati.stateful.INumbers.class);

		if(numbersManipulatorStab != null){
			INumbers numbersManipulator = (INumbers)numbersManipulatorStab;
			int total = numbersManipulator.getTotal();
			respWriter.append("Total is: " + total);
		}
		else{
			respWriter.append("There is no Total");
		}

	}

	//	private InitialContext getJndiReference(){
	//		Hashtable<String,String> h=new Hashtable<String,String>();
	//		h.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
	//		h.put("java.naming.provider.url","localhost");
	//		try {
	//			return new InitialContext(h);
	//		} catch (NamingException e) {
	//			System.out.println("Cannot generate InitialContext");
	//			e.printStackTrace();
	//		}
	//		return null;
	//	}


}
