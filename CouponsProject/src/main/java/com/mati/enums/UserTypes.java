package com.mati.enums;

import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

public enum UserTypes {
	ADMINISTRATOR("Administrator"),
	COMPANY("Company"),
	CUSTOMER("Customer");
	
	private String name;	
	private static Map<String, UserTypes> types;
	//Initialization of the Map types by key = type name and value = UserType
	static {		
		types = new TreeMap<>();
		for(UserTypes type : EnumSet.allOf(UserTypes.class)){
			String name = type.getName();
			types.put(name, type);
		}
		
	}
	//Ctor
	private UserTypes(String name) {
		this.name = name;

	}	
	public String getName() {
		return name;

	}
	//returns UserType according the name
	public static UserTypes getType(String type) {	
		return types.get(type);
	}
	
}
