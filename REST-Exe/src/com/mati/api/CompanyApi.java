package com.mati.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.mati.beans.Company;

@Path("/api/company")
public class CompanyApi {
	
	@Path("/createCompany")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCompany(Company company){
		System.out.println(company);
	}

}
