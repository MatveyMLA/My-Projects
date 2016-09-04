package beans;

import enums.Commission;
import enums.Intrest;
import enums.Ranks;

public final class RegularClient extends Client{	

	
	
	public RegularClient(long id, String name, float balance) {		
		super(id, name, balance);		
		super.rank = Ranks.REGULAR.getValue();
		super.commissionRate = Commission.REGULAR.getValue();
		super.intrestRate = Intrest.REGULAR.getValue();		
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
