
package com.mati.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mati.beans.Coupon;
import com.mati.enums.CouponTypes;
import com.mati.enums.ErrorTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.interfaces.daointerfaces.ICouponDao;
import com.mysql.jdbc.Statement;

@Repository
public class CouponDao implements ICouponDao {

	@Autowired
	private ConnectionPool connectionPool;		
	
	@Override
	public void createCoupon(Coupon coupon, long companyId) throws ApplicationException {

		Connection connection = connectionPool.getConnection();		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(

					//SQL command inserts the new coupon to DB and returns generated coupon's id.

					"insert into coupons(title,start_date,end_date,amount, type, message, price, image) "
					+"values(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, coupon.getTitle());
			preparedStatement.setTimestamp(2, coupon.getStartDate());
			preparedStatement.setTimestamp(3, coupon.getEndDate());
			preparedStatement.setInt(4, coupon.getAmountOfCoupons());
			preparedStatement.setString(5, coupon.getType().getName());
			preparedStatement.setString(6, coupon.getMessage());
			preparedStatement.setDouble(7, coupon.getPrice());
			preparedStatement.setString(8, coupon.getImage());
			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();
			long ceneratedCouponId = 0;

			if(resultSet.next()){					
				ceneratedCouponId = resultSet.getLong(1);	
				//Creating a relation company_coupon by company id and coupon id.
				//in another words inserting company id and coupon id to join table.
				createCompanyCoupon(companyId, ceneratedCouponId, connection);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public void removeCoupon(long couponId) throws ApplicationException {		

		Connection 	connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		try {		
			preparedStatement = connection.prepareStatement(
					//SQL command removes coupon from the coupons table and join tables
					"delete company_coupon, customer_coupon, coupons "
					+"from coupons "					
					+"join company_coupon on coupons.id = company_coupon.coupon " 			
					+"left join customer_coupon on coupons.id = customer_coupon.coupon "
					+"where coupons.id = ?");

			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();	

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement);
		}
	}
	@Override
	public void removePurchasedCoupon( long customerId, long couponId) throws ApplicationException {		

		Connection 	connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		try {		
			preparedStatement = connection.prepareStatement(
					//SQL command removes PurchasedCoupon from the customer_coupon table
					"delete from customer_coupon where customer = ? and coupon = ?");

			preparedStatement.setLong(1, customerId);
			preparedStatement.setLong(2, couponId);
			preparedStatement.executeUpdate();	

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;

		try {			
			preparedStatement = connection.prepareStatement(
					//SQL command updates actual coupon data according requirements 
					//and also another coupon data like message, amount and image
					"update coupons "
					+ "set end_date = ?, "
					+ "amount = ?, "
					+ "message = ?, "
					+ "price =?, "
					+ "image =? "
					+ "where id = ?");

			preparedStatement.setTimestamp(1, coupon.getEndDate());
			preparedStatement.setInt(2, coupon.getAmountOfCoupons());
			preparedStatement.setString(3, coupon.getMessage());
			preparedStatement.setDouble(4, coupon.getPrice());
			preparedStatement.setString(5, coupon.getImage());
			preparedStatement.setLong(6, coupon.getId());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement);

		}
	}

	@Override
	public Coupon getCoupon(long couponId) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("select * from coupons where id = ?");
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return null;

			}
			Coupon coupon = getCouponFromResultSet(resultSet);
			return coupon;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);

		}
	}


	@Override
	public Collection<Coupon> getCouponsByCustomer(long customerId) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(
					//SQL command returns from DB all coupons which belong to the customer by the company id according join table 
					"select * "
					+ "from coupons "
					+ "join customer_coupon "
					+ "on customer_coupon.coupon = coupons.id "
					+ "where customer_coupon.customer = ?;");

			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();
			Collection<Coupon> allCoupons = getCouponsFromResultSet(resultSet);
			return allCoupons;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}

	}

	@Override
	public Collection<Coupon> getCouponsByCompany(long companyId) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(
					//SQL command returns from DB all coupons which belong to the company by the company id according join table
					"select * "
					+ "from coupons "
					+ "join company_coupon "
					+ "on company_coupon.coupon = coupons.id "
					+ "where company_coupon.company = ?");

			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();
			Collection<Coupon> allCoupons = getCouponsFromResultSet(resultSet);
			return allCoupons;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);

		}
	}

	@Override
	public Collection<Coupon> getAllCoupons() throws ApplicationException {
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("select * from coupons");
			resultSet = preparedStatement.executeQuery();
			Collection<Coupon> allCoupons = getCouponsFromResultSet(resultSet);
			return allCoupons;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}

	}

	@Override
	public boolean isCouponExistsById(long couponId) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("select * from coupons where id = ?");
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();
			return resultSet.next() ? true : false ;

		}catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public Collection<Coupon> getCouponsByCompanyAndType(long companyId, String couponType)throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(
					// SQL command returns coupons from the DB which belong to specify company by company id and coupon type.
					"select * from coupons join company_coupon "
					+ "on coupons.id = company_coupon.coupon "
					+ "where coupons.type = ? "
					+ "and company_coupon.company = ?");

			preparedStatement.setString(1, couponType);
			preparedStatement.setLong(2, companyId );
			resultSet = preparedStatement.executeQuery();
			Collection<Coupon> allCoupons = getCouponsFromResultSet(resultSet);
			return allCoupons;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);

		}
	}

	@Override
	public Collection<Coupon> getCouponsByCompanyAndPrice(long companyId, double price) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(
					// SQL command returns coupons from the DB which belong to specify company by company id and by a certain price.
					"select * from coupons join company_coupon "
					+ "on coupons.id = company_coupon.coupon "
					+ "where coupons.price <= ? "
					+ "and company_coupon.company = ?");

			preparedStatement.setDouble(1, price);
			preparedStatement.setLong(2, companyId);
			resultSet = preparedStatement.executeQuery();

			Collection<Coupon> allCoupons = getCouponsFromResultSet(resultSet);
			return allCoupons;

		} catch (SQLException e) {

			e.printStackTrace();
			// log
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {

			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public Collection<Coupon> getCouponsByCompanyAndEndDate(long companyId, Timestamp endDate)throws ApplicationException {
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(
					
					// SQL command returns coupons from the DB which belong to specify company by company id and by a certain date.
					"select * from coupons join company_coupon "
					+ "on coupons.id = company_coupon.coupon "
					+ "where coupons.end_date = ? "
					+ "and company_coupon.company = ?");

			preparedStatement.setLong(1, companyId);
			preparedStatement.setTimestamp(2,endDate);
			resultSet = preparedStatement.executeQuery();
			Collection<Coupon> allCoupons = getCouponsFromResultSet(resultSet);
			return allCoupons;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);

		}
	}
	//The method invokes by the DailyCouponExpirationTask. This method removes all dated coupons according the current time
	public void removeExpiratedCoupons() throws ApplicationException {
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(

					// SQL Command deletes coupons which end_date <= current time from the DB.
					"delete company_coupon, customer_coupon, coupons "
					+"from company_coupon " 
					+"join coupons on coupons.id = company_coupon.coupon "
					+"left join customer_coupon on coupons.id = customer_coupon.coupon " 
					+"where coupons.end_date <= now()");

			preparedStatement.executeUpdate();		
		} catch (SQLException e) {
			e.printStackTrace();
			// log
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement);
		}
	}
	
	//This method inserts customer id and coupon id to the join table which represents relation customer_coupon. In another words client buys coupon.
	public void purchaseCoupon(long customerId, long couponId) throws ApplicationException{

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;

		try{			
			connection.setAutoCommit(false);			
			preparedStatement1 = connection.prepareStatement("insert into customer_coupon values(?,?)");
			preparedStatement1.setLong(1, customerId);
			preparedStatement1.setLong(2, couponId);
			preparedStatement1.executeUpdate();
			preparedStatement2 = connection.prepareStatement("update coupons set amount = amount - 1 where id = ?");
			preparedStatement2.setLong(1, couponId);
			preparedStatement2.executeUpdate();			
			connection.commit();

		}catch (SQLException e){
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally{			
			connectionPool.closeResourses(connection, preparedStatement1);
			connectionPool.closeResourses(null, preparedStatement2);
		}
	}
	@Override
	public Collection<Coupon> getCouponsByCustomerIdAndPrice(long customerId, double price)throws ApplicationException{	

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(
					
					// SQL command returns coupons from the DB which belong to specify customer by customer id and by a certain price.
					"select * from coupons join customer_coupon "
					+ "on coupons.id = customer_coupon.coupon "
					+ "where coupons.price <= ? "
					+ "and customer_coupon.customer = ?");
			
			preparedStatement.setDouble(1, price);
			preparedStatement.setLong(2, customerId);
			resultSet = preparedStatement.executeQuery();

			Collection<Coupon> allCoupons = getCouponsFromResultSet(resultSet);
			return allCoupons;

		} catch (SQLException e) {
			e.printStackTrace();
			// log
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);

		}	
	}

	public Collection<Coupon> getCouponsByCustomerIdAndType(long customerId, String couponType)throws ApplicationException{
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(
					
					// SQL command returns coupons from the DB which belong to specify customer by customer id and by a certain coupon type.
					"select * from coupons join customer_coupon "
					+ "on coupons.id = customer_coupon.coupon "
					+ "where coupons.type = ? "
					+ "and customer_coupon.customer = ?");
			
			preparedStatement.setString(1, couponType);
			preparedStatement.setLong(2,customerId);
			resultSet = preparedStatement.executeQuery();
			Collection<Coupon> allCoupons = getCouponsFromResultSet(resultSet);
			return allCoupons;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		} finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);

		}
	}


	public boolean isCouponBelongsToCompany( long couponId, long companyId) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement("select * from company_coupon where company = ? and coupon = ?");
			preparedStatement.setLong(1, companyId);
			preparedStatement.setLong(2, couponId);
			resultSet = preparedStatement.executeQuery();	
			return resultSet.next() ? true : false ;

		}catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}	
	}

	@Override
	//	Check if customer bought the coupon 
	public boolean isCustomerGotTheCoupon(long couponId, long customerId) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("select * from customer_coupon where customer = ? and coupon = ?");
			preparedStatement.setLong(1, customerId);
			preparedStatement.setLong(2, couponId);
			resultSet = preparedStatement.executeQuery();	
			return resultSet.next() ? true : false ;

		}catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);

		}	

	}

	@Override
	public boolean isCouponInStock(long couponId) throws ApplicationException {

		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement("select amount from coupons where id = ?");
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();	

			if(!resultSet.next()){
				throw new ApplicationException(ErrorTypes.GENERAL_ERROR, "Can't get amoumt");
			}
			//Taking the amount of the coupon from the DB
			int amount = resultSet.getInt(1);
			//checking the amount. If run out of the amount then false returns
			if(amount > 0){
				return true;
				
			}
			return false;

		}catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.GENERAL_ERROR, e, e.getMessage());

		}finally {
			connectionPool.closeResourses(connection, preparedStatement, resultSet);
		}	
	}	

	//Private functions	

	//The getCouponsFromResultSet extracting the coupons information from a result set and packing to Collection
	private Collection<Coupon> getCouponsFromResultSet(ResultSet resultSet) throws SQLException{
		List<Coupon> coupons = new ArrayList<>();

		while(resultSet.next()){

			Coupon coupon = getCouponFromResultSet(resultSet);
			coupons.add(coupon);			
		}
		return coupons;

	}
	//The getCouponFromResultSet extracting the coupons information from a result and return the ready coupon
	private Coupon getCouponFromResultSet(ResultSet resultSet) throws SQLException{

		long id = resultSet.getLong("id");
		String title = resultSet.getString("title");
		Timestamp startDate = resultSet.getTimestamp("start_date");
		Timestamp endDate = resultSet.getTimestamp("end_date");
		int amount = resultSet.getInt("amount");
		String strTypeCoupon = resultSet.getString("type");
		CouponTypes typeCoupon = CouponTypes.getType(strTypeCoupon);
		String massage = resultSet.getString("message");
		double price = resultSet.getDouble("price");
		String image = resultSet.getString("image");

		Coupon coupon = new Coupon(id, title, startDate, endDate, amount, typeCoupon, massage, price, image);

		return coupon;


	}
	private void createCompanyCoupon(long companyId, long couponId, Connection connection) throws SQLException{

		PreparedStatement preparedStatement = null;
		preparedStatement =connection.prepareStatement("insert into company_coupon values(?,?)");
		preparedStatement.setLong(1, companyId);
		preparedStatement.setLong(2, couponId);
		preparedStatement.executeUpdate();

	}

}
