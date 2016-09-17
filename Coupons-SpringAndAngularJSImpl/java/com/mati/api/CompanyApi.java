package com.mati.api;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mati.beans.Company;
import com.mati.exceptions.ApplicationException;
import com.mati.logic.CompanyLogic;


@RestController
@RequestMapping(value = "/companies")
public class CompanyApi{

	@Autowired
	CompanyLogic companyLogic;
	
	@RequestMapping(method = RequestMethod.POST)
	public void createCompany(@RequestBody Company company) throws ApplicationException{
		companyLogic.createCompany(company);		
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public void updateCompany(@RequestBody Company company)throws ApplicationException{
		companyLogic.updateCompany(company);
	}

	@RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
	public @ResponseBody Company getCompany(@PathVariable("companyId")long companyId)throws ApplicationException{
		Company company = companyLogic.getCompany(companyId);
		return company;
	}
	
	@RequestMapping(value = "/{companyId}", method = RequestMethod.DELETE)
	public void removeCompany(@PathVariable("companyId")long companyId) throws ApplicationException{
		companyLogic.removeCompany(companyId);
	}
	
	@RequestMapping(value = "/allcompanies", method = RequestMethod.GET)
	public @ResponseBody Collection<Company> getAllCompanies() throws ApplicationException{
		Collection<Company> companies = companyLogic.getAllCompanies();
		return companies;
	}

}
