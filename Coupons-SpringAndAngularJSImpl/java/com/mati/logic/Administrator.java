package com.mati.logic;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mati.beans.Company;
import com.mati.beans.Customer;
import com.mati.enums.ErrorTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.interfaces.daointerfaces.ICompanyDao;
import com.mati.interfaces.daointerfaces.ICustomerDao;
import com.mati.interfaces.logicinterfaces.IClient;

@Controller
public class Administrator implements IClient{

	@Autowired
	private ICompanyDao companyDao;
	@Autowired
	private ICustomerDao customerDao;

	public Administrator(){}

	public void createCompany(Company company) throws ApplicationException {
		if(company == null){
			throw new ApplicationException(ErrorTypes.FALSE_COMPANY_INFORMATION, "Company is null");

		}
		if(companyDao.isCompanyExists(company.getId())){
			throw new ApplicationException(ErrorTypes.COMPANY_ALREDY_EXSISTS, "Company with the same name alredy exists.");

		} 
		companyDao.createCompany(company);

	}
	public void removeCompany(long companyId) throws ApplicationException{		
		if(!companyDao.isCompanyExists(companyId)){
			throw new ApplicationException(ErrorTypes.COMPANY_DOES_NOT_EXSIST, "Company not found");

		}
		companyDao.removeCompanyAndCreatedCouponsByCompanyId(companyId);

	}
	public void updateCompany(Company company) throws ApplicationException{
		if(company == null){
			throw new ApplicationException(ErrorTypes.FALSE_COMPANY_INFORMATION, "Company is null");

		}
		if(!companyDao.isCompanyExists(company.getId())){
			throw new ApplicationException(ErrorTypes.COMPANY_DOES_NOT_EXSIST, "Company not found");

		}
		companyDao.updateCompany(company);
	}
	public Collection<Company> getAllCompanies() throws ApplicationException{
		Collection<Company> companies = companyDao.getAllCompanies();
		return companies;

	}
	public Company getCompany(long companyId) throws ApplicationException{		
		Company company = companyDao.getCompany(companyId);		
		if(company == null){
			throw new ApplicationException(ErrorTypes.COMPANY_DOES_NOT_EXSIST, "Company not found");

		}
		return company;

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

	public void login(String userName, String password) throws ApplicationException {
		if(userName.equals("admin") && password.equals("1234")){
			return;
		}
		throw new ApplicationException(ErrorTypes.INVALID_USER_NAME_OR_PASSWORD, "Login failed. User name or password is incorrect"); 
	}




}
