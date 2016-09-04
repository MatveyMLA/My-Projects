package beans;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import exceptions.BankException;
import logger.Logger;

public final class Bank {

	private static final String FILE = "C:\\JavaWorkspace\\Bank system\\StoreFile.data";
	private static Bank instance;
	private static float balance;
	private Map<Long, Client> allClients;

	// Ctor
	private Bank() {	

		// Initializes the allClients by method load() that returns list of existing clients that was stored before or empty list.
		this.allClients = load();

		// Initializes the Bank balance by method getTotalClientsBalance() that sums the total client balance. 
		balance = getTotalClientsBalance();
	}

	// Singleton method that initialize the constant variable "Bank instance" if null, or returns initialized instance.
	public static synchronized Bank getInstance(){

		if(instance == null){		

			instance = new Bank();
		}

		return instance;

	}	

	// Return a total Bank balance
	static float getBalance() {	

		return balance;
	}

	//Set the Bank variable balance
	static void setBalance(float balance) {

		Bank.balance = balance;
	}

	//Returns a list of Bank clients. 
	public Collection<Client> getClients(){

		return this.allClients.values();
	}

	//Method adds a client by Client instance to the allClients
	public void addClient(Client newClient) throws BankException {

		try {

			//Checks if the client exists
			if(this.allClients.containsValue(newClient)){

				throw new BankException("Client allredy exists");

			}

			//Adds new client and his id to the allClients
			long newClientId = newClient.getId();
			this.allClients.put(newClientId, newClient);

			// Update the bank balance because a client was added			
			Bank.balance += newClient.getFortune();

			//log this operation
			String description = "Client has been added";
			Logger.getInstance().log(newClientId, description);

		}
		catch(Exception e) {

			Logger.getInstance().logExeption(e.getMessage() + " " + e.getStackTrace());	
			e.printStackTrace();
			throw new BankException(e.getMessage(),e);
		}
	}	


	//This method removes a client by Client instance
	public void removeClient(Client clientToRemove) throws BankException {

		try {
			//Checks in allCliens if the client we want to remove exists.
			//If the client doesn't exist, exception will be thrown.
			if(!this.allClients.containsValue(clientToRemove)){

				throw new BankException("There is not a client to remove");
			}

			// Update the bank balance because a client was removed
			Bank.balance -= clientToRemove.getFortune();

			//Removing client by id
			long idOfRemovedClient = clientToRemove.getId();
			this.allClients.remove(idOfRemovedClient);

			//log this operation
			String description = "Client has been removed";
			Logger.getInstance().log(idOfRemovedClient, description);	

		} catch (Exception e) {

			Logger.getInstance().logExeption(e.getMessage() + " " + e.getStackTrace());	
			e.printStackTrace();
			throw new BankException(e.getMessage(),e);
		}

	}

	//This method removes a client by the Client's id
	public void removeClient(long idToRemoveTheClient) throws BankException  {

		try{
			//Check in the allCliens if the client we want to remove exists.
			//If the client doesn't exist, exception will be thrown.
			if(!this.allClients.containsKey(idToRemoveTheClient)){

				throw new BankException("There is not a client to remove");
			}

			// Update the bank balance because a client was removed
			Bank.balance -= allClients.get(idToRemoveTheClient).getFortune();

			//Removing client by id
			this.allClients.remove(idToRemoveTheClient);

			//log this operation
			String description = "Client has been removed";
			Logger.getInstance().log(idToRemoveTheClient, description);

		} catch(Exception e){

			Logger.getInstance().logExeption(e.getMessage() + " " + e.getStackTrace());	
			e.printStackTrace();
			throw new BankException(e.getMessage(),e);
		}
	}


	//This method shows the log file
	public void viewLogs() {		
		Logger.getInstance().printLogs();
	}

	//This method updates client's accounts asynchronously every 24 hours by dint of Timer.
	public void startAccountUpdater() throws BankException{
		try{			

			//Create the account updater for use it with the timer
			AccountUpdater accountUpdater = new AccountUpdater(this.allClients);		
			Timer timer = new Timer();

			//Launch the account updater with the timer. The account updater starts every 24 hours.
			timer.schedule(accountUpdater, 86400000L, 86400000L);//86400000 Millis = 24 hours 
		}
		catch(Exception e){

			String massage = "Cant update uccounts.";

			Logger.getInstance().logExeption(massage + " " + e.getMessage() );
			throw new BankException(massage, e);
		}


	}	

	//This method stores the allClients to a file
	public void store()throws BankException{

		try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(FILE))){

			writer.writeObject(allClients);

		} catch (IOException e){

			String massage = "Can't store data to a file.";

			Logger.getInstance().logExeption(massage + " " + e.getMessage());
			e.printStackTrace();
			throw new BankException(e.getMessage(),e);		}
	}

	// This method returns the allClients stored in file. If there aren't clients in the bank then returns empty client map.
	@SuppressWarnings("unchecked")	
	private Map<Long, Client> load() {

		Object allCliensObj = null;

		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(FILE))){

			allCliensObj = reader.readObject();

			if (allCliensObj == null){

				return new TreeMap<Long, Client>();
			}

			if (!(allCliensObj instanceof Map)){

				return new TreeMap<Long, Client>();
			}			


		} catch (Exception e) {			

			return new TreeMap<Long, Client>();		

		}


		return (TreeMap<Long, Client>)allCliensObj;
	}

	// Method sums and returns total client balance which represents the Bank balancei
	private float getTotalClientsBalance() {

		float totalClientsBalace = 0;
		for (Client client : this.allClients.values()) {

			float fortuneOfClient = client.getFortune();
			totalClientsBalace += fortuneOfClient;
		}

		return totalClientsBalace;
	}
}
