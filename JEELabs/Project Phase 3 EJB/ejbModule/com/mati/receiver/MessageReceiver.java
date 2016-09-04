package com.mati.receiver;


import java.io.IOException;

import javax.ejb.*;
import javax.jms.*;

import org.jboss.metatype.plugins.values.mappers.ObjectNameMetaMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mati.entities.Income;
import com.mati.managers.IncomeManagerInterface;


// When JBoss loads, it reads this annotation and interprets it in a manner which leads him to 
// create a pool of LogMDBean objects (much like stateless sessions beans)
// JBoss will manage the whole connection to the queue, and each time a message arrives
// it will extract an object from the pool (a "listeners" pool).
// It is guaranteed that this object will have the onMessage() method 
// Each class commits to "implements javax.jms.MessageListener", and so the onMessage()
// function is called upon the message arrival
@MessageDriven(activationConfig={
		// A key value like initialization, which means - "we're connecting to a queue and not to a topic"   
		@ActivationConfigProperty(
				   propertyName="destinationType",
				   propertyValue="javax.jms.Queue"),
		   
		// An initialization which says "The queue name is Statistic"
		   @ActivationConfigProperty(propertyName="destination", 
		                             propertyValue="queue/Statistic")})

public class MessageReceiver implements MessageListener{

	@EJB
	private IncomeManagerInterface incomManager; 
	
	
	public void onMessage(Message msg) {
		ObjectMapper objMapper = new ObjectMapper();
		try {
			String incomeJson = ((TextMessage)msg).getText();
			System.out.println(incomeJson);
			Income income = objMapper.readValue(incomeJson, Income.class);
			
			incomManager.storeIncome(income);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
