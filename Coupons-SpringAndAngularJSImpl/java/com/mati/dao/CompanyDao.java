package com.mati.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mati.beans.Company;
import com.mati.enums.ErrorTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.interfaces.daointerfaces.ICompanyDao;
import com.mysql.jdbc.Statement;

@Repository
public class CompanyDao implements ICompanyDao {
	
	@Autowired 
	private ConnectionPool connectionPool;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void createCompany(Company company) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try{
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("insert into companies(name_company, password, email) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, company.getName());
			preparedStatement.setString(2, company.getPassword());	
			preparedStatement.setString(3, company.getEmail());					
			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();
			long generatedCompanyId = 0;			
			if(resultSet.next()){					
				generatedCompanyId = resultSet.getLong(1);	
				//Creating a relation user_client by company id and user id.
				//In another words inserting company id and user id to join table.
				userDao.createCompanyUser(company, generatedCompanyId, connection);
				connection.commit();
			}
			else{
				connection.rollback();
			}
		}catch (SQLException e){

			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally{			
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public void removeCompany(long companyId) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("delete from companies where id = ?");
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement);

		}
	}
	@Override
	public void removeCompanyAndCreatedCouponsByCompanyId(long companyId) throws ApplicationException{

		Connection connection =  connectionPool.getConnection();
		PreparedStatement preparedStatement = null;

		try {
			preparedStatement = connection.prepareStatement(
					//The command removes the company by companyId from the system and in this connection deletes belonging coupons
					"delete companies, company_coupon, customer_coupon, coupons from companies "+					
					"join company_coupon on companies.id = company_coupon.company " + 
					"left join coupons on coupons.id = company_coupon.coupon " + 
					"left join customer_coupon on customer_coupon.coupon = company_coupon.coupon " + 
					"where companies.id = ?");
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement);

		}	
	}

	@Override
	public void updateCompany(Company company) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;

		try{			
			preparedStatement = connection.prepareStatement("update companies set password = ?, email = ? where id = ?");			
			preparedStatement.setString(1, company.getPassword());
			preparedStatement.setString(2, company.getEmail());	
			preparedStatement.setLong(3, company.getId());				
			preparedStatement.executeUpdate();

		}catch (SQLException e){
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally{	
			connectionPool.closeResourses(connection, preparedStatement);
		}		
	}

	@Override
	public Company getCompany(long companyId) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;		

		try {
			preparedStatement = connection.prepareStatement("select * from companies where id = ?");
			preparedStatement.setLong(1, companyId );
			resultSet = preparedStatement.executeQuery();			

			if(!resultSet.next()){				
				return null;

			}
			Company company = getCompanyFromResultSet(resultSet);
			return company;

		}catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);

		}
	}

	@Override
	public Collection<Company> getAllCompanies() throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("select * from companies");
			resultSet = preparedStatement.executeQuery();	
			Collection<Company> allCompanies = getCompaniesFromResultSet(resultSet);
			return allCompanies;

		}catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);

		}
	}



	@Override
	public boolean login(String companyName, String password) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("select * from companies where name_company = ? and password = ?");
			preparedStatement.setString(1, companyName);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();	

			return resultSet.next() ? true : false ;

		}catch (SQLException e){
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);

		}
	}

	@Override
	public boolean isCompanyExists(Long companyId) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {			
			preparedStatement = connection.prepareStatement("select * from companies where id = ?");
			preparedStatement.setLong(1, companyId);

			resultSet = preparedStatement.executeQuery();	

			return resultSet.next() ? true : false ;

		}catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {

			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}
	}
	//private functions
	
	private Company getCompanyFromResultSet(ResultSet resultSet) throws SQLException{

		long id = resultSet.getLong("id");
		String name = resultSet.getString("name_company");
		String password = resultSet.getString("password");
		String email = resultSet.getString("email");
		Company company = new Company(id, name, password, email);

		return company;

	}
	private Collection<Company> getCompaniesFromResultSet(ResultSet resultSet) throws SQLException{
		List<Company> allCompanies = new ArrayList<>();
		while(resultSet.next()){

			Company copany = getCompanyFromResultSet(resultSet);
			allCompanies.add(copany);

		}

		return allCompanies;
	}
}
