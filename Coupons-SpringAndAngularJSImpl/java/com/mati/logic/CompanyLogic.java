package com.mati.logic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mati.beans.Company;
import com.mati.enums.ErrorTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.interfaces.daointerfaces.ICompanyDao;
import com.mati.interfaces.logicinterfaces.IClient;

@Controller
public class CompanyLogic implements IClient{

	@Autowired
	private ICompanyDao companyDao;

	public CompanyLogic() {}	

	public void createCompany(Company company) throws ApplicationException {
		if(company == null){
			throw new ApplicationException(ErrorTypes.FALSE_COMPANY_INFORMATION, "Company is null");

		}
		companyDao.createCompany(company);

	}	

	public Company getCompany(long companyId) throws ApplicationException{		
		Company company = companyDao.getCompany(companyId);		
		if(company == null){
			throw new ApplicationException(ErrorTypes.COMPANY_DOES_NOT_EXSIST, "Company not found");

		}
		return company;

	}	


	public Collection<Company> getAllCompanies() throws ApplicationException{
		Collection<Company> companies = companyDao.getAllCompanies();
		return companies;

	}

	public void updateCompany(Company company) throws ApplicationException{
		if(company == null){
			throw new ApplicationException(ErrorTypes.FALSE_COMPANY_INFORMATION, "Company is null");

		}
		if(!companyDao.isCompanyExists(company.getId())){
			throw new ApplicationException(ErrorTypes.COMPANY_DOES_NOT_EXSIST, "Company not found");

		}
		companyDao.updateCompany(company);
	}

	public void removeCompany(long companyId) throws ApplicationException{		
		if(!companyDao.isCompanyExists(companyId)){
			throw new ApplicationException(ErrorTypes.COMPANY_DOES_NOT_EXSIST, "Company not found");

		}
		companyDao.removeCompanyAndCreatedCouponsByCompanyId(companyId);

	}



	public void login(String companyName, String password) throws ApplicationException {
		if(companyDao.login(companyName, password) ? true : false)
			return;
		throw new ApplicationException(ErrorTypes.INVALID_USER_NAME_OR_PASSWORD, "Login failed. User name or password is incorrect");
	}

}
