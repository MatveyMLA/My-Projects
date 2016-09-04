package company;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import db.Employee;

@Stateless(name = "EmployeeManager")
public class EmployeeManagerBean implements EmployeeManager{

	@PersistenceContext(unitName = "company")
	private EntityManager manager;


	public void addEmployee(Employee emp) {
		manager.persist(emp);

	}

	public Employee getEmployee(long id) {
		Employee emp = manager.find(Employee.class, id);
		return emp;
	}

	public void updateEmployee(Employee emp) {
		manager.merge(emp);

	}

	public void removeEmployee(long id) {
		Employee emp = getEmployee(id);
		if(emp != null){
			manager.remove(emp);
		}
	}


}
