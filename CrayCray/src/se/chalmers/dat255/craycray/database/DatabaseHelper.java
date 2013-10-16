package se.chalmers.dat255.craycray.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * A class where the database is created
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION=1;
	
	public DatabaseHelper(Context context){
		super(context, DatabaseConstants.DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DatabaseConstants.DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version" + oldVersion 
				+ "to version" + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS CrayCrayNeeds");
		onCreate(db);
		
	}

}
