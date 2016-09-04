package client;

import java.util.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Query;
import javax.rmi.PortableRemoteObject;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import company.EmployeeManager;
import db.*;


public class Test {

	public static void main(String[] args){
		InitialContext ctx=getInitialContext();
		Object ref=null;
		try {
			ref = ctx.lookup("EmployeeRelationsManager/remote");
		} catch (NamingException e) {
			System.out.println("Lookup Failed");
			e.printStackTrace();
		}
		EmployeeManager empManager=(EmployeeManager)PortableRemoteObject.narrow(ref, EmployeeManager.class);
		
		Dept deptSales = new Dept();
		deptSales.setName("Sales");
		Dept deptFinance = new Dept();
		deptFinance.setName("Finance");

		empManager.addDept(deptFinance);
		empManager.addDept(deptSales);

		Employee empDavid = new Employee();
		empDavid.setFirstName("David");
		empDavid.setLastName("Young");
		empDavid.setSalary(10000);
		empDavid.setDept(empManager.getDept(1));
		empManager.addEmployee(empDavid);
		
		Employee empDana = new Employee();
		empDana.setFirstName("Dana");
		empDana.setLastName("Kore");
		empDana.setSalary(15000);
		empDana.setDept(empManager.getDept(2));
		empManager.addEmployee(empDana);

		Employee empKim = new Employee();
		empKim.setFirstName("Kim");
		empKim.setLastName("Deal");
		empKim.setSalary(12350);
		empKim.setDept(empManager.getDept(1));
		empManager.addEmployee(empKim);

		Dept salesDept = empManager.getDept(1);		
		System.out.println("Sales Dept Employees: \n" + salesDept.getEmployees());
		System.out.println("------------------------------");
		Dept finDept = empManager.getDept(2);		
		System.out.println("Finance Dept Employees: \n" + finDept.getEmployees());
		System.out.println("-------Find by firstName------------");
		System.out.println(empManager.getEmployeesByFirstName("David"));
//       Thnx Amitay! np... good luck keep up the good work :)
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
