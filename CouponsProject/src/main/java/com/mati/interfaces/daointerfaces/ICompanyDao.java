package com.mati.interfaces.daointerfaces;

import java.util.Collection;

import com.mati.beans.Company;
import com.mati.exceptions.ApplicationException;

public interface ICompanyDao {

	void createCompany(Company company) throws ApplicationException;
	void removeCompany(long companyId) throws ApplicationException;
	void updateCompany(Company company) throws ApplicationException;
	Company getCompany(long companyId) throws ApplicationException;
	Collection<Company> getAllCompanies() throws ApplicationException;	
	boolean login(String companyName, String password) throws ApplicationException;	
	boolean isCompanyExists(Long companyId) throws ApplicationException;
	void removeCompanyAndCreatedCouponsByCompanyId(long companyId) throws ApplicationException;
}
