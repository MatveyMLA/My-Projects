package beans;

import java.io.Serializable;

import logger.Logger;


public class Account implements Serializable{

	private final long id;
	private float balance;
	private static long serialCounter;

	public Account(long id, float balance) {
		
		serialCounter ++;
		this.id = serialCounter;
		this.balance = balance;
	}

	public long getId() {
		return this.id;
	}

	public float getBalance() {
		return this.balance;
	}

	public void setBalance(float balance) {

		this.balance = balance;		
		String description = "Account has been seted";
		Logger.getInstance().log(this.id, description);

	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(this.balance);
		result = prime * result + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (!(obj instanceof Account))
			return false;
		
		Account other = (Account) obj;
		if (this.id != other.id)
			return false;
		return true;
	}

}
