package se.chalmers.dat255.craycray.model;

import android.util.Log;

public class NeedsModel {
	
	private static NeedsModel instance = null;
	private int hungerCount;

	private NeedsModel(){
		hungerCount = 0;
	}
	
	public synchronized static NeedsModel getInstance(){
		if(instance == null){
			Log.w("Thread", "Creating model");
			instance = new NeedsModel();
			return instance;
		}else{
			return instance;
		}
	}
	
	public synchronized int getHungerCount(){
		return hungerCount;
	}
	
	public synchronized void setHungerCount(int hunger){
		if(hunger < 100){
			hungerCount = hunger;
		}else{
			hungerCount = 100;
		}
	}

}
