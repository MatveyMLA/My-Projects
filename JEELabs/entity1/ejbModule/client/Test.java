package client;



import java.util.Hashtable;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import company.EmployeeManager;
import db.Employee;


public class Test {

	public static void main(String[] args){
		InitialContext ctx=getInitialContext();
		Object ref=null;
		try {
			ref = ctx.lookup("EmployeeManager/remote");
		} catch (NamingException e) {
			System.out.println("Lookup Failed");
			e.printStackTrace();
		}
		EmployeeManager stub=(EmployeeManager)PortableRemoteObject.narrow(ref, EmployeeManager.class);
		
		
		//Add new employee
		
		Employee emp1=new Employee();
		emp1.setFirstName("David");
		emp1.setLastName("Young");
		emp1.setDept("Sales");
		emp1.setSalary(10000);
		stub.addEmployee(emp1);
		System.out.println("Employee no'1 was added");
		
		Employee emp2=new Employee();
		emp2.setFirstName("Dana");
		emp2.setLastName("Kore");
		emp2.setDept("Finance");
		emp2.setSalary(15000);
		stub.addEmployee(emp2); 
		System.out.println("Employee no'2 was added");
		System.out.println("______________________________");
		
		// View employee list
		System.out.println("Employee List:");
		emp1=stub.getEmployee(1);
		emp2=stub.getEmployee(2);
		System.out.println(emp1.getId()+": "+emp1.getFirstName()+" Salary="+emp1.getSalary());
		System.out.println(emp2.getId()+": "+emp2.getFirstName()+" Salary="+emp2.getSalary());
		System.out.println("______________________________");
		
		// Update employee
		emp1=stub.getEmployee(1);
		emp1.setSalary(11100);
		stub.updateEmployee(emp1);
		System.out.println("Employee no'1 was updated");

		
		// Remove employee
		stub.removeEmployee(2);
		System.out.println("Employee no'2 was removed");
		System.out.println("______________________________");
		
		// Check updates
		System.out.println("Employee List:");
		emp1=stub.getEmployee(1);
		System.out.println(emp1.getId()+": "+emp1.getFirstName()+" Salary="+emp1.getSalary());
		emp2=stub.getEmployee(2);
		System.out.println("2: "+emp2);
		System.out.println("______________________________");
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
