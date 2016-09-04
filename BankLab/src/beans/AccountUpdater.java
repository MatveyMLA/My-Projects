package beans;

import java.util.Collection;
import java.util.Map;
import java.util.TimerTask;

public final class AccountUpdater extends TimerTask {

	private Collection<Client> clientList;
	
	public AccountUpdater(Map<Long, Client> clients){
		
		this.clientList = clients.values();
	}

	@Override
	public void run() {	
		
		for (Client client : clientList) {
			
			client.autoUpdateAccounts();
			
		}		
		
	}
}