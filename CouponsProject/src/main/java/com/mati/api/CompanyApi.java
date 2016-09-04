package com.mati.api;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mati.beans.Company;
import com.mati.exceptions.ApplicationException;
import com.mati.logic.CompanyLogic;

@Path("/companies")
public class CompanyApi{

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCompany(Company company) throws ApplicationException{
		CompanyLogic companyLogic = new CompanyLogic();
		companyLogic.createCompany(company);		
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCompany(Company company)throws ApplicationException{
		CompanyLogic companyLogic = new CompanyLogic();
		companyLogic.updateCompany(company);
	}

	@GET
	@Path("/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@PathParam("companyId")long companyId)throws ApplicationException{
		CompanyLogic companyLogic = new CompanyLogic();
		Company company = companyLogic.getCompany(companyId);
		return company;
	}

	@DELETE
	@Path("/{companyId}")
	public void removeCompany(@PathParam("companyId")long companyId) throws ApplicationException{
		System.out.println(companyId);

		CompanyLogic companyLogic = new CompanyLogic();
		companyLogic.removeCompany(companyId);
	}

	@GET
	@Path("/allcompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Company> getAllCompanies() throws ApplicationException{
		CompanyLogic companyLogic = new CompanyLogic();
		Collection<Company> companies = companyLogic.getAllCompanies();
		return companies;
	}

}
