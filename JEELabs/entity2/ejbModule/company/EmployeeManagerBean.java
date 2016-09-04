package company;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import db.*;

@Stateless(name = "EmployeeRelationsManager")
public class EmployeeManagerBean implements EmployeeManager{
	
	@PersistenceContext(unitName="company") 
	private EntityManager manager;

	public void addEmployee(Employee emp) {
		manager.persist(emp);
	}

	public Employee getEmployee(long id) {
		return manager.find(Employee.class,id);
	}

	public void removeEmployee(long id) {
		manager.remove(manager.find(Employee.class,id));
	}

	public void updateEmployee(Employee emp) {
		manager.merge(emp);
	}
	
	public Dept getDept(long id) {
		return manager.find(Dept.class,id);
	}

	public void addDept(Dept dept){
		manager.persist(dept);
	}
	
	public List<Employee> getEmployeesByFirstName(String firstName){ 
		Query query = manager.createQuery("select emp from Employee as emp where firstName = ?1 order by firstName");
		query.setParameter(1, firstName);
		List<Employee> result = query.getResultList();
		return result;
		
	}

}
