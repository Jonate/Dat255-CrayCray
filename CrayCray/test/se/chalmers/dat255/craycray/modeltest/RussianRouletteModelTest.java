package se.chalmers.dat255.craycray.modeltest;

import static org.junit.Assert.*;

import org.junit.Test;

import android.test.AndroidTestCase;

import se.chalmers.dat255.craycray.model.RussianRouletteModel;

import junit.framework.TestCase;

/**
 * A class for testing the class RussianRouletteModel
 *
 */
public class RussianRouletteModelTest extends AndroidTestCase {
	
	RussianRouletteModel rModel = new RussianRouletteModel();
	

	@Test
	public void getRandomTest(){
		
		for(int i = 0; i <= 20; i++){
			int random = rModel.getRandom();
			assertTrue(random <= 5 && random >= 0);
		}
		
	}
}

