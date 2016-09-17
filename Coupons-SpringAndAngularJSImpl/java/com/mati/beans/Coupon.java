package com.mati.beans;
import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.mati.enums.CouponTypes;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "coupon")
public class Coupon {
	@XmlElement(name = "id", required = false)
	private long id; 
	
	@XmlElement(name = "title", required = true)	
	private String title;
	
	@XmlElement(name = "startDate", required = true)
	@XmlJavaTypeAdapter(com.mati.adapters.TimestampAdapter.class)
	private Timestamp startDate;
	
	@XmlElement(name = "endDate", required = true)
	@XmlJavaTypeAdapter(com.mati.adapters.TimestampAdapter.class)
	private Timestamp endDate;
	
	@XmlElement(name = "amountOfCoupons", required = true)
	private int amountOfCoupons;
	
	@XmlElement(name = "type", required = true)
	private CouponTypes type;
	
	@XmlElement(name = "message", required = true)
	private String message;
	
	@XmlElement(name = "price", required = true)
	private double price;
	
	@XmlElement(name = "image", required = true)
	private String image;

	public Coupon(){

	}	

	public Coupon(String title, Timestamp startDate, Timestamp endDate, int amountOfCoupons, CouponTypes TYPE, String massage, double price, String image) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amountOfCoupons = amountOfCoupons;
		this.type = TYPE;
		this.message = massage;
		this.price = price;
		this.image = image;
	}

	public Coupon(long id, String title, Timestamp startDate, Timestamp endDate, int amountOfCoupons, CouponTypes TYPE, String massage, double price, String image) {		
		this(title, startDate, endDate, amountOfCoupons, TYPE, massage, price, image);
		this.id = id;		
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public int getAmountOfCoupons() {
		return amountOfCoupons;
	}

	public void setAmountOfCoupons(int amountOfCoupons) {
		this.amountOfCoupons = amountOfCoupons;
	}

	public CouponTypes getType() {
		return type;
	}

	public void setType(CouponTypes type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amountOfCoupons=" + amountOfCoupons + ", type=" + type + ", massage=" + message + ", price="
				+ price + "]";
	}




}


