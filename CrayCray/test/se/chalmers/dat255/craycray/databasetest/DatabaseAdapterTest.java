package se.chalmers.dat255.craycray.databasetest;

import org.junit.Test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import se.chalmers.dat255.craycray.database.DatabaseAdapter;

public class DatabaseAdapterTest extends AndroidTestCase{

	private DatabaseAdapter adapter;
	
	public void setUpTest(){
		
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_"); 
		adapter = new DatabaseAdapter(context);
		assertNotNull(adapter);
	
	}
	
	public void addValueTest(){
	
		assertTrue(adapter.getValue("test1")==0);
		//assertTrue(adapter.addValue("test1", 3)==1);
		//assertTrue(adapter.addValue("test2", 4)==1);
		//assertTrue(adapter.addValue("test3", 5)==2);
	
	}
	
	public void getValueTest(){
		
		assertTrue(adapter.getValue("test1")==0);
		adapter.addValue("test3", 5);
		adapter.addValue("test4", 6);
		adapter.addValue("test5", 7);
		assertTrue(adapter.getValue("test4")==6);
		//assertTrue(adapter.getValue("adsasd")==-1);
	
	}
	
	public void updateValueTest(){
		
		assertTrue(adapter.getValue("test1")==0);
		adapter.addValue("test3", 5);
		adapter.addValue("test4", 6);
		adapter.addValue("test5", 7);
		adapter.updateValue("test4", 8);
		assertTrue(adapter.getValue("test4")==8);
	
	}
	
}
