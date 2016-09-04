package utilities;

import java.sql.*;
import exceptions.BankException;
import logger.Logger;

public class ConnectionManeger {

	public static Connection getConnection() throws BankException {
		
		Connection connection = null;		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_jhon_bryce_db", "root", 
					"root");
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.getInstance().logExeption(e.getMessage());
			throw new BankException("Cant create connection.", e);
			
		}
		
		return connection;
		
	}
	
	public static void closeResourses(Connection connection, Statement statement ) throws BankException{
		
		try{
			connection.close();
		}catch(SQLException e){
			e.printStackTrace();
			Logger.getInstance().logExeption(e.getMessage());
			throw new BankException(e.getMessage(), e);
		}
		
		
	}
	public static void closeResourses(Connection connection, Statement statement, ResultSet resultSet) throws BankException{
		closeResourses(connection, statement );
		
		try{
			resultSet.close();
		}catch(SQLException e){
			e.printStackTrace();
			Logger.getInstance().logExeption(e.getMessage());
			throw new BankException(e.getMessage(), e);
		}
		
		
	}
	
}
