package se.chalmers.dat255.craycray.databasetest;

import org.junit.Assert;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import se.chalmers.dat255.craycray.database.DatabaseAdapter;
import se.chalmers.dat255.craycray.model.DatabaseException;

public class DatabaseAdapterTest extends AndroidTestCase{

	private DatabaseAdapter adapter;
	
	public void setUp(){
		
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_"); 
		adapter = DatabaseAdapter.getInstance(context);
		assertNotNull(adapter);
	
	}
	
	public void addValueTest(){
		
		//RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_"); 
		//adapter = DatabaseAdapter.getInstance(context);
		
		try{
			adapter.addValue("test0", 0);
			}
		catch(DatabaseException d){}
		/*try{
			assertTrue(adapter.addValue("test1", 3)==-1);
		}
		catch(DatabaseException d){}
		try{
			assertTrue(adapter.addValue("test2", 4)==-1);
		}
		catch(DatabaseException d){}
		try{
			assertTrue(adapter.addValue("test3", 5)==-1);
		}
		catch(DatabaseException d){}*/
	
	}
	
	public void getValueTest(){
		
		
		try{
			adapter.getValue("test0");
			Assert.fail("fail som fan");
			}
		catch(DatabaseException d){}
//		assertTrue(adapter.getValue("test1")==0);
//		adapter.addValue("test3", 5);
//		adapter.addValue("test4", 6);
//		adapter.addValue("test5", 7);
//		assertTrue(adapter.getValue("test4")==6);
//		assertTrue(adapter.getValue("adsasd")==-1);
	
	}
	
	public void updateValueTest() throws DatabaseException{
		
		assertTrue(adapter.getValue("test1")==0);
		adapter.addValue("test3", 5);
		adapter.addValue("test4", 6);
		adapter.addValue("test5", 7);
		adapter.updateValue("test4", 8);
		assertTrue(adapter.getValue("test4")==8);
	
	}
	
}
