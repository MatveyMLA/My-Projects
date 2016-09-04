package com.mati.enums;

//Phase 3
public enum IncomAmounts {
	CREATE_COUPON(100),
	UPDATE_COUPON(10);
	
	private double incomeAmount;
	
	private IncomAmounts(double incomeAmount){
		this.incomeAmount = incomeAmount;
	}

	public double getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	
	
	
}
