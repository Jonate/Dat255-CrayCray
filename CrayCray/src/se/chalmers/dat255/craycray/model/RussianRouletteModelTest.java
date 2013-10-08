package se.chalmers.dat255.craycray.model;

import junit.framework.TestCase;

public class RussianRouletteModelTest extends TestCase {
	
	RussianRouletteModel rModel = new RussianRouletteModel();
	

	public void getRandomTest(){
		
		for(int i = 0; i <= 20; i++){
			int random = rModel.getRandom();
			assertTrue(random <= 5 && random >= 0);
		}
		
	}
}
