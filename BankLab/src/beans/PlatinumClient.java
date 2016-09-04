package beans;

import enums.Commission;
import enums.Intrest;
import enums.Ranks;

public final class PlatinumClient extends Client{

	public PlatinumClient(long id, String name,float balance) {
		super(id, name,  balance);
		super.rank = Ranks.PLATINUM.getValue();
		super.commissionRate = Commission.PLATINUM.getValue();
		super.intrestRate = Intrest.PLATINUM.getValue();
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