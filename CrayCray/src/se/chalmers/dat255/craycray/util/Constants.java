package se.chalmers.dat255.craycray.util;

/**
 * A class containing the constants needed for playing CrayCray.
 */
public class Constants {
	
	//For facial expressions
	public final static int DEFAULT = -1;
	public final static long DEFAULT_LEVEL = 0;
	public final static int HUNGER = 1;
	public final static int CLEANNESS = 2;
	public final static int HAPPINESS = 3;
	public final static int ENERGY = 4;
	public final static int DRUNK = 5;
	public final static int DEAD = 6;

	public final static int POO = 1;
	public final static int NOPOO = 2;
	
	//For the thread
	public final static long THREAD_SLEEP = 500;
	public final static double THREAD_SLEEP_SEC = THREAD_SLEEP/1000.0;
	
	//For needs
	public final static double NEED_LEVEL_MAX = 100;
	public final static double NEED_LEVEL_MIN = 0;
	public final static int MAX_DRUNK_COUNT = 7;
	public final static int ILL_COUNT = 200;
	
	//For decreasing needlevels
	public final static double HUNGERLEVELDECREASE = -0.01;
	public final static double CLEANLEVELDECREASE = -0.02;
	public final static double ENERGYLEVELDECREASE = -0.03;
	public final static double POOLEVELDECREASE = -0.09;
	public final static double CUDDLELEVELDECREASE  = -0.04;
	
	//For increasing needlevels
	public final static double HUNGERLEVELINCREASE = 1.2;
	public final static double CLEANLEVELINCREASE = 1.3;
	public final static double ENERGYLEVELINCREASE = 2.0;
	public final static double CUDDLELEVELINCREASE = 1.5;

	//For playing Russian Roulette
	public final static int RUSSIAN_REQUEST_CODE = 1;
	public final static String RUSSIAN_KEY = "key";
	
	//Death causes
	public final static String HUNGER_DEATH = "OMG! CrayCray starved to death.";
	public final static String ILLNESS_DEATH = "OMG! CrayCray died of illness";
	public final static String RUSSIAN_DEATH = "Bad luck! CrayCray lost Russian Roulette (haha *buhu*)";

	//For notifications
	public final static int DEAD_NOTI = 0;
	public final static int DIRTY_NOTI = 1;
	public final static int ILL_NOTI = 2;
}
