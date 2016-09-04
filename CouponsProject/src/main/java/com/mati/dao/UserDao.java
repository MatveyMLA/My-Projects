 package com.mati.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mati.beans.Company;
import com.mati.beans.Customer;
import com.mati.beans.User;
import com.mati.enums.ErrorTypes;
import com.mati.enums.UserTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.interfaces.daointerfaces.IUserDao;
import com.mysql.jdbc.Statement;

public class UserDao implements IUserDao {
	
	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	public void removeUser(User user) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		long userId = getUserId(user, connection); 
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("delete from users where id = ?");
			preparedStatement.setLong(1, userId);
			preparedStatement.executeUpdate();			

		} catch (SQLException e) {
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant remove user.");

		}finally {
			connectionPool.closeResourses(connection, preparedStatement);

		}
	}

	public void updateUser(User user) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;

		try{			
			preparedStatement = connection.prepareStatement("update users set password = ? where name = ?");			
			preparedStatement.setString(1, user.getPassword());
			preparedStatement.setString(2, user.getUserName());	
			preparedStatement.executeUpdate();

		}catch (SQLException e){
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant update user");

		}finally{	
			connectionPool.closeResourses(connection, preparedStatement);
		}		
	}

	public boolean isValidUser(User user) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {			
			preparedStatement = connection.prepareStatement("select * from users where user_name = ? and password = ?");
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());

			resultSet = preparedStatement.executeQuery();	

			return resultSet.next() ? true : false ;

		}catch (SQLException e) {
			
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant check user.");
		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}
	}

	public void createCustomerUser(Customer customer, long customerId, Connection connection) throws ApplicationException {
		
		    User user = getUserFromCustomer(customer);
			long generatedUserId = createUser(user, connection);
			addUserClient(generatedUserId, customerId, connection);
		
	}

	public void createCompanyUser(Company company, long companyId, Connection connection) throws ApplicationException {
	
			User user = getUserFromCompany(company);
			long generatedUserId = createUser(user, connection);
			addUserClient(generatedUserId, companyId, connection);
	
	}
	
	@Override
	public long getClientIdByUser(User user) throws ApplicationException {
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		long clientId = 0;
		try{
			long userId = getUserId(user, connection);
			preparedStatement = connection.prepareStatement("select client_id from user_client where user_id = ?");			
			preparedStatement.setLong(1, userId);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				clientId = resultSet.getLong(1);
			}
			else{
				throw new ApplicationException(ErrorTypes.GENERAL_ERROR, "Cant get client id.");
			}

		}catch (SQLException e){
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant get client id.");
			
		}finally{	
			connectionPool.closeResourses(connection, preparedStatement);
		}	
		return clientId;
	}
	
	private long getUserId(User user, Connection connection) throws ApplicationException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		long userId = 0;
		try {
			preparedStatement = connection.prepareStatement("select id from users where user_name = ? and password = ?");
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				userId = resultSet.getLong(1);
			}
			else{
				throw new ApplicationException(ErrorTypes.GENERAL_ERROR, "Cant get user_id.");
			}
		} catch (SQLException e) {
			
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant get user_id.");
		}			
		return userId;
	}

	private long createUser(User user, Connection connection) throws ApplicationException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		long userId = 0;

		try{
			preparedStatement = connection.prepareStatement("insert into users(user_name, password, client_type) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());	
			preparedStatement.setString(3, user.getType().getName());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			
			if(resultSet.next()){
				userId = resultSet.getLong(1);
			}
			else{
				throw new ApplicationException(ErrorTypes.GENERAL_ERROR, "Cant create user");
			}

		}catch (SQLException e){
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant create user");

		}
		return userId;
	}
	private void addUserClient(long userId, long clientId, Connection connection) throws ApplicationException{
		PreparedStatement preparedStatement = null;
		try{
			preparedStatement = connection.prepareStatement("insert into user_client(user_id, client_id) values(?,?)");
			preparedStatement.setLong(1, userId);
			preparedStatement.setLong(2, clientId);
			preparedStatement.executeUpdate();
		}catch (SQLException e){
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant add user.");
		}finally{			
			connectionPool.closeResourses(connection, preparedStatement);
		}

	}

	private User getUserFromCompany(Company company){
		String userName = company.getName();
		String password = company.getPassword();
		UserTypes type = UserTypes.COMPANY;
		User user = new User(userName, password, type);

		return user;
	}


	private User getUserFromCustomer(Customer customer) {
		String userName = customer.getName();
		String password = customer.getPassword();
		UserTypes type = UserTypes.CUSTOMER;
		User user = new User(userName, password, type);

		return user;
	}

	@Override
	public UserTypes getClientType(User user) throws ApplicationException {
		Connection connection = connectionPool.getConnection();
		long userId = getUserId(user, connection);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UserTypes type = null;
		try {
			preparedStatement = connection.prepareStatement("select client_type from users where id = ?");
			preparedStatement.setLong(1, userId);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){				
				String typeStr = resultSet.getString(1);
				type = UserTypes.getType(typeStr);
			}
			else{
				throw new ApplicationException(ErrorTypes.GENERAL_ERROR, "Cant get user type.");
			}
		} catch (SQLException e) {
			
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant get user type.");
		}			
		return type;
	}
	

}
