package com.mati.api;

import java.sql.Timestamp;
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
import com.mati.beans.Coupon;
//import com.mati.entities.Income;
import com.mati.enums.IncomAmounts;
import com.mati.enums.IncomeDescription;
import com.mati.exceptions.ApplicationException;
import com.mati.logic.CouponLogic;
//import com.mati.statistics.Statistic;

@Path("/coupons")
public class CouponsApi{

	@POST
	@Path("/{companyId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCoupon(@PathParam("companyId")long companyId, Coupon coupon) throws ApplicationException{
		
		CouponLogic couponLogic = new CouponLogic();
		couponLogic.createCoupon(coupon, companyId);
//		
//		Income income = new Income(companyId ,IncomeDescription.COMPANY_NEW_COUPON.getDescription(), IncomAmounts.CREATE_COUPON.getIncomeAmount());
//		Statistic.getInstance().storeIncome(income);
	}

	@PUT
	@Path("/{companyId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCoupon(@PathParam("companyId")long companyId, Coupon coupon)throws ApplicationException {
		CouponLogic couponLogic = new CouponLogic();
		couponLogic.updateCoupon(coupon);	
		
//		Income income = new Income(companyId ,IncomeDescription.COMPANY_UPDATE_COUPON.getDescription(), IncomAmounts.UPDATE_COUPON.getIncomeAmount());
//		Statistic.getInstance().storeIncome(income);
	}

	@GET
	@Path("/{couponId}/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCoupon(@PathParam("couponId")long couponId, @PathParam("companyId")long companyId) throws ApplicationException {
		CouponLogic couponLogic = new CouponLogic();
		Coupon coupon = couponLogic.getCoupon(couponId, companyId);
		return coupon;
	}
	
	@DELETE
	@Path("/{couponId}")
	public void removeCoupon(@PathParam("couponId")long couponId) throws ApplicationException {
		CouponLogic couponLogic = new CouponLogic();
		couponLogic.removeCoupon(couponId);
	}
	
	@DELETE
	@Path("/removepurchasedcoupon/{couponId}/{customerId}")
	public void  removePurchasedCoupon(@PathParam("couponId")long couponId, @PathParam("customerId")long customerId) throws ApplicationException {
		CouponLogic couponLogic = new CouponLogic();
		couponLogic.removePurchasedCoupon(couponId, customerId);
	}
	
	@GET
	@Path("/allcouponsbycompanyid/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCouponsByCompanyId(@PathParam("companyId")long companyId) throws ApplicationException {
		CouponLogic couponLogic = new CouponLogic();
		Collection<Coupon> coupons = couponLogic.getAllCoupons(companyId);
		return coupons;
	}
	
	@GET
	@Path("/allcoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllCoupons() throws ApplicationException {
		CouponLogic couponLogic = new CouponLogic();
		Collection<Coupon> coupons = couponLogic.getAllCoupons();
		return coupons;
	}

	@GET
	@Path("/couponsbytype/{couponType}/{companyId}")
	@Produces(MediaType.APPLICATION_JSON )
	public Collection<Coupon> getCouponsByType(@PathParam("couponType")String couponType, @PathParam("companyId")long companyId) throws ApplicationException{
		CouponLogic couponLogic = new CouponLogic();
		Collection<Coupon> couponsByType = couponLogic.getCouponsByType(couponType, companyId);
		return couponsByType;
	}
	
	@GET
	@Path("/couponsbyprice/{price}/{companyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getCouponsByPrice(@PathParam("price")double price, @PathParam("companyId")long companyId) throws ApplicationException{
			CouponLogic couponLogic = new CouponLogic();
			Collection<Coupon> couponsByPrice = couponLogic.getCouponsByPrice(price, companyId);
			return couponsByPrice;			
	}	
	
	@GET
	@Path("/couponsbycustumerid/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByCustumerId(@PathParam("customerId")long customerId) throws ApplicationException{
		CouponLogic couponLogic = new CouponLogic();
		Collection<Coupon> couponsByCustumerId = couponLogic.getAllPurchasedCouponsByCustumerId(customerId);
		return couponsByCustumerId;

	}
	
	@GET
	@Path("/couponsbypriceandcustumerid/{price}/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByPriceAndCustumerId(@PathParam("price")double price, @PathParam("customerId")long customerId) throws ApplicationException{
		CouponLogic couponLogic = new CouponLogic();
		Collection<Coupon> couponsByPriceAndCustumerId = couponLogic.getAllPurchasedCouponsByPriceAndCustumerId(price, customerId);
		return couponsByPriceAndCustumerId;
	}
	
	@PUT
	@Path("/couponsbydate/{companyId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public  Collection<Coupon> getCouponsByDate(Timestamp date, @PathParam("companyId")long companyId) throws ApplicationException{
		CouponLogic couponLogic = new CouponLogic();
		Collection<Coupon> couponsByDate = couponLogic.getCouponsByDate(date, companyId);
		return couponsByDate;
	}
	
	@GET
	@Path("/couponsbytypeandcustumerid/{couponType}/{customerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coupon> getAllPurchasedCouponsByTypeAndCustumerId(@PathParam("couponType")String couponType, @PathParam("customerId")long customerId) throws ApplicationException{
		CouponLogic couponLogic = new CouponLogic();
		Collection<Coupon> couponsByTypeAndCustumerId = couponLogic.getAllPurchasedCouponsByTypeAndCustumerId(couponType,  customerId);
		return couponsByTypeAndCustumerId;
	}

	@PUT
	@Path("/purchasecoupon/{customerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void purchaseCouponByCustumerId(Coupon coupon, @PathParam("customerId")long customerId) throws ApplicationException{
		CouponLogic couponLogic = new CouponLogic();
		long couponId = coupon.getId();
		couponLogic.purchaseCouponByCustumerId(couponId, customerId);
		
//		double price = coupon.getPrice();
//		Income income = new Income(customerId ,IncomeDescription.CUSTOMER_PURCHASE.getDescription(), price);
//		Statistic.getInstance().storeIncome(income);
	}
}
