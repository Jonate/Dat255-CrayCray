package se.chalmers.dat255.craycray.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase database;

	public final static String NEED_TABLE="CrayCrayNeeds";

	public final static String NEED_ID="_id";
	public final static String NEED_VALUE="value";

	public DatabaseAdapter(Context context){
		dbHelper = new DatabaseHelper(context);  
		database = dbHelper.getWritableDatabase();
	}

	public long createRecords(String id, String name){  
		ContentValues values = new ContentValues();  
		values.put(NEED_ID, id);  
		values.put(NEED_VALUE, name);  
		return database.insert(NEED_TABLE, null, values);  
	}    

	public Cursor selectRecords() {
		String[] cols = new String[] {NEED_ID, NEED_VALUE};  
		Cursor mCursor = database.query(true, NEED_TABLE,cols,null  
				, null, null, null, null, null); 
		
		if (mCursor != null) {
			mCursor.moveToFirst();
		}  
		
		return mCursor; // iterate to get each value.
	}
}
