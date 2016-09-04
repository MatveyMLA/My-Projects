package com.mati.interfaces.daointerfaces;

import java.util.Collection;

import com.mati.beans.Customer;
import com.mati.exceptions.ApplicationException;

public interface ICustomerDao {
	
	void createCustomer(Customer customer) throws ApplicationException;
	void removeCustomer(long customerId) throws ApplicationException;
	void updateCustomer(Customer customer) throws ApplicationException;
	Customer getCustomer(long customerId)throws ApplicationException;
	Collection<Customer> getAllCustomers() throws ApplicationException;
	boolean login(long customerId, String password)throws ApplicationException;
	boolean isCustomerExists(long customerId)throws ApplicationException;
	void removeCustomerAndAllPurchasedCoupons(long customerId) throws ApplicationException;
	
}
