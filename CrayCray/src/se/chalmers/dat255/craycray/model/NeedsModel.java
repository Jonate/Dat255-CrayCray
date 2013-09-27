package se.chalmers.dat255.craycray.model;

import android.util.Log;

/**
 * 
 *A model class for keeping track of CrayCrays needs, including hunger, cuddling and cleanness.
 *Designpattern Singleton is used to avoid conflicts if the methods for setting the levels for 
 *needs are used in different threads.
 * 
 */


public class NeedsModel {

	private static NeedsModel instance = null;
	
	private int hungerLevel;
	private int cuddleLevel;
	private int cleanLevel;
	private int pooLevel;
	private boolean ill;
	
	//private String deathCause; on�dig?

	private NeedsModel(){
		hungerLevel = 100;
		cuddleLevel = 100;
		cleanLevel = 100;
		pooLevel = 100;
		ill = false;
		
	}

	/**
	 * @return the single instance of NeedsModel
	 */
	public synchronized static NeedsModel getInstance(){
		if(instance == null){
			instance = new NeedsModel();
			return instance;
		}else{
			return instance;
		}
	}

	/*
	 * Getters for needs.
	 */
	public synchronized int getHungerLevel(){
		return hungerLevel;
	}
		
	public synchronized int getCleanLevel(){
		return cleanLevel;
	}	

	public synchronized int getCuddleLevel(){
		return cuddleLevel;
	}
	
	public synchronized int getPooLevel(){
		return pooLevel;
	}
	
	public synchronized boolean isIll(){
		return ill;
	}

	/*
	 * Setters for needs.
	 */
	/**
	 * Method for setting CrayCrays hunger level.
	 * If hunger level is set to <= 0, DeadException is thrown.
	 * Maximum hunger level is 100.
	 * @param hunger
	 * @throws DeadException
	 */
	public synchronized void setHungerLevel(int hunger) throws DeadException{
		if(hunger <= 0){
			throw new DeadException("CrayCray starved to death!");
		}else if(hunger < 100 && hunger > 0){
			hungerLevel = hunger;

		}else{
			hungerLevel = 100;
		}
	}
	
	
	/**
	 * Method for setting CrayCrays cleanness level
	 * @param clean
	 */
	public synchronized void setCleanLevel(int clean){
		if(clean <= 0){
			cleanLevel = 0;
		}else if(clean < 100 && clean > 0){
			cleanLevel = clean;
		}else{
			cleanLevel = 100;
		}
	}
	
	/**
	 * Method for setting CrayCrays cuddle level
	 * @param cuddle
	 */
	public synchronized void setCuddleLevel(int cuddle){
		if(cuddle <= 0){
			cuddleLevel = 0;
		}else if(cuddle < 100 && cuddle > 0){
			cuddleLevel = cuddle;
		}else{
			cuddleLevel = 100;
		}
	}
	
	/**
	 * Method for setting how much CrayCray needs to poo.
	 * @param pooNeed
	 */
	public synchronized void setPooLevel(int pooNeed){
		if(pooNeed <= 0){
			pooLevel = 0;
		}else if(pooNeed < 100 && pooNeed > 0){
			pooLevel = pooNeed;
		}else{
			pooLevel = 100;
		}
	}
	
	/**
	 * Set if CrayCray is ill or not.
	 */
	public synchronized void setIllorHealthy(){
		ill = !ill;
	}

}
