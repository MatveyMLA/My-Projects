package com.mati.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.mati.beans.ErrorBean;

@Provider
public class ExceptionsHandler extends Exception implements ExceptionMapper<Throwable> 
{
	@Override
	public Response toResponse(Throwable exception) 
	{
		exception.printStackTrace();

		if (exception instanceof ApplicationException){
			ApplicationException e = (ApplicationException) exception;

			int internalErrorCode = e.getErrorType().getErrorCode();
			String message = e.getMessage();
			ErrorBean errorBean = new ErrorBean(internalErrorCode, message);
			return Response.status(403).entity(errorBean).build();
		}

		return Response.status(500).entity("General failure. Sorry.").build();

	}
}