package se.chalmers.dat255.craycray.model;

import se.chalmers.dat255.craycray.util.Constants;

/**
 * A class for playing russian roulette with 5/6 chance to win.
 * Manipulates NeedsModel according to result. Win means all
 * needs fullfilled, loosing means dying.
 */
public class RussianRouletteModel {

	NeedsModel needsModel = NeedsModel.getInstance();
	
	/**
	 * Returns a random number between 0 and 5.
	 */
	public int getRandom(){
		return (int)(Math.random()* 4);
	}
	
	/**
	 * Play Russian Roulette and manipulate NeedsModel
	 * according to result. 5/6 chance to win.
	 *  Win means all needs fullfilled, loosing means dying. 
	 *  Manipulates NeedsModel according to result.
	 */
	public void play(){
		int random = this.getRandom();
		if(random == 0){
			needsModel.kill(Constants.RUSSIAN_DEATH);
		}else{
			needsModel.maxAllNeeds();
		}
		
	}
	
}
