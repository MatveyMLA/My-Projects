package com.mati.interfaces.daointerfaces;

import java.sql.Timestamp;
import java.util.Collection;

import com.mati.beans.Coupon;
import com.mati.exceptions.ApplicationException;

public interface ICouponDao {

	void createCoupon(Coupon coupon, long companyId) throws ApplicationException;
	void removeCoupon(long couponId) throws ApplicationException;
	void updateCoupon(Coupon coupon) throws ApplicationException;
	//	void removeExpiratedCoupons() throws ApplicationException;
	boolean isCouponExistsById(long couponId) throws ApplicationException;
	Coupon getCoupon(long couponId) throws ApplicationException;
	Collection<Coupon> getAllCoupons() throws ApplicationException;
	Collection<Coupon> getCouponsByCompany(long companyId) throws ApplicationException;
	Collection<Coupon> getCouponsByCustomer(long customerId) throws ApplicationException;
	Collection<Coupon> getCouponsByCompanyAndType(long companyId, String couponType) throws ApplicationException;
	Collection<Coupon> getCouponsByCompanyAndPrice(long companyId, double price) throws ApplicationException;
	Collection<Coupon> getCouponsByCompanyAndEndDate(long companyId, Timestamp endDate) throws ApplicationException;
	void purchaseCoupon(long customerId, long couponId) throws ApplicationException;
	Collection<Coupon> getCouponsByCustomerIdAndPrice(long customerId, double price)throws ApplicationException;	
	Collection<Coupon> getCouponsByCustomerIdAndType(long customerId, String couponType)throws ApplicationException;
	boolean isCouponBelongsToCompany(long couponId, long companyId) throws ApplicationException;
	boolean isCustomerGotTheCoupon(long couponId, long customerId)throws ApplicationException;
	boolean isCouponInStock(long couponId) throws ApplicationException;
	public void removePurchasedCoupon( long customerId, long couponId) throws ApplicationException;

}
