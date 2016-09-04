package db;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="DEPT")
public class Dept implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private List<Employee> employees = new ArrayList<Employee>();
	// define employee collection here

	@Id
	@GeneratedValue
	@Column(name="DEPT_ID", nullable=false, columnDefinition="integer")
	public long getId() {return id;	}

	public void setId(long id) {this.id = id;}

	@Column(name="NAME")
	public String getName() {return name;	}
	public void setName(String name) {this.name = name;	}

	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy = "dept",
			fetch = FetchType.EAGER)
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	// add set/get for your CMR field with relevant annotation settings here

	@Override
	public String toString() {
		return "Dept [name=" + name + "]";
	}
	
	

}

