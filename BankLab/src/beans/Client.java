package beans;

import java.io.Serializable;
import java.sql.Connection;
import java.util.*;

import javax.swing.JOptionPane;

import exceptions.BankException;
import logger.Logger;

public abstract class Client implements Serializable, Comparable<Client>{	
	
	private String name;
	private float balance;
	private TreeMap<Long, Account> allAccounts;
	
	protected float commissionRate;
	protected float intrestRate;
	protected final long id;
	protected String rank;

	//Ctor
	public Client(long id, String name, float balace) {

		this.id = id;
		this.name = name;
		this.balance = balace;
		this.allAccounts = new TreeMap<Long, Account>();

	}
	
	//Getters/Setters
	public String getName() {
		return this.name;
	}

	public void setName(String name) {		
		this.name = name;
	}

	public float getBalance() {
		return this.balance;
	}

	public void setBalance(float newBlace) {
		// Sets the bank balance
		this.balance = newBlace;
		// Log the operation that account balance had been established
		String description = "Account balance has been established";
		Logger.getInstance().log(this.id, description);
	}

	public long getId() {
		return this.id;
	}
	
	public String getRank() {
		return rank;
	}
	
	public float getCommissionRate() {
		return commissionRate;
	}
	public float getIntrestRate() {
		return intrestRate;
	}
	
	// Function returns all of accounts of the client
	public Collection<Account> getAccounts() throws BankException {
		try{
			// Check in if the allAccounts contains clients 
			if(this.allAccounts.isEmpty()){
				// If there are not clients in the bank then exception well be thrown
				throw new BankException("There is no a requested account");
			}

			// If there are clients in the bank, return the clients 
			return allAccounts.values();
		}
		catch(Exception e){		
			// Log exception in the log exceptions file 
			Logger.getInstance().logExeption(e.getMessage());
			// Throw new exception that contains cause exception and proper massage
			throw new BankException(e.getMessage(), e);
		}
	}


	// Add a new account to the client
	public void addAccount(Account account) throws BankException {		
		try{
			if(account == null){

				throw new BankException("Account is wrong.");

			}
			// Check if account already exists
			if(this.allAccounts.containsValue(account)){
				// If the account already exists then throw new proper exception
				throw new BankException("Account already exists.");
			}

			long idOfAnNewAccount = account.getId();
			// Add account to allAccounts map by new account id
			this.allAccounts.put(idOfAnNewAccount, account);
			// Log that new account has been added 
			String description = "Accoount with id = " + idOfAnNewAccount + "has been added";
			Logger.getInstance().log(this.id, description);

		}catch(Exception e){
			// Log the massage that the exception contains to log file
			Logger.getInstance().logExeption(e.getMessage());
			// Throw new exception that contains cause exception and proper massage 
			throw new BankException(e.getMessage(), e);
		}
	}

	// Get account by id 
	public Account getAccount(long idToGetAccount) throws BankException {
		try{
			//Check in the id if the id is valid, that is positive and not 0.
			if(idToGetAccount < 0){
				//If the id is wrong, then throw a new proper exception
				throw new BankException("ID is wrong.");
			}

			//Check in if the account with wanted id exists 
			if(!this.allAccounts.containsKey(idToGetAccount)){
				//If account with the id doesn't exist, then throw new proper exception
				throw new BankException("Account not found");
			}
			//Return wanted account by requested id 
			return this.allAccounts.get(idToGetAccount);

		}catch(Exception e){
			//Log the thrown exception
			Logger.getInstance().logExeption(e.getMessage());
			//And throw the new exception with actual massage and with couse exception further 
			throw new BankException(e.getMessage(), e);
		}

	}

	//Remove an account by id
	public void removeAccount(long idToRemove) throws BankException {
		try{
			//Check in the id if the id is valid, that is positive and not 0.
			if(idToRemove < 0 ){

				//If id is wrong, then throw new proper exception
				throw new BankException("ID is wrong.");
			}

			//Check in if the account with wanted id exists 
			if (!this.allAccounts.containsKey(idToRemove)) {

				//If account with the id doesn't exist, then throw new proper exception
				throw new BankException("Account not found.");
			}

			//Update the client balance with the account balance that would be removed
			this.balance += this.allAccounts.get(idToRemove).getBalance();
			//Remove the account by id
			this.allAccounts.remove(idToRemove);
			//Log removing account operation operation
			String description = "Account has been removed";
			Logger.getInstance().log(idToRemove, description);

		} catch(Exception e){
			//Log the thrown exception
			Logger.getInstance().logExeption(e.getMessage());
			//And throw the new exception with actual massage and with cause exception further
			throw new BankException(e.getMessage(), e);
		}
	}
	//Remove an account by Account instance
	public void removeAccount(Account accoutToRemove) throws BankException {
		try{
			//Check in if the requested for removing account is correct, that is not null
			if(accoutToRemove == null){
				//If the account is wrong, throw new proper exception
				throw new BankException("Account is wrong.");
			}

			//Check in if the wonted account exists
			if(!this.allAccounts.containsValue(accoutToRemove)){

				//If requested account doesn't exist, then throw new proper exception
				throw new BankException("Account not found");
			}
			//Remove the account by instance
			this.allAccounts.remove(accoutToRemove);

			String description = "Account has been removed";
			long idOfRemovedAccount = accoutToRemove.getId();
			Logger.getInstance().log(idOfRemovedAccount, description);
		}catch(Exception e){

			Logger.getInstance().logExeption(e.getMessage());
			throw new BankException(e.getMessage(),e);
		}


	}



	/*
	 *  Deposit and withdraw methods implement to add or remove the amount  from 
	 *  clients balance according to the commission.
	 */
	
	public void deposit(float amountOfDeposit) {

		float newBalance = getBalance() + amountOfDeposit;
		newBalance -= newBalance * commissionRate;
		this.setBalance(newBalance);

		Bank.setBalance(Bank.getBalance() + amountOfDeposit * (1 - commissionRate));

		String description = "Deposit operation";
		Logger.getInstance().log(this.id, description);

	}

	public void withdraw(float amountOfWithdraw)throws BankException{
		try{
			if(amountOfWithdraw > this.balance){

				throw new BankException("Not enough money on the balance.");
			}

			float newBalance = this.balance - amountOfWithdraw;
			newBalance -= newBalance * commissionRate;
			this.setBalance(newBalance);

			String description = "Withdraw operation";
			Logger.getInstance().log(this.id, description);
		} catch (Exception e){

			Logger.getInstance().logExeption(e.getMessage());
			throw new BankException(e.getMessage(), e);
		}
	}
	
	/*
	 *   Runs over the accounts, calculates the amount to add according
	 *  to the client interest and add it to each account balance.
	 *  Use the interest data member in your calculation. Log this. 
	 */
	
	public void autoUpdateAccounts() {

		for (Account account : this.allAccounts.values()){

			float currentBalance = account.getBalance();
			float newBalanse = currentBalance + (currentBalance * intrestRate);
			account.setBalance(newBalanse);

			String description = "Auto apdate account";
			Logger.getInstance().log(this.id, description);
			
			
		}
	}

	//Returns the sum of client balance + total account balance.
	public float getFortune(){

		float sumOfEachAccountBalance = this.getSumOfAccountsBalance();
		float fortune = this.balance + sumOfEachAccountBalance;

		return fortune;
	}

	private float getSumOfAccountsBalance(){

		float sum = 0;

		for(Account account : this.allAccounts.values()){

			sum+=account.getBalance();

		}

		return sum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof Client))
			return false;

		Client other = (Client) obj;
		if (id != other.id)
			return false;

		else if (!name.equals(other.name))
			return false;
		return true;
	}





}
