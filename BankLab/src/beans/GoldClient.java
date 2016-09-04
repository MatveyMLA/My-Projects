package beans;

import enums.*;

public final class GoldClient extends Client{
	
	public GoldClient(long id, String name, float balance) {	
		
		super(id, name, balance);
		super.rank = Ranks.GOLD.getValue();
		super.commissionRate = Commission.GOLD.getValue();
		super.intrestRate = Intrest.GOLD.getValue();
	}
	
	@Override
	public String toString() {
		String clientType = this.getClass().getSimpleName();
		String string = clientType + ", ID: " + this.getId();
		return string;
	}

	@Override
	public int compareTo(Client otherClient) {
		
		if(otherClient.getId() > this.getId())
			return -1;
		
		if(otherClient.getId() < this.getId())
			return 1;
		
		return 0;
	}

}
