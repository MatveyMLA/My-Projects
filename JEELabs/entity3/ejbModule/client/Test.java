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
			ref = ctx.lookup("EmployeeQueryManager/remote");
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

		Employee emp3=new Employee();
		emp3.setFirstName("Kim");
		emp3.setLastName("Deal");
		emp3.setDept("Sales");
		emp3.setSalary(12350);
		stub.addEmployee(emp3); 
		System.out.println("Employee no'3 was added");
		
		Employee emp4=new Employee();
		emp4.setFirstName("Dan");
		emp4.setLastName("Wild");
		emp4.setDept("Finance");
		emp4.setSalary(19150);
		stub.addEmployee(emp3); 
		System.out.println("Employee no'4 was added");
		System.out.println("______________________________");
		
		// View employee list
		System.out.println("Employee List:");
		List<Employee>  employees=stub.getEmployees();
		for(Employee emp:employees){			
			System.out.println(emp.getId()+": "+emp.getFirstName()+" Salary="+emp.getSalary());
		}
		System.out.println("______________________________");
		
		// View employees with salary greater than 10,000
		System.out.println("Employee with salary greater than 10,000:");
		employees=stub.getEmployeesWithSalaryGreaterThan(10000);
		for(Employee empl:employees){
			System.out.println(empl.getId()+": "+empl.getFirstName()+" Salary="+empl.getSalary());
		}
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
