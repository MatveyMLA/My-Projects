package com.mati.enums;

import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public enum CouponTypes {

	FOOD("Food"),
	TRAVEL("Travel"),
	CLOTHES("Clothes"),
	ELECTRONICS("Electronics"),
	MEDICINE("Medicine"),
	ENTERTAINMENT("Entertainment"),
	EDUCATION("Education"),
	GENERAL("General");

	private String name;	
	private static Map<String, CouponTypes> types;
	
	//Initialization of the Map types by key = type name and value = CouponType
	static {		
		types = new TreeMap<>();
		for(CouponTypes type : EnumSet.allOf(CouponTypes.class)){
			String name = type.getName();
			types.put(name, type);
		}
		
	}
	//Ctor
	private CouponTypes(String name) {
		this.name = name;

	}	
	CouponTypes(){}
	
	public String getName() {
		return name;

	}
	public void setName(String name) {
		this.name= name;
		
	}
	//returns CouponType according the name
	public static CouponTypes getType(String type) {	
		return types.get(type);
	}
}
