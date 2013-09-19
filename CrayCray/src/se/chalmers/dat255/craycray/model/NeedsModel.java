package se.chalmers.dat255.craycray.model;

public class NeedsModel {
	
	private static NeedsModel instance = null;
	private int hungerCount;

	private NeedsModel(){
		hungerCount = 100;
	}
	
	public static NeedsModel getInstance(){
		if(instance == null){
			return new NeedsModel();
		
		}else{
			return instance;
		}
	}
	
	public int getHungerCount(){
		return hungerCount;
	}
	
	public void setHungerCount(int hunger){
		hungerCount = hunger;
	}

}
