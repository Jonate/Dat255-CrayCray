package se.chalmers.dat255.craycray.database;

/**
 * All the database constants are stored here
 * 
 */
public class DatabaseConstants {
	
	public static final String DATABASE_NAME= "craycray_needs_database";
	
	public static final String DATABASE_CREATE = "create table CrayCrayNeeds " +
			"( _id String primary key, value text not null);";

	public static final String FIRST_OF_TIME = "FirstOfTime";
	
	
	
	public static String HUNGER="Hunger";
	public static String TIME="Time";
	public static String CUDDLE="Cuddle";
	public static String CLEAN="Clean";
	public static String POO="Poo";
	public static String ENERGY="Energy";
	public static String SLEEPING="Sleeping";
	public static String ILL="Ill";
	public static String POOPED="Pooped";
	public static String ILL_COUNT="IllCount";

}
