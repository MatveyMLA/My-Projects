package com.mati.managers;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.mati.entities.Income;


@Stateless(name = "IncomeManager")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class IncomeManager implements IncomeManagerInterface{

	@PersistenceContext(unitName="coupons_project") 
	private EntityManager manager;

	public IncomeManager() { 
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void storeIncome(Income income) {
		manager.persist(income);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<Income> viewAllIncome() {
		Query query = manager.createQuery("select object(income) "
				+ "from Income as income"
				+ "order by income.user desc");

		Collection<Income> incomes = query.getResultList();
		return incomes;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Collection<Income> viewAllIncomeByUser(long userId) {
		Query query = manager.createQuery("select object(income) "
				+ "from Income as income"
				+ "where income.user = ?1");
		query.setParameter(1 , userId);

		Collection<Income> incomes = query.getResultList();
		return incomes;
	}


}

