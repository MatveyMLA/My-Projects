package com.mati.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mati.beans.Customer;
import com.mati.exceptions.ApplicationException;
import com.mati.logic.CustomerLogic;


@RestController
@RequestMapping(value = "/customers")
public class CustomerApi {
	
	@Autowired
	CustomerLogic customerLogic;

	@RequestMapping(method = RequestMethod.POST)
	public void createCustomer(@RequestBody Customer customer) throws ApplicationException{
		customerLogic.createCustomer(customer);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public void updateCustomer(@RequestBody Customer customer) throws ApplicationException{
		customerLogic.updateCustomer(customer);		
	}
	
	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	public @ResponseBody Customer getCustomer(@PathVariable("customerId")long customerId) throws ApplicationException {
		Customer customer = customerLogic.getCustomer(customerId);
		return customer;
	}
	
	@RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
	public void removeCustomer(@PathVariable("customerId")long customerId) throws ApplicationException{
		customerLogic.removeCustomer(customerId);
	}
	
	@RequestMapping(value = "/allcustomers", method = RequestMethod.GET)
	public @ResponseBody Collection<Customer> getAllCustomers() throws ApplicationException{
		Collection<Customer> customers = customerLogic.getAllCustomers();
		return customers;
	}
	
}
