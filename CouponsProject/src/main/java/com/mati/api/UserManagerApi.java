package com.mati.api;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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

@Path("/user")
public class UserManagerApi {


	@POST
	@Path("/companyregister")
	@Consumes(MediaType.APPLICATION_JSON)
	public void companyRegister(Company company) throws ApplicationException{
		CompanyLogic companyLogic = new CompanyLogic();
		companyLogic.createCompany(company);
		
	}

	@POST
	@Path("/customerregister")
	@Consumes(MediaType.APPLICATION_JSON)
	public void customerRegister(Customer customer) throws ApplicationException{
		CustomerLogic customerLogic = new CustomerLogic();

		customerLogic.createCustomer(customer);
		
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletResponse resp, User user) throws ApplicationException{
		UserLogic userLogic = new UserLogic();
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

	@DELETE
	public void logout(@Context HttpServletRequest req){

		Cookie[] cookies = req.getCookies();
		String loginData = HttpUtils.getCookieValue(cookies, Constans.COOKIE_KEY);
		Cache.getInstance().removeLogedInUser(loginData);
		HttpUtils.killCookie(cookies, Constans.COOKIE_KEY);
	}
	
	@DELETE
	@Path("/removeuser")
	@Consumes(MediaType.APPLICATION_JSON)
	public void removeUser(User user) throws ApplicationException{
		UserLogic userLogic = new UserLogic();
		userLogic.removeUser(user);
	}
}
