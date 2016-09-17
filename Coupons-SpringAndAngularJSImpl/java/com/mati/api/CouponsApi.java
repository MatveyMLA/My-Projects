package com.mati.api;

import java.sql.Timestamp;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mati.beans.Coupon;
import com.mati.exceptions.ApplicationException;
import com.mati.logic.CouponLogic;

@RestController
@RequestMapping(value = "/coupons")
public class CouponsApi{
	
	@Autowired
	CouponLogic couponLogic;
	
	@RequestMapping(value = "/{companyId}", method = RequestMethod.POST)
	public void createCoupon(@PathVariable("companyId")long companyId, @RequestBody Coupon coupon) throws ApplicationException{
		couponLogic.createCoupon(coupon, companyId);
		
//		Income income = new Income(companyId ,IncomeDescription.COMPANY_NEW_COUPON.getDescription(), IncomAmounts.CREATE_COUPON.getIncomeAmount());
//		Statistic.getInstance().storeIncome(income);
	}

	@RequestMapping(value = "/{companyId}", method = RequestMethod.PUT)
	public void updateCoupon(@PathVariable("companyId")long companyId, @RequestBody Coupon coupon)throws ApplicationException {
		couponLogic.updateCoupon(coupon);	
		
//		Income income = new Income(companyId ,IncomeDescription.COMPANY_UPDATE_COUPON.getDescription(), IncomAmounts.UPDATE_COUPON.getIncomeAmount());
//		Statistic.getInstance().storeIncome(income);
	}

	@RequestMapping(value = "/{couponId}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody Coupon getCoupon(@PathVariable("couponId")long couponId, @PathVariable("companyId")long companyId) throws ApplicationException {
		Coupon coupon = couponLogic.getCoupon(couponId, companyId);
		return coupon;
	}
	
	@RequestMapping(value = "/{couponId}", method = RequestMethod.DELETE)
	public void removeCoupon(@PathVariable("couponId")long couponId) throws ApplicationException {
		couponLogic.removeCoupon(couponId);
	}
	
	@RequestMapping(value = "/removepurchasedcoupon/{couponId}/{customerId}", method = RequestMethod.DELETE)
	public void  removePurchasedCoupon(@PathVariable("couponId")long couponId, @PathVariable("customerId")long customerId) throws ApplicationException {
		couponLogic.removePurchasedCoupon(couponId, customerId);
	}
	
	@RequestMapping(value = "/allcouponsbycompanyid/{companyId}", method = RequestMethod.GET)
	public @ResponseBody Collection<Coupon> getAllCouponsByCompanyId(@PathVariable("companyId")long companyId) throws ApplicationException {
		Collection<Coupon> coupons = couponLogic.getAllCoupons(companyId);
		return coupons;
	}
	
	@RequestMapping(value = "/allcoupons", method = RequestMethod.GET)
	public @ResponseBody Collection<Coupon> getAllCoupons() throws ApplicationException {
		Collection<Coupon> coupons = couponLogic.getAllCoupons();
		return coupons;
	}

	@RequestMapping(value = "/couponsbytype/{couponType}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody Collection<Coupon> getCouponsByType(@PathVariable("couponType")String couponType, @PathVariable("companyId")long companyId) throws ApplicationException{
		Collection<Coupon> couponsByType = couponLogic.getCouponsByType(couponType, companyId);
		return couponsByType;
	}
	
	@RequestMapping(value = "/couponsbyprice/{price}/{companyId}", method = RequestMethod.GET)
	public @ResponseBody Collection<Coupon> getCouponsByPrice(@PathVariable("price")double price, @PathVariable("companyId")long companyId) throws ApplicationException{
			Collection<Coupon> couponsByPrice = couponLogic.getCouponsByPrice(price, companyId);
			return couponsByPrice;			
	}	
	
	@RequestMapping(value = "/couponsbycustumerid/{customerId}", method = RequestMethod.GET)
	public @ResponseBody Collection<Coupon> getAllPurchasedCouponsByCustumerId(@PathVariable("customerId")long customerId) throws ApplicationException{
		Collection<Coupon> couponsByCustumerId = couponLogic.getAllPurchasedCouponsByCustumerId(customerId);
		return couponsByCustumerId;

	}
	
	@RequestMapping(value = "/couponsbypriceandcustumerid/{price}/{customerId}", method = RequestMethod.GET)
	public @ResponseBody Collection<Coupon> getAllPurchasedCouponsByPriceAndCustumerId(@PathVariable("price")double price, @PathVariable("customerId")long customerId) throws ApplicationException{
		Collection<Coupon> couponsByPriceAndCustumerId = couponLogic.getAllPurchasedCouponsByPriceAndCustumerId(price, customerId);
		return couponsByPriceAndCustumerId;
	}
	
	@RequestMapping(value = "/couponsbydate/{companyId}", method = RequestMethod.GET)
	public @ResponseBody Collection<Coupon> getCouponsByDate(@RequestBody Timestamp date, @PathVariable("companyId")long companyId) throws ApplicationException{
		Collection<Coupon> couponsByDate = couponLogic.getCouponsByDate(date, companyId);
		return couponsByDate;
	}
	
	@RequestMapping(value = "/couponsbytypeandcustumerid/{couponType}/{customerId}", method = RequestMethod.GET)
	public @ResponseBody Collection<Coupon> getAllPurchasedCouponsByTypeAndCustumerId(@PathVariable("couponType")String couponType, @PathVariable("customerId")long customerId) throws ApplicationException{
		Collection<Coupon> couponsByTypeAndCustumerId = couponLogic.getAllPurchasedCouponsByTypeAndCustumerId(couponType,  customerId);
		return couponsByTypeAndCustumerId;
	}

	@RequestMapping(value = "/purchasecoupon/{customerId}", method = RequestMethod.PUT)
	public void purchaseCouponByCustumerId(@RequestBody Coupon coupon, @PathVariable("customerId")long customerId) throws ApplicationException{
		long couponId = coupon.getId();
		couponLogic.purchaseCouponByCustumerId(couponId, customerId);
		
//		double price = coupon.getPrice();
//		Income income = new Income(customerId ,IncomeDescription.CUSTOMER_PURCHASE.getDescription(), price);
//		Statistic.getInstance().storeIncome(income);
	}
}
