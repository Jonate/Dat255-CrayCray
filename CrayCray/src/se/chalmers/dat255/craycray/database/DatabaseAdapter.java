package se.chalmers.dat255.craycray.database;

import se.chalmers.dat255.craycray.model.DatabaseException;
import se.chalmers.dat255.craycray.model.NeedsModel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * A database class called when you want to add, change or get a value in the database
 * 
 */
public class DatabaseAdapter {

	private static DatabaseAdapter instance = null;

	private DatabaseHelper dbHelper;
	private SQLiteDatabase database;

	private Context ctx;

	// The name of the table for needs in the database
	public final static String NEED_TABLE="CrayCrayNeeds";

	public final static String NEED_ID="_id";
	public final static String NEED_VALUE="value";

	private DatabaseAdapter(Context context){
		dbHelper = new DatabaseHelper(context);  
		database = dbHelper.getWritableDatabase();
		ctx=context;
	}

	/**
	 * @return the single instance of DatabaseAdapter
	 */
	public synchronized static DatabaseAdapter getInstance(Context context){
		if(instance == null){
			instance = new DatabaseAdapter(context);
			return instance;
		}else{
			return instance;
		}
	}
	
	/**
	 * Reset the databese.
	 */
	public void resetDatabase(){
		ctx.deleteDatabase(DatabaseConstants.DATABASE_NAME);
		
		dbHelper = new DatabaseHelper(ctx);  
		database = dbHelper.getWritableDatabase();
		
	}

	/**
	 * A method for adding values to the database
	 * 
	 * @param id the id the value will have in the database
	 * @param value 
	 * @return the rowindex of the value added
	 * @throws DatabaseException 
	 */
	public long addValue(String id, double value) throws DatabaseException{
		ContentValues values = new ContentValues();  
		values.put(NEED_ID, id);  
		values.put(NEED_VALUE, value);  
		try{
			return database.insert(NEED_TABLE, null, values);  
		} catch(Exception e){
			throw new DatabaseException("error in addValue");
		}
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
	 * @throws DatabaseException 
	 */
	public long updateValue(String id, double value) throws DatabaseException{
		ContentValues values = new ContentValues();
		values.put(NEED_VALUE, value);  
		try{
			return database.update(NEED_TABLE, values, NEED_ID+" =?" ,new String[]{id});  
		} catch(Exception e){
			throw new DatabaseException("error in updateValue");
		}

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
	 * Get the value associated with the id.
	 * @param id
	 * @return the value
	 * @throws DatabaseException if the id does not exist
	 */
	public double getValue(String id) throws DatabaseException {
		String[] cols = new String[] {NEED_ID, NEED_VALUE};  
		Cursor mCursor = database.query(NEED_TABLE,cols, NEED_ID+"='"+id+"'"  
				, null, null, null, null, null); 

		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		try{
			return Double.parseDouble(mCursor.getString(1)); // iterate to get each value.
		}catch(Exception e){
			throw new DatabaseException("first time");
		}

	}

	/**
	 * Get the value associated with the id in String representation.
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
