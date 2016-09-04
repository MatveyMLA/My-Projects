package tests;

import bank_dao.ClientsDao;
import beans.Bank;
import beans.Client;
import beans.GoldClient;
import beans.PlatinumClient;

public class Test {

	public static void main(String[] args) throws Exception {			
		
		Client goldGlient =  new GoldClient(2112,"Yossi", 2000f) ;
		
		ClientsDao dao = new ClientsDao();
		
		System.out.println(goldGlient);
		
		dao.createClient(goldGlient);		
		
		
		Client goldGlient1 = dao.getClient(2112);
		
		dao.deleteClient(2112);
		
		System.out.println(goldGlient);
		
		
		
	}
 
}
