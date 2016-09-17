package com.mati.logic;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mati.beans.Coupon;
import com.mati.enums.ErrorTypes;
import com.mati.exceptions.ApplicationException;
import com.mati.interfaces.daointerfaces.ICouponDao;

@Controller
public class CouponLogic {
	
	@Autowired
	private ICouponDao couponDao;

	public CouponLogic(){}

	public void createCoupon(Coupon coupon,long companyId) throws ApplicationException{
		if(coupon == null){
			throw new ApplicationException(ErrorTypes.FALSE_COUPON_INFORMATION, "Coupon is null.");			
		}		
		if(couponDao.isCouponExistsById(coupon.getId())){
			throw new ApplicationException(ErrorTypes.COUPON_ALREDY_EXSISTS, "Coupon alredy exists. Coupon id = " + coupon.getId());
		}	
		this.couponDao.createCoupon(coupon, companyId);

	}	

	public void removeCoupon(long couponId) throws ApplicationException{
		if(!couponDao.isCouponExistsById(couponId)){
			throw new ApplicationException(ErrorTypes.COUPON_NOT_FOUND, "Coupon doesn't exist. Id = " + couponId);			
		}		
		this.couponDao.removeCoupon(couponId);

	}
	
	public void removePurchasedCoupon(long couponId, long customerId) throws ApplicationException{
		if(!couponDao.isCustomerGotTheCoupon(couponId, customerId)){
			throw new ApplicationException(ErrorTypes.CUSTOMER_DOESNT_HAVE_THE_COUPON, "Coupon with Id = " + couponId + "doesn't belong to customer with id = " + customerId);
		}
		couponDao.removePurchasedCoupon(customerId, couponId);
	}
	
	public void updateCoupon(Coupon coupon) throws ApplicationException{
		if(coupon == null){
			throw new ApplicationException(ErrorTypes.FALSE_COUPON_INFORMATION, "Coupon is null.");
		}

		if(!couponDao.isCouponExistsById(coupon.getId())){
			throw new ApplicationException(ErrorTypes.COUPON_NOT_FOUND, "Coupon doesnt exists. Coupon id = " + coupon.getId());			
		}
		this.couponDao.updateCoupon(coupon);

	}
	
	public Coupon getCoupon(long couponId, long companyId) throws ApplicationException{		
		if(!couponDao.isCouponExistsById(couponId)){
			throw new ApplicationException(ErrorTypes.COUPON_NOT_FOUND, "Coupon doesnt exists. Coupon id = " + couponId);		

		}
		if(!couponDao.isCouponBelongsToCompany(couponId, companyId)){			
			throw new ApplicationException(ErrorTypes.FALSE_COUPON_INFORMATION, "Coupon doesn't belong to company whith id = " + companyId);

		}
		return couponDao.getCoupon(couponId);
	}

	public Collection<Coupon> getAllCoupons(long companyId) throws ApplicationException	{
		Collection<Coupon> coupons = this.couponDao.getCouponsByCompany(companyId);
		return coupons;

	}
	public Collection<Coupon> getAllCoupons() throws ApplicationException	{
		Collection<Coupon> coupons = this.couponDao.getAllCoupons();
		return coupons;
		
	}

	public Collection<Coupon> getCouponsByType(String couponType, long companyId) throws ApplicationException{	
		return couponDao.getCouponsByCompanyAndType(companyId, couponType);

	}

	public Collection<Coupon> getCouponsByPrice(double price, long companyId) throws ApplicationException{
		return couponDao.getCouponsByCompanyAndPrice(companyId, price);

	}

	public  Collection<Coupon> getCouponsByDate(Timestamp date, long companyId) throws ApplicationException{	
		return couponDao.getCouponsByCompanyAndEndDate(companyId, date);

	}
	
	public void purchaseCouponByCustumerId(long couponId, long customerId) throws ApplicationException{		

		if(couponDao.isCustomerGotTheCoupon(couponId, customerId)){
			throw new ApplicationException(ErrorTypes.CUSTOMER_ALREADY_HAVE_COUPON, "The coupon(id = " + couponId + ") purchased by customer (id = " + customerId +")");
		}		
		if(!couponDao.isCouponInStock(couponId)){
			throw new ApplicationException(ErrorTypes.NO_MORE_COUPONS, "No more coupons. Coupon id = " + couponId);

		}	
		couponDao.purchaseCoupon(customerId , couponId);

	}

	public Collection<Coupon> getAllPurchasedCouponsByCustumerId(long customerId) throws ApplicationException{
		Collection<Coupon> coupons = couponDao.getCouponsByCustomer(customerId);
		return coupons;

	}

	public Collection<Coupon> getAllPurchasedCouponsByPriceAndCustumerId(double price, long customerId) throws ApplicationException{
		Collection<Coupon> coupons = couponDao.getCouponsByCustomerIdAndPrice(customerId, price);
		return coupons;

	}

	public Collection<Coupon> getAllPurchasedCouponsByTypeAndCustumerId(String couponType, long customerId) throws ApplicationException{
		Collection<Coupon> coupons = couponDao.getCouponsByCustomerIdAndType(customerId, couponType);
		return coupons;

	}
}
