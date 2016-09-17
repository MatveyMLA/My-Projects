
/* Phase 3 */

/*package com.mati.statistics;

import java.util.Collection;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mati.entities.Income;
import com.mati.managers.IncomeManagerInterface;


public class Statistic {
	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueSender qsender;
	private Queue queue;
	
	private IncomeManagerInterface incomeManager;
	private static Statistic instance = new Statistic();
	
	private Statistic(){
		try{
			InitialContext ctx = getInitialContext(); 
			qconFactory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");//create Factory
			qcon = qconFactory.createQueueConnection();//create Connection
			qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);//create Session
			queue = (Queue) ctx.lookup("queue/Statistic");//create Queue
			qsender = qsession.createSender(queue);//create Sender
			qcon.start();
			
			Object ref = ctx.lookup("IncomeManager/local");
			
		    incomeManager =(IncomeManagerInterface)PortableRemoteObject.narrow(ref, IncomeManagerInterface.class);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Statistic getInstance(){
			return instance;
	}

	public synchronized void storeIncome(Income income){
		try{
			String incomeJson = incomeToJson(income);
			TextMessage msg =  qsession.createTextMessage();
			msg.setText(incomeJson);
			qsender.send(msg);	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public synchronized Collection<Income> viewAllIncome(){
		return incomeManager.viewAllIncome();
	}
	
	public synchronized Collection<Income> viewAllIncomeByUser(long userId) {
		return incomeManager.viewAllIncomeByUser(userId);
	}
	
	private String incomeToJson(Income income){
		ObjectMapper objMapper = new ObjectMapper();
		String incomeJson = null;
		try {
			incomeJson = objMapper.writeValueAsString(income);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return incomeJson;
	}
	
	
	private  InitialContext getInitialContext(){
		Hashtable<String,String> h=new Hashtable<>();
		h.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		h.put("java.naming.provider.url","localhost");
		try {
			return new InitialContext(h);
		} catch (NamingException e) {
			System.out.println("Cannot generate InitialContext");
			e.printStackTrace();
		}
		return null;
	}
}
*/