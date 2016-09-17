package com.mati.threads;


import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mati.dao.CouponDao;
import com.mati.enums.ErrorTypes;
import com.mati.exceptions.ApplicationException;

@Component
public class DailyCouponExpirationTask extends TimerTask{
	
	@Autowired
	private CouponDao couponDao;
	private Timer timer;
	
	//Ctor
	public DailyCouponExpirationTask(){
		this.timer = new Timer();
	}

	@Override	
	public void run(){
		try {
			couponDao.removeExpiratedCoupons();

		} catch (ApplicationException e) {
			e.printStackTrace();

		}		
	}

	public void startTask() throws ApplicationException{
		//Setting the timer which starts the task 86400000 millis = 24 hours
		try{
			timer.schedule(this, 86400000L);
		}
		catch(IllegalArgumentException e){
			e.printStackTrace();
			throw new ApplicationException(ErrorTypes.COUPON_EXPIRATION_TASK, e, "Removing of expirated coupons faild");
		}

	}
	//Stops the timer and task
	public void stopTask() {
		timer.cancel();		
	}
}
