package se.chalmers.dat255.craycray.modeltest;

import se.chalmers.dat255.craycray.model.NeedsModel;
import se.chalmers.dat255.craycray.util.Constants;
import android.test.AndroidTestCase;


/**
 * A class for testing the class NeedsModel.
 * 
 */

public class NeedsModelTest extends AndroidTestCase{

	private static boolean isAlive = true;
	public void singletonTest() {
		
		NeedsModel model = NeedsModel.getInstance();
		NeedsModel needsModel = NeedsModel.getInstance();
		assertSame(model, needsModel);

		model.setCleanLevel(10);
		assertTrue(needsModel.getCleanLevel() == 10);

		needsModel.setCleanLevel(30);
		assertTrue(model.getCleanLevel() == 30);
	
	}

	/**
	 * Test if it is possible to get the hungerLevel
	 * @throws DeadException
	 */
	public void getHungerLevelTest(){
		
		NeedsModel model = NeedsModel.getInstance();
		assertTrue(model.getHungerLevel() == 10);

	}

	/**
	 * Test if it is possible to set the hungerLevel 
	 * and if a DeadException is thrown
	 * @throws DeadException
	 */
	public void setHungerLevelTest(){

		NeedsModel model = NeedsModel.getInstance();
		model.setHungerLevel(50);
		assertTrue(model.getHungerLevel() == 50);
		model.setHungerLevel(200);
		assertTrue(model.getHungerLevel() == 100);
		
	}

	/**
	 * Test if it is possible to get the cleanLevel
	 */
	public void getCleanLevel() {
		
		NeedsModel model = NeedsModel.getInstance();
		assertTrue(model.getCleanLevel() == 100);
	
	}

	/**
	 * Test if it is possible to set the cleanLevel
	 */
	public void setCleanLevel() {
		
		NeedsModel model = NeedsModel.getInstance();

		model.setCleanLevel(10);
		assertTrue(model.getCleanLevel() == 10);

		model.setCleanLevel(150);
		assertTrue(model.getCleanLevel() == 100);

		model.setCleanLevel(24);
		assertTrue(model.isIll());
		assertTrue(model.getCleanLevel() == 24);

		model.setCleanLevel(-35);
		assertTrue(model.isIll());
		assertTrue(model.getCleanLevel() == 0);

		model.setCleanLevel(0);
		assertTrue(model.isIll());
		assertTrue(model.getCleanLevel() == 0);
	
	}

	/**
	 * Test if the model is ill
	 */
	public void isIllTest() {
		
		NeedsModel model = NeedsModel.getInstance();
		assertTrue(model.isIll() == true);

	}
	/**
	 * Test if it is possible to set the illness
	 */
	public void setIllnessTest() {

		NeedsModel model = NeedsModel.getInstance();

		model.setIllness(true);
		assertTrue(model.isIll());

		model.setIllness(false);
		assertFalse(model.isIll());

	}


	/**
	 * Test if it is possible to get the cuddleLevel
	 */
	public void getCuddleLevelTest() {

		NeedsModel model = NeedsModel.getInstance();
		assertTrue(model.getCuddleLevel() == 100);

	}


	/**
	 * Test if it is possible to set the cuddleLevel
	 */
	public void setCuddleLevelTest() {

		NeedsModel model = NeedsModel.getInstance();

		model.setCuddleLevel(45);
		assertTrue(model.getCuddleLevel() == 45);

		model.setCuddleLevel(1000);
		assertTrue(model.getCuddleLevel() == 100);

		model.setCuddleLevel(-15);
		assertTrue(model.getCuddleLevel() == 0);

		model.setCuddleLevel(0);
		assertTrue(model.getCuddleLevel() == 0);

	}

	/**
	 * Test if it is possible to get the energyLevel
	 */
	public void getEnergylevelTest() {

		NeedsModel model = NeedsModel.getInstance();
		assertTrue(model.getEnergyLevel() == 100);

	}

	/**
	 * Test if it is possible to set the energyLevel
	 */
	public void setEnergyLevelTest() {

		NeedsModel model = NeedsModel.getInstance();

		model.setEnergyLevel(75);
		assertTrue(model.getEnergyLevel() == 75);

		model.setEnergyLevel(-75);
		assertTrue(model.getEnergyLevel() == 0);

		model.setEnergyLevel(0);
		assertTrue(model.getEnergyLevel() == 0);

		model.setEnergyLevel(250);
		assertTrue(model.getEnergyLevel() == 100);

	}
	
	/**
	 * Test if it is possible to get the pooLevel
	 */
	public void getPooLevelTest() {
		
		NeedsModel model = NeedsModel.getInstance();
		assertTrue(model.getPooLevel() == 100);
		
	}
	
	/**
	 * Test if it is possible to set the pooLevel
	 */
	public void setPooLevelTest() {

		NeedsModel model = NeedsModel.getInstance();

		model.setPooLevel(55);
		assertTrue(model.getPooLevel() == 55);

		model.setPooLevel(101);
		assertTrue(model.getPooLevel() == 100);

		model.setPooLevel(-1);
		assertTrue(model.getPooLevel() == 0);

		model.setPooLevel(0);
		assertTrue(model.getPooLevel() == 0);

	}
	/**
	 * Test if all needs are maximized
	 */
	public void maxAllNeedsTest(){
		NeedsModel model = NeedsModel.getInstance();
		
		model.setCuddleLevel(100);
		assertTrue(model.getCuddleLevel() == Constants.NEED_LEVEL_MAX);
		
		model.setCleanLevel(100);
		assertTrue(model.getCleanLevel() == Constants.NEED_LEVEL_MAX);
		
		model.setEnergyLevel(100);
		assertTrue(model.getEnergyLevel() == Constants.NEED_LEVEL_MAX);
		
		model.setPooLevel(100);
		assertTrue(model.getPooLevel() == Constants.NEED_LEVEL_MAX);
		
		model.setHungerLevel(100);
		assertTrue(model.getHungerLevel() == Constants.NEED_LEVEL_MAX);
	}
	
	/**
	 * Test if all needs are minimized 
	 */
	public void minAllNeedsTest(){
		
		NeedsModel model = NeedsModel.getInstance();
				
		model.setCuddleLevel(0);
		assertTrue(model.getCuddleLevel() == Constants.NEED_LEVEL_MIN);
		
		model.setCleanLevel(0);
		assertTrue(model.getCleanLevel() == Constants.NEED_LEVEL_MIN);
		
		model.setEnergyLevel(0);
		assertTrue(model.getEnergyLevel() == Constants.NEED_LEVEL_MIN);
		
		model.setPooLevel(0);
		assertTrue(model.getPooLevel() == Constants.NEED_LEVEL_MIN);
		
	}
	public void killTest(){
		isAlive=false;
		assertFalse(isAlive);
		
	}

}