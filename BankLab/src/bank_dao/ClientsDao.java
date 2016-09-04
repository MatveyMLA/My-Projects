package bank_dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.Client;
import beans.GoldClient;
import beans.RegularClient;
import exceptions.BankException;
import logger.Logger;
import utilities.ConnectionManeger;
import enums.Ranks;

public class ClientsDao {

	public void createClient(Client client) throws BankException{
		//PreparedStatement preparedStatement = null;
		//Connection connection = ConnectionManeger.getConnection();
		// creating the new client ( inserting it to the DB) 
		try(Connection connection = ConnectionManeger.getConnection();
			PreparedStatement preparedStatement =connection.prepareStatement("insert into clients values(?,?,?,?,?,?)")) 
		{
			//preparedStatement = connection.prepareStatement("insert into clients values(?,?,?,?,?,?)");
			preparedStatement.setLong(1, client.getId());
			preparedStatement.setString(2, client.getName());
			preparedStatement.setFloat(3, client.getBalance());
			preparedStatement.setString(4, client.getRank());
			preparedStatement.setFloat(5, client.getCommissionRate());
			preparedStatement.setFloat(6, client.getIntrestRate());	
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			Logger.getInstance().logExeption(e.getMessage());
			throw new BankException(e.getMessage(), e);

		}
		//finally {
		//ConnectionManeger.closeQuaetly(connection);
		//ConnectionManeger.closeQuaetly(preparedStatement);		
		//}
	}
	public void deleteClient(Client client) throws BankException{
		//PreparedStatement preparedStatement = null;
		//Connection connection = ConnectionManeger.getConnection();
		//deleting the client from clients table 
		try (Connection connection = ConnectionManeger.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("delete from clients where id = ?"))
		{
			//preparedStatement = connection.prepareStatement("delete from clients where id = ?");
			preparedStatement.setLong(1, client.getId());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			Logger.getInstance().logExeption(e.getMessage());
			throw new BankException(e.getMessage(), e);

		}
		//finally {
		//ConnectionManeger.closeQuaetly(connection);
		//ConnectionManeger.closeQuaetly(preparedStatement);		
		//}
	}

	public void deleteClient(long clientId) throws BankException{
		//		Connection connection = ConnectionManeger.getConnection();
		//		PreparedStatement preparedStatement = null;
		// deleting the client from clients table 
		try(Connection connection = ConnectionManeger.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("delete from clients where id = ?"))
		{
			//			preparedStatement = connection.prepareStatement("delete from clients where id = ?");
			preparedStatement.setLong(1, clientId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			Logger.getInstance().logExeption(e.getMessage());
			throw new BankException(e.getMessage(), e);

		}
		//		finally {
		//			ConnectionManeger.closeQuaetly(connection);
		//			ConnectionManeger.closeQuaetly(preparedStatement);		
		//		}
	}

	public Client getClient(long clientId) throws BankException{
		//		Connection connection = ConnectionManeger.getConnection();
		//		PreparedStatement preparedStatement = null;
		//		ResultSet resultSet = null;

		// Getting the client from clients table 
		Client client = null;
		try(Connection connection = ConnectionManeger.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("select * from clients where id = ?")) {
			//preparedStatement = connection.prepareStatement("select * from clients where id = ?");
			preparedStatement.setLong(1, clientId);
			try(ResultSet resultSet = preparedStatement.executeQuery())			
			{
				if(resultSet.next()){				

					String rank = resultSet.getString("rank");
					String name = "";
					float balance = 0f;
					
					switch(rank){

					case "regular": 
						name = resultSet.getString("name");
						balance = resultSet.getFloat("balance");
						client = new RegularClient(clientId, name, balance);
						return client;

					case "gold": 
						name = resultSet.getString("name");
						balance = resultSet.getFloat("balance");
						client = new GoldClient(clientId, name, balance);
						return client;

					case "platinum": 
						name = resultSet.getString("name");
						balance = resultSet.getFloat("balance");
						client = new GoldClient(clientId, name, balance);
						return client;

					}
				}
			}
			return client;

		} catch (SQLException e) {

			e.printStackTrace();
			Logger.getInstance().logExeption(e.getMessage());
			throw new BankException(e.getMessage(), e);

		}

		//finally {
		//ConnectionManeger.closeQuaetly(connection);
		//ConnectionManeger.closeQuaetly(preparedStatement);		
		//}
	}
}
