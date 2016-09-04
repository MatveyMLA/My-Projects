package com.mati.couponsystem;



import com.mati.dao.ConnectionPool;
import com.mati.enums.UserTypes;
import com.mati.enums.ErrorTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.interfaces.logicinterfaces.IClient;
import com.mati.logic.Administrator;
import com.mati.logic.CompanyLogic;
import com.mati.logic.CustomerLogic;
import com.mati.threads.DailyCouponExpirationTask;

public class CouponSystem{


	private static CouponSystem instance;

	private Administrator admin;
	private CompanyLogic companyLogic;
	private CustomerLogic customerLogic;

	//Task which removes all dated coupons
	private DailyCouponExpirationTask couponExpirationTask;	
	private ConnectionPool connectionPool;

	// Ctor
	private CouponSystem() throws ApplicationException{		

		//Starts and initializes ConnectionPool.
		this.connectionPool = ConnectionPool.getInstance();
		//Starts removing dated coupons
		this.couponExpirationTask = new DailyCouponExpirationTask();
		this.couponExpirationTask.startTask();

		this.admin = new Administrator();
		this.companyLogic = new CompanyLogic();
		this.customerLogic = new CustomerLogic();
	}	

	public static  CouponSystem getInstance() throws ApplicationException{
		if(instance == null){

			instance = new CouponSystem();

		}
		return instance;
	}	

	public IClient login(String userName, String password, UserTypes clientType ) throws ApplicationException{
		return null;
	}

	//This method stops removing dated coupons and closes connection pool
	public void shutdown() {
		this.couponExpirationTask.stopTask();
		this.connectionPool.closeConnections();
		System.exit(0);

	}	
}
