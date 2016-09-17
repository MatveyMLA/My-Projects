package com.mati.util;

import javax.servlet.http.Cookie;

public class HttpUtils {

	public static String getCookieValue(Cookie[] cookies, String key){
		if(cookies != null && cookies.length > 0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(key)){
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	public static void killCookie(Cookie[] cookies, String key){
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(key)){
				cookie.setMaxAge(0);
			}
		}
	}
	
	public static boolean isLoginCookieExist(Cookie[] cookies, String key){
		if(cookies != null && cookies.length > 0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(key)){					
					return true;
				}
			}
		}
		return false;
	}
}
