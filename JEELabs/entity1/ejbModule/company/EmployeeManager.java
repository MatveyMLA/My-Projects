package company;

import java.util.List;

import javax.ejb.Remote;

import db.Employee;

@Remote
public interface EmployeeManager {
	public void addEmployee(Employee emp);
	public Employee getEmployee(long id);
	public void updateEmployee(Employee emp);
	public void removeEmployee(long id); 
}
