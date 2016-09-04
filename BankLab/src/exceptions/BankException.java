package exceptions;

public class BankException extends Exception{	
	
	public BankException( String massage){
		
		super(massage);
	}
	public BankException( String massage, Exception exp){
		
		super(massage, exp);
	}
	
}
