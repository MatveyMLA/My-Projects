package com.mati.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mati.exceptions.ApplicationException;
import com.mati.threads.DailyCouponExpirationTask;

@Component
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DailyCouponExpirationTask dailyCouponExpirationTask;
	
	@Override
	public void init() throws ServletException {
		
		try {
			dailyCouponExpirationTask.startTask();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

}
