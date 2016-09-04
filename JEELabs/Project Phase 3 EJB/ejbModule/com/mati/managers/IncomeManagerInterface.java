package com.mati.managers;

import java.util.Collection;

import javax.ejb.Local;

import com.mati.entities.Income;

@Local
public interface IncomeManagerInterface {
	void storeIncome(Income income);
	Collection <Income> viewAllIncome();
	Collection <Income> viewAllIncomeByUser(long userId);

	
}
