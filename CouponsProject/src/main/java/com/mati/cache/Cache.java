package com.mati.cache;

import java.util.HashSet;
import java.util.Set;


public class Cache {
	
	private Set<String> loginCache ;
	private static Cache instance;
	
	private Cache(){
		loginCache = new HashSet<>();
	}
		
	public static Cache getInstance(){
		if(instance == null){
			return new Cache();
		}
		return instance;
	}
	
	public void isertToCache(String loginData){
		loginCache.add(loginData);
	}
	
	public boolean isLoggedIn(String loginData) {
		return loginCache.contains(loginData);
	}	
	
	
	public  void cleanCache(){
		
		loginCache = new HashSet<>();
	}
	
	public boolean removeLogedInUser(String loginData){
		return loginCache.remove(loginData);
	}
	
	public  void printCache(){
		for (String string : loginCache) {
			System.out.println(string);
		}
	}
}
