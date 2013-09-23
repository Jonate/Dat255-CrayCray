package se.chalmers.dat255.craycray.model;

import android.util.Log;

//A model class for keeping track of CrayCrays Hunger/Health level
//Designpattern Singleton is used to avoid conflicts if Hunger/Health levels are used in different threads

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
	
	//sets the hungerlevel counter to specified int
	//if hungerlevel is 100, and this method is called, hungerlevel stays at 100
	public synchronized void setHungerCount(int hunger){
		if(this.getHungerCount() + hunger < 100){
			hungerCount = hunger;
		}else{
			hungerCount = 100;
		}
	}

}
