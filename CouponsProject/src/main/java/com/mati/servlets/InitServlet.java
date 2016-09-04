package com.mati.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.mati.dao.ConnectionPool;
import com.mati.exceptions.ApplicationException;
import com.mati.threads.DailyCouponExpirationTask;


public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		
		ConnectionPool.getInstance();
		try {
			new DailyCouponExpirationTask().startTask();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

}
