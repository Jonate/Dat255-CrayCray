package se.chalmers.dat255.craycray.modeltest;

import android.test.AndroidTestCase;

import se.chalmers.dat255.craycray.model.RussianRouletteModel;

/**
 * A class for testing the class RussianRouletteModel
 *
 */
public class RussianRouletteModelTest extends AndroidTestCase {
	
	RussianRouletteModel rModel = new RussianRouletteModel();
	
	/**
	 * Test if it is possible to get a random number between 0 and 5
	 */
	public void getRandomTest(){
		
		for(int i = 0; i <= 20; i++){
			int random = rModel.getRandom();
			assertTrue(random <= 5 && random >= 0);
		}
		
	}
	
}

