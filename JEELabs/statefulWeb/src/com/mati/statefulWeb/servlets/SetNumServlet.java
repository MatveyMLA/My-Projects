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

public class SetNumServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter respWriter = response.getWriter();

		
		String numString = request.getParameter("num");
		int num = Integer.parseInt(numString);
//		InitialContext ict = getJndiReference();
		
		Object lookup = null;
		try {
			
			InitialContext ict = new InitialContext();
			lookup = ict.lookup("Nums/local");
			
		} catch (NamingException e) {
			e.printStackTrace();
		}

		Object numbersManipulatorStab = PortableRemoteObject.narrow(lookup, com.mati.stateful.INumbers.class);
		
		if(numbersManipulatorStab!=null){
			INumbers numbersManipulator = (INumbers)numbersManipulatorStab;
			numbersManipulator.addNum(num);
			respWriter.append("<h1>" + num + "</h1>");
		}
		
	}

//	private InitialContext getJndiReference(){
//		Hashtable<String,String> h=new Hashtable<String,String>();
//		h.put("java.naming.factory.initial" , "org.jnp.interfaces.NamingContextFactory");
//		h.put("java.naming.provider.url" , "localhost");
//		try {
//			return new InitialContext(h);
//		} catch (NamingException e) {
//			System.out.println("Cannot generate InitialContext");
//			e.printStackTrace();
//		}
//		return null;
//	}
}
