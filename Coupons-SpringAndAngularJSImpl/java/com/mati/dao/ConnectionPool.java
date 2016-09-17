package com.mati.dao;

import java.sql.*;
import java.util.NoSuchElementException;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mati.enums.ErrorTypes;
import com.mati.exceptions.ApplicationException;

@Component
public class ConnectionPool {

	static
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		connections = new ConcurrentLinkedQueue<>();		
		makeConnections();
		
		
	}
	
//	private static ConnectionPool instance;
	private static  ConcurrentLinkedQueue<Connection> connections;	
	@Value("${DEFAULT}")
	private static int DEFAULT_AMOUNT_OF_CONNECTIONS;
	@Value("${MAX}")
	private static int MAX_AMOUNT_OF_CONNECTIONS ;
	@Value("${MIN}")
	private static int MIN_AMOUNT_OF_CONNECTIONS; 

	// Ctor.
	public ConnectionPool(){		
		
	}
	
	//Singleton method returns instance of ConnectionPool.
//	public static synchronized ConnectionPool getInstance() {
//		if (instance == null){			
//			instance = new ConnectionPool();
//		}
//		return instance;
//
//	}	

	//Method closes connections. Method invokes thread that actually closes one connection in pool.
	public void closeConnections(){
		synchronized (connections) {	
			for (int i = 0; i < connections.size(); i++) {
				new CloseConnectionInPool().start();

			}
		}

	}
	
	//Method makes connections. Method invokes thread that actually makes one connection and put the connection to pool.
	private static void makeConnections(){	

		for(int i = 0; i < DEFAULT_AMOUNT_OF_CONNECTIONS; i++){
			new AddConnectionToPool().start();
		}	
		synchronized(connections){
			connections.notify();
		}
		
	}
	
	//Method returns ready to work connection.
	public  Connection getConnection() throws ApplicationException {

		Connection connection = null;
		
		synchronized(connections){
			
			try{
				//If connections run out in pool, then thread which invoked this method waits for connection to return
				if(connections.size() <= MIN_AMOUNT_OF_CONNECTIONS){
					
					connections.wait();
					if(connections.size() < MAX_AMOUNT_OF_CONNECTIONS - DEFAULT_AMOUNT_OF_CONNECTIONS){
						makeConnections();
						connections.notify();
					}
					
				}	
				connection = connections.remove();
			}
			catch(NoSuchElementException | InterruptedException e){
				e.printStackTrace();
				throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant get connection.");

			}
			
		}
		return connection;

	}

	public void closeResourses(Connection connection, Statement statement ) throws ApplicationException {		
		if(statement != null){

			try{
				statement.close();
			}catch(SQLException e){
				e.printStackTrace();
				//Logger.getInstance().logExeption(e.getMessage());
				throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant close resources");


			}	
		}

		synchronized(connections){

			if(connection != null){
				try{
					//Returns the connection to pool.
					if(connections.size() <= DEFAULT_AMOUNT_OF_CONNECTIONS){
						connections.add(connection);
						connections.notify();

					}else {
						connection.close();
					}
				}catch(Exception e){
					e.printStackTrace();
					//Logger.getInstance().logExeption(e.getMessage());
					throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant close resources");

				}	
			}
		}

	}

	public void closeResourses(Connection connection, Statement statement, ResultSet resultSet) throws ApplicationException {

		if(resultSet != null){
			try{
				resultSet.close();
			}catch(SQLException e){
				e.printStackTrace();
				//Logger.getInstance().logExeption(e.getMessage());
				throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant close resources");
			}
		}

		closeResourses(connection, statement );

	}	


	private static synchronized Connection createConnection() throws ApplicationException {

		Connection connection = null;		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/coupons_project?USEssl=false&useSSL=false", "root", "root");

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, "Cant create connection.");

		}

		return connection;

	}	

	private static class AddConnectionToPool extends Thread{
		@Override
		public void run(){

			try {
				Connection connection;
				connection = createConnection();
				connections.add(connection);

			} catch (ApplicationException e){
				e.printStackTrace();
			}		
		}
	}

	private class CloseConnectionInPool extends Thread{
		@Override
		public void run(){

			Connection connection;
			
			connection = connections.remove();
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}		
	}
}


