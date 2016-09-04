package company;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import db.Employee;

@Stateless(name="EmployeeQueryManager")
public class EmployeeManagerBean implements EmployeeManager{
	@PersistenceContext(unitName="company") private EntityManager manager;

	public void addEmployee(Employee emp) {
		manager.persist(emp);
	}

	public Employee getEmployee(long id) {
		return (Employee)manager.find(Employee.class,id);
	}

	public void removeEmployee(long id) {
		manager.remove(manager.find(Employee.class,id));
	}

	public void updateEmployee(Employee emp) {
		manager.merge(emp);
	}

	public List<Employee> getEmployees() {
		Query query = manager.createQuery("SELECT OBJECT(emp) FROM Employee AS emp");
		List<Employee> emps = query.getResultList();
		return emps;
	}

	public List<Employee> getEmployeesWithSalaryGreaterThan(double salary) {
		Query query = manager.createQuery("SELECT OBJECT(emp) FROM Employee AS emp WHERE emp.salary > ?1");
		query.setParameter(1, salary);
		List<Employee> emps = query.getResultList();
		return emps;
	}

	
}
