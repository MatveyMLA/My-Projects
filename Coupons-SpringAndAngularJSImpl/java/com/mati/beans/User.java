package com.mati.beans;


import com.mati.enums.UserTypes;

	public class User {
	private long id;
	private String userName;
	private String password;
	private UserTypes type;
	
	public User(){}
		
	public User(String userName, String password, UserTypes type) {
		this.userName = userName;
		this.password = password;
		this.type = type;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserTypes getType() {
		return type;
	}

	public void setType(UserTypes type) {
		this.type = type;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", userType=" + type + "]";
	}
	
}
