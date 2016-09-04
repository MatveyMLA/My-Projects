package db;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="EMPLOYEES")
public class Employee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String firstName;
	private String lastName;
	private double salary;
	private Dept dept;
	
	
	@Id
	@GeneratedValue
	@Column(name="EMP_ID", nullable=false, columnDefinition="integer")
	public long getId() {return id;	}
	public void setId(long id) {this.id = id;}
	
	@Column(name="FIRST_NAME", nullable=false)
	public String getFirstName() {return firstName;	}
	public void setFirstName(String firstName) {this.firstName = firstName;	}
	
	@Column(name="LAST_NAME", nullable=false)
	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;	}
	
	@Column(name="SALARY", nullable=false, columnDefinition="double")
	public double getSalary() {return salary;}
	public void setSalary(double salary) {this.salary = salary;	}
	
//	@Column(name = "DEPT_COL") *** when you are using @manyToOne or OneToMany you never combine it with @column @*** Ok , thnx
// got a tester? yep run it. Ok, got it.	
	@ManyToOne(cascade = {CascadeType.MERGE})
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary + ", dept=" + dept
				+ "]";
	}
	
}

