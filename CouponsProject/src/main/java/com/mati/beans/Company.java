package com.mati.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Company {
	
	private long id;
	private String name;
	private String password;
	private String email;
 
	public Company(){
		
	}
		
	public Company(String name, String password, String email) {
		
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public Company(long id, String name, String password, String email) {
		
		this(name, password, email);
		this.id = id;		
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Company [ id = " + id + ", name = " + name + ", email = " + email + " ]";
	}
	
	
}
