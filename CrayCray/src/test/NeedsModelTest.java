package test;

import se.chalmers.dat255.craycray.model.DeadException;
import se.chalmers.dat255.craycray.model.NeedsModel;
import android.test.AndroidTestCase;
import junit.framework.TestCase;
	
	public class NeedsModelTest extends AndroidTestCase{
		
		public void singletonTest(){
			NeedsModel model = NeedsModel.getInstance();
			NeedsModel needsModel = NeedsModel.getInstance();
			assertSame(model, needsModel);
			
			model.setCleanLevel(10);
			assertTrue(needsModel.getCleanLevel() == 10);
			
			needsModel.setCleanLevel(30);
			assertTrue(model.getCleanLevel() == 30);
		}
		
		
		public void getHungerLevelTest() throws DeadException{
			
			NeedsModel model = NeedsModel.getInstance();
			assertTrue(model.getHungerLevel() == 100);
			
		}
		
		public void setHungerLevelTest() throws DeadException{
			
			
			NeedsModel model = NeedsModel.getInstance();
			model.setHungerLevel(50);
			assertTrue(model.getHungerLevel() == 50);
			model.setHungerLevel(200);
			assertTrue(model.getHungerLevel() == 100);
			
			try{
				model.setHungerLevel(-10);
				fail("DeadException not thrown when hungerlevel is 0");
			}catch(DeadException e){
				
			}
			assertTrue(model.getHungerLevel() == 0);
			
			try{
				model.setHungerLevel(0);
				fail("DeadException not thrown when hungerlevel is 0");
			}catch(DeadException e){
				
			}
			assertTrue(model.getHungerLevel() == 0);
		}
		
		public void getCleanLevel(){
			
			NeedsModel model = NeedsModel.getInstance();
			assertTrue(model.getCleanLevel() == 100);
		}
		
		public void setCleanLevel(){
			NeedsModel model = NeedsModel.getInstance();
			
			model.setCleanLevel(90);
			assertTrue(model.getCleanLevel() == 90);
			
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
		
		public void isIllTest(){
			
			NeedsModel model = NeedsModel.getInstance();
			assertTrue(model.isIll() == true);
			
		}
		
		public void setIllnessTest(){
			
			NeedsModel model = NeedsModel.getInstance();
			
			model.setIllness(true);
			assertTrue(model.isIll());
			
			model.setIllness(false);
			assertFalse(model.isIll());
			
		}
		
		public void getCuddleLevelTest(){
			
			NeedsModel model = NeedsModel.getInstance();
			assertTrue(model.getCuddleLevel() == 100);
			
		}
		
		public void setCuddleLevelTest(){
			
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
		
		public void getEnergylevelTest(){
			
			NeedsModel model = NeedsModel.getInstance();
			assertTrue(model.getEnergyLevel() == 100);
			
		}
		
		public void setEnergyLevelTest(){
			
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
		
		public void getPooLevelTest(){
			
			NeedsModel model = NeedsModel.getInstance();
			assertTrue(model.getPooLevel() == 100);
			
		}
		
		public void setPooLevelTest(){
			
			NeedsModel model = NeedsModel.getInstance();
			
			model.setPooLevel(55);
			assertTrue(model.getPooLevel() == 55);
			
			model.setPooLevel(109);
			assertTrue(model.getPooLevel() == 100);
			
			model.setPooLevel(-1);
			assertTrue(model.getPooLevel() == 0);
			
			model.setPooLevel(0);
			assertTrue(model.getPooLevel() == 0);
			
		}
		
		

}
