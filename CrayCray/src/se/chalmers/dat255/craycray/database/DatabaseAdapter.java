package se.chalmers.dat255.craycray.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * A database class called when you want to add, change or get a value in the database
 * 
 */
public class DatabaseAdapter {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase database;

	// The name of the table for needs in the database
	public final static String NEED_TABLE="CrayCrayNeeds";

	public final static String NEED_ID="_id";
	public final static String NEED_VALUE="value";

	public DatabaseAdapter(Context context){
		dbHelper = new DatabaseHelper(context);  
		database = dbHelper.getWritableDatabase();
	}

	/**
	 * A method for adding values to the database
	 * 
	 * @param id the id the value will have in the database
	 * @param value 
	 * @return the rowindex of the value added
	 */
	public long addValue(String id, int value){  
		ContentValues values = new ContentValues();  
		values.put(NEED_ID, id);  
		values.put(NEED_VALUE, value);  
		return database.insert(NEED_TABLE, null, values);  
	}
	
	/**
	 * A method for adding values to the database
	 * used when adding String values, for example the time
	 * 
	 * @param id the id the value will have in the database
	 * @param value 
	 * @return the rowindex of the value added
	 */
	public long addStringValue(String id, String value){  
		ContentValues values = new ContentValues();  
		values.put(NEED_ID, id);  
		values.put(NEED_VALUE, value);
		return database.insert(NEED_TABLE, null, values);  
	}    
	
	/**
	 * A method for updating values of an id that already exists in the database
	 * 
	 * @param id the id of the value that will be replaced
	 * @param value the new value
	 * @return the rowindex of the updated value
	 */
	public long updateValue(String id, int value){  
		ContentValues values = new ContentValues();
		values.put(NEED_VALUE, value);  
		return database.update(NEED_TABLE, values, NEED_ID+" =?" ,new String[]{id});  
	}
	
	/**
	 * A method for updating values of an id that already exists in the database 
	 * used when adding String values, for example the time
	 * 
	 * @param id the id of the value that will be replaced
	 * @param value the new value
	 * @return the rowindex of the updated value
	 */
	public long updateStringValue(String id, String value){  
		ContentValues values = new ContentValues();
		values.put(NEED_VALUE, value);  
		return database.update(NEED_TABLE, values, NEED_ID+" =?" ,new String[]{id});  
	}

	/**
	 * 
	 * @param id
	 * @return the value, returns -1 if the id does not exist
	 */
	public int getValue(String id) {
		String[] cols = new String[] {NEED_ID, NEED_VALUE};  
		Cursor mCursor = database.query(NEED_TABLE,cols, NEED_ID+"='"+id+"'"  
				, null, null, null, null, null); 
		
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		try{
			return Integer.parseInt(mCursor.getString(1)); // iterate to get each value.
		}catch(Exception e){
			return -1;
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return the value, returns null if the id does not exist
	 */
	public String getStringValue(String id) {
		String[] cols = new String[] {NEED_ID, NEED_VALUE};  
		Cursor mCursor = database.query(NEED_TABLE,cols, NEED_ID+"='"+id+"'"  
				, null, null, null, null, null); 
		
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		try{
			return mCursor.getString(1); // iterate to get each value.
		}catch(Exception e){
			return null;
		}				
	}
}
