package com.mati.threads;

import java.util.Timer;
import java.util.TimerTask;

import com.mati.cache.Cache;

public class CacheCleanerTimer  extends TimerTask{
	private Timer timer;
	
	//Ctor
	public CacheCleanerTimer(){
		this.timer = new Timer();
	}

	@Override	
	public void run(){
			Cache.getInstance().cleanCache();

	}

	public void startTask(){
		//Setting the timer which starts the task. 15000 millis = 15 minutes.
			timer.schedule(this, 15000L);
			
	}
	
	//Stops the timer and task
	public void stopTask() {
		timer.cancel();		
	}
}
