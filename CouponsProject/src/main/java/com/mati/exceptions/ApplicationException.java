package com.mati.exceptions;

import com.mati.enums.ErrorTypes;

public class ApplicationException extends Exception{	
	
	private ErrorTypes errorType;		
		
	public ApplicationException(ErrorTypes errorType, String massage){	
		super(massage);
		this.errorType = errorType;		
	}
	public ApplicationException(ErrorTypes errorType, Exception e ,String massage){	
		super(massage, e);
		this.errorType = errorType;		
	}

	public ErrorTypes getErrorType() {
		return errorType;
	}	
}
