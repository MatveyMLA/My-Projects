package com.mati.exceptions;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionsHandler 
{
	
	@ExceptionHandler(Exception.class)
	public void toResponse(HttpServletResponse response, ApplicationException exception) 
	{
			exception.printStackTrace();
			int internalErrorCode = exception.getErrorType().getErrorCode();
			String message = exception.getMessage();
			response.setStatus(600);
			
			Cookie errorCodeCookie = new Cookie("internalErrorCode", String.valueOf(internalErrorCode));
			response.addCookie(errorCodeCookie);
			
			response.setHeader("errorMessage", message);
	}
}
