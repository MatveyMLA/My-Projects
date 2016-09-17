package com.mati.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mati.beans.Customer;
import com.mati.enums.ErrorTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.interfaces.daointerfaces.ICustomerDao;
import com.mysql.jdbc.Statement;

@Repository
public class CustomerDao implements ICustomerDao{
	
	@Autowired
	private ConnectionPool connectionPool;
	@Autowired
	private UserDao userDao;
	
	@Override
	public void createCustomer(Customer customer) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try{		
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("insert into customers(name_customer, password) values(?,?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, customer.getName());
			preparedStatement.setString(2, customer.getPassword());					
			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();
			long generatedCustomerId = 0;			
			if(resultSet.next()){					
				generatedCustomerId = resultSet.getLong(1);	
				//Creating a relation user_client by company id and user id.
				//In another words inserting company id and user id to join table
				userDao.createCustomerUser(customer, generatedCustomerId, connection);
				connection.commit();
			}
			else{
				connection.rollback();
			}
		}catch (SQLException e){
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally{			
			connectionPool.closeResourses(connection, preparedStatement);
		}
	}


	@Override
	public void removeCustomer(long customerId) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;

		try 
		{
			preparedStatement = connection.prepareStatement("delete from customers where id = ? ");
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement);
		}

	}
	//removeCustomerAndAllPurchasedCoupons removes customer and purchased coupons from join table
	@Override
	public void removeCustomerAndAllPurchasedCoupons(long customerId) throws ApplicationException {

		Connection connection =  connectionPool.getConnection();
		PreparedStatement preparedStatement = null;

		try 
		{			
			preparedStatement = connection.prepareStatement("delete customers, customer_coupon from customers join customer_coupon on customers.id = customer_coupon.customer where customers.id = ?");
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;

		try{

			preparedStatement =connection.prepareStatement("update customers set name_customer = ?, password = ? where id = ?");			
			preparedStatement.setString(1, customer.getName());	
			preparedStatement.setString(2, customer.getPassword());	
			preparedStatement.setLong(3, customer.getId());
			preparedStatement.executeUpdate();

		}catch (SQLException e){
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally{	
			connectionPool.closeResourses(connection, preparedStatement);
		}
	}

	@Override
	public Customer getCustomer(long customerId) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("select * from customers where id = ?");
			preparedStatement.setLong(1,customerId );
			resultSet = preparedStatement.executeQuery();

			if(!resultSet.next()){
				return null;
			}
			Customer customer = getCustomerFromResultSet(resultSet);
			return customer;

		}catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}

	}

	@Override
	public Collection<Customer> getAllCustomers() throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;	

		try {
			preparedStatement = connection.prepareStatement("select * from customers ");
			resultSet = preparedStatement.executeQuery();	
			Collection<Customer> allCustomers = getCustomersFromResultSet(resultSet);

			return allCustomers;

		}catch (SQLException e) {

			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public boolean login(long customerId, String password) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("select * from customers where id = ? and password = ?");
			preparedStatement.setLong(1, customerId);
			preparedStatement.setString(2, password);

			resultSet = preparedStatement.executeQuery();	

			return resultSet.next() ? true : false ;

		}catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}

	}

	@Override
	public boolean isCustomerExists(long customerId) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {			
			preparedStatement = connection.prepareStatement("select * from customers where id = ?");
			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();	

			return resultSet.next() ? true : false ;

		}catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}
	} 
	
	//private functions:
	
		
	//The getCustomersFromResultSet extracting the customers information from a result set and packing customers to Collection
	private Collection<Customer> getCustomersFromResultSet(ResultSet resultSet) throws SQLException{
		List<Customer> allCustomers = new ArrayList<>();

		while(resultSet.next()){

			Customer customer = getCustomerFromResultSet(resultSet);
			allCustomers.add(customer);
		}
		return allCustomers;

	}
	//The getCustomerFromResultSet extracting the customer information from a result set and returns customer instance
	private Customer getCustomerFromResultSet(ResultSet resultSet) throws SQLException{

		long id = resultSet.getLong("id");
		String name = resultSet.getString("name_customer");
		String password = resultSet.getString("password");
		Customer customer = new Customer(id, name, password);

		return customer;

	}

}
