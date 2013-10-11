package se.chalmers.dat255.craycray.model;



import java.util.TimerTask;
import se.chalmers.dat255.craycray.R;
import se.chalmers.dat255.craycray.util.Constants;
import android.util.Log;
import android.widget.ImageView;

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
	private int energyLevel;
	
	private int illCount;
	
	private boolean ill;
	private boolean sleeping;
	private boolean diedOfRussian = false;
	
	private NeedsModel(){
		maxAllNeeds();
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
	
	public synchronized int getIllCount(){
		return illCount;
	}
	
	public synchronized int getEnergyLevel(){
		return energyLevel;
	}
	
	public synchronized boolean isIll(){
		return ill;
	}
	
	public synchronized boolean isSleeping(){
		return sleeping;
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
			hunger = 0;
			String deathCause;
			if(!diedOfRussian){
				deathCause = Constants.HUNGER_DEATH;
				throw new DeadException(deathCause);
			}else{
				deathCause = Constants.RUSSIAN_DEATH;
				throw new DeadException(deathCause);
			}
		}else if(hunger < Constants.NEED_LEVEL_MAX && hunger > 0){
			hungerLevel = hunger;

		}else{
			hungerLevel = Constants.NEED_LEVEL_MAX;
		}
	}
	
	
	/**
	 * Method for setting CrayCrays cleanness level
	 * @param clean
	 */
	public synchronized void setCleanLevel(int clean){
		if(clean <= 0){
			cleanLevel = 0;
//			setIllness(true);
		}else if(clean < Constants.NEED_LEVEL_MAX && clean > 0){
			cleanLevel = clean;
		}else{
			cleanLevel = Constants.NEED_LEVEL_MAX;
		}
	}
	
	/**
	 * Method for setting CrayCrays cuddle level
	 * @param cuddle
	 */
	public synchronized void setCuddleLevel(int cuddle){
		if(cuddle <= 0){
			cuddleLevel = 0;
		}else if(cuddle < Constants.NEED_LEVEL_MAX && cuddle > 0){
			cuddleLevel = cuddle;
		}else{
			cuddleLevel = Constants.NEED_LEVEL_MAX;
		}
	}
	
	/**
	 * Method for setting CrayCrays energy level
	 * @param energy
	 */
	public synchronized void setEnergyLevel(int energy){
		if(energy <= 0){
			energyLevel = 0;
		}else if(energy < Constants.NEED_LEVEL_MAX && energy > 0){
			energyLevel = energy;
		}else{
			energyLevel = Constants.NEED_LEVEL_MAX;
		}
	}
	
	/**
	 * Method for setting how much CrayCray needs to poo.
	 * @param pooNeed
	 */
	public synchronized void setPooLevel(int pooNeed){
		
		if(pooNeed <=0){
			pooLevel = 0;
		}else if(pooNeed < Constants.NEED_LEVEL_MAX && pooNeed >0){
			pooLevel = pooNeed;
		}else{
			pooLevel = Constants.NEED_LEVEL_MAX;
		}
	}
	
	/**
	 * Set if CrayCray is ill or not. 
	 */
	public synchronized void setIllness(boolean state){
		this.ill = state;
	}
	
	/**
	 * 
	 * @param count the new value of the illCount
	 */
	public synchronized void setIllCount(int count){
		illCount = count;
		
	}
	
	/**
	 * Set if CrayCray is asleep or not. 
	 */
	public synchronized void setSleep(boolean state){
		this.sleeping = state;
	}
	
	/**
	 * @throws DeadException 
	 * 
	 */
	public void diedOfRussian(){
		diedOfRussian = true;
	}
	
	/**
	 * Maximize all needs.
	 */
	public void maxAllNeeds(){
		hungerLevel = Constants.NEED_LEVEL_MAX;
		cuddleLevel = Constants.NEED_LEVEL_MAX;
		cleanLevel = Constants.NEED_LEVEL_MAX;
		pooLevel = Constants.NEED_LEVEL_MAX;
		energyLevel =Constants.NEED_LEVEL_MAX;
		
		ill = false;
		
	}
	
	/**
	 * Minimize all needs.
	 * @throws DeadException 
	 */
	public void minAllNeeds(){
		hungerLevel = Constants.NEED_LEVEL_MIN;
		cuddleLevel = Constants.NEED_LEVEL_MIN;
		cleanLevel = Constants.NEED_LEVEL_MIN;
		pooLevel = Constants.NEED_LEVEL_MIN;
		energyLevel =Constants.NEED_LEVEL_MIN;
		
		ill = true;
		
	}

	public void killWhenIll() throws DeadException{
		if(illCount == 0){
			String deathCause = "CrayCray died of illness!";
			throw new DeadException(deathCause);
		}
	}
	

}
