package com.mati.api;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mati.beans.Company;
import com.mati.beans.Customer;
import com.mati.beans.User;
import com.mati.cache.Cache;
import com.mati.enums.UserTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.logic.CompanyLogic;
import com.mati.logic.CustomerLogic;
import com.mati.logic.UserLogic;
import com.mati.util.Constans;
import com.mati.util.HttpUtils;

@RestController
@RequestMapping(value = "/user")
public class UserManagerApi {
	@Autowired
	CompanyLogic companyLogic;
	@Autowired
	CustomerLogic customerLogic;
	@Autowired
	UserLogic userLogic;
	
	@RequestMapping(value = "/companyregister", method = RequestMethod.POST)
	public void companyRegister(@RequestBody Company company) throws ApplicationException{
		companyLogic.createCompany(company);
	}

	@RequestMapping(value = "/customerregister", method = RequestMethod.POST)
	public void customerRegister(@RequestBody Customer customer) throws ApplicationException{
		customerLogic.createCustomer(customer);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody User login(HttpServletResponse resp, @RequestBody User user) throws ApplicationException{
		userLogic.checkUser(user);
		if(userLogic.isAdmin(user)){
			user.setType(UserTypes.ADMINISTRATOR);
			user.setPassword("NONE");
			setCookie(resp, user);
			return user;
		}
		
		long clientId = userLogic.getClientIdByUser(user);
		UserTypes type = userLogic.getClientType(user);
		user.setId(clientId);
		user.setType(type);
		user.setPassword("NONE");
		setCookie(resp, user);
		return user;
	}

	private void setCookie(HttpServletResponse resp, User user) {
		String loginData = user.getUserName();
		Cookie cookie = new Cookie(Constans.COOKIE_KEY, loginData);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		resp.addCookie(cookie);		
	}
	@RequestMapping(method = RequestMethod.DELETE)
	public void logout(HttpServletRequest req){

		Cookie[] cookies = req.getCookies();
		String loginData = HttpUtils.getCookieValue(cookies, Constans.COOKIE_KEY);
		Cache.getInstance().removeLogedInUser(loginData);
		HttpUtils.killCookie(cookies, Constans.COOKIE_KEY);
	}
	
	@RequestMapping(value = "/removeuser",method = RequestMethod.DELETE)
	public void removeUser(@RequestBody User user) throws ApplicationException{
		userLogic.removeUser(user);
	}
}
