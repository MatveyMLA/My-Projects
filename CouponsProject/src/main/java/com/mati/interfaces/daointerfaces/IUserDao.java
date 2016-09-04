package com.mati.interfaces.daointerfaces;

import java.sql.Connection;

import com.mati.beans.Company;
import com.mati.beans.Customer;
import com.mati.beans.User;
import com.mati.enums.UserTypes;
import com.mati.exceptions.ApplicationException;

public interface IUserDao {
	void createCustomerUser(Customer customer, long customerId, Connection connection) throws ApplicationException;
	void createCompanyUser(Company company, long companyId, Connection connection) throws ApplicationException;
	void removeUser(User user) throws ApplicationException;
	void updateUser(User user) throws ApplicationException;
	boolean isValidUser(User user) throws ApplicationException;
	long getClientIdByUser(User user)throws ApplicationException;
	UserTypes getClientType(User user)throws ApplicationException;
	
}
