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
	private static final String DATABASE_NAME= "craycray_needs_database";
	
	private static final String DATABASE_CREATE = "create table CrayCrayNeeds " +
			"( _id String primary key, value text not null);";
	
	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version" + oldVersion 
				+ "to version" + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS CrayCrayNeeds");
		onCreate(db);
		
	}

}
