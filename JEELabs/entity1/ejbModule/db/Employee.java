package db;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEES")
public class Employee implements Serializable{
		
	private long id;
	private String firstName;
	private String lastName;
	private double salary;
	private String dept;
	
	@Id
	@GeneratedValue
	@Column(name="EMP_ID", nullable=false, columnDefinition="integer")
	public long getId() {return id;	}
	public void setId(long id) {this.id = id;}
	
	@Column(name="DEPARTMENT", nullable=false)
	public String getDept() {return dept;}
	public void setDept(String dept) {this.dept = dept;	}
	
	@Column(name="FIRST_NAME", nullable=false)
	public String getFirstName() {return firstName;	}
	public void setFirstName(String firstName) {this.firstName = firstName;	}
	
	@Column(name="LAST_NAME", nullable=false)
	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;	}
	
	@Column(name="SALARY", nullable=false, columnDefinition="double")
	public double getSalary() {return salary;}
	public void setSalary(double salary) {this.salary = salary;	}
	
}

