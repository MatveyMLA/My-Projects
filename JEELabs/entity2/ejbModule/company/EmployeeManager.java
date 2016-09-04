package company;


import java.util.List;

import javax.ejb.Remote;

import db.*;

@Remote
public interface EmployeeManager {
	public void addEmployee(Employee emp);
	public Employee getEmployee(long id);
	public void updateEmployee(Employee emp);
	public void removeEmployee(long id); 
	public Dept getDept(long id);
	public void addDept(Dept dept);
	public List<Employee> getEmployeesByFirstName(String firstName);
}
