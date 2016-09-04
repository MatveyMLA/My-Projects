package com.mati.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mati.beans.Customer;
import com.mati.exceptions.ApplicationException;
import com.mati.logic.CustomerLogic;

@Path("/customers")
public class CustomerApi {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCustomer(Customer customer) throws ApplicationException{
		System.out.println(customer);
		CustomerLogic customerLogic = new CustomerLogic();
		customerLogic.createCustomer(customer);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCustomer(Customer customer) throws ApplicationException{
		CustomerLogic customerLogic = new CustomerLogic();
		customerLogic.updateCustomer(customer);		
	}
	
	@GET
	@Path("/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("customerId")long customerId) throws ApplicationException {
		CustomerLogic customerLogic = new CustomerLogic();
		Customer customer = customerLogic.getCustomer(customerId);
		return customer;
	}
	
	@DELETE
	@Path("/{customerId}")
	public void removeCustomer(@PathParam("customerId")long customerId) throws ApplicationException{
		CustomerLogic customerLogic = new CustomerLogic();
		customerLogic.removeCustomer(customerId);
	}
	
	@GET
	@Path("/allcustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getAllCustomers() throws ApplicationException{
		CustomerLogic customerLogic = new CustomerLogic();
		Collection<Customer> customers = customerLogic.getAllCustomers();
		return customers;
		
	}
	
}
