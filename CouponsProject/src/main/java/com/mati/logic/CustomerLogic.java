package com.mati.logic;

import java.util.Collection;

import com.mati.beans.Customer;
import com.mati.dao.CustomerDao;
import com.mati.enums.ErrorTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.interfaces.daointerfaces.ICustomerDao;
import com.mati.interfaces.logicinterfaces.IClient;
public class CustomerLogic implements IClient{

	private ICustomerDao customerDao;
	
	public CustomerLogic(){		
		
		
		this.customerDao = new CustomerDao();
	}	
	
	public void createCustomer(Customer customer) throws ApplicationException{
		if(customer == null){
			throw new ApplicationException(ErrorTypes.FALSE_CUSTOMER_INFORMATION, "Custommer is null");

		}
		if(customerDao.isCustomerExists(customer.getId())){
			throw new ApplicationException(ErrorTypes.CUSTOMER_ALREADY_EXSISTS, "Custommer Alredy Exists in system. Custumer id = " + customer.getId());

		}
		customerDao.createCustomer(customer);
	}

	public void removeCustomer(long customerId) throws ApplicationException{
		if(!customerDao.isCustomerExists(customerId)){
			throw new ApplicationException(ErrorTypes.CUSTOMER_NOT_FOUND, "Cant find the custumer by id = " + customerId);

		}
		//Removing the customer and history of customer.
		customerDao.removeCustomerAndAllPurchasedCoupons(customerId);
		
	}

	public void updateCustomer(Customer customer) throws ApplicationException{
		if(customer == null){
			throw new ApplicationException(ErrorTypes.FALSE_CUSTOMER_INFORMATION, "Custommer is null");

		}
		if(!customerDao.isCustomerExists(customer.getId())){
			throw new ApplicationException(ErrorTypes.CUSTOMER_NOT_FOUND, "Customer not found. Id = " + customer.getId());

		}

		customerDao.updateCustomer(customer);
	}
	public Customer getCustomer(long customerId) throws ApplicationException{			
		Customer customer = customerDao.getCustomer(customerId);
		if(customer == null){
			throw new ApplicationException(ErrorTypes.CUSTOMER_NOT_FOUND, "Customer not found. Id = " + customerId);

		}

		return customer;

	}
	public Collection<Customer> getAllCustomers() throws ApplicationException{
		Collection<Customer> customers = customerDao.getAllCustomers();
		return customers;
	}	
	
	
	public void login(long customerId, String password) throws ApplicationException {
		if(customerDao.login(customerId, password ))
			return;
		throw new ApplicationException(ErrorTypes.INVALID_USER_NAME_OR_PASSWORD, "Login failed. User name or password is incorrect");

	}

}
