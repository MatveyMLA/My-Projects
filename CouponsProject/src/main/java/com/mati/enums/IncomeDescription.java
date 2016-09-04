package com.mati.enums;


//Phase 3
public enum IncomeDescription {
		
	CUSTOMER_PURCHASE("Purchase coupon income."),
	COMPANY_NEW_COUPON("Creating new coupon income."),
	COMPANY_UPDATE_COUPON("Updating coupon income.");
	
	private String description;
	
	private IncomeDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
