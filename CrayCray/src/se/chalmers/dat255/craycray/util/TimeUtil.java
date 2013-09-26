package se.chalmers.dat255.craycray.util;

import java.util.Calendar;

public class TimeUtil {

	private static int SECONDS_PER_YEAR=31556926;
	private static int SECONDS_PER_MONTH=2629744;
	private static int SECONDS_PER_DAY=86400;
	private static int SECONDS_PER_HOUR=3600;
	private static int SECONDS_PER_MINUTE=60;
	
	
	public static String getCurrentTime(){
		Calendar c=Calendar.getInstance();
		return c.get(Calendar.YEAR)+","+c.get(Calendar.MONTH)+","+c.get(Calendar.DATE)+","
				+c.get(Calendar.HOUR_OF_DAY)+","+c.get(Calendar.MINUTE)+","+c.get(Calendar.SECOND);
	}
	
	public static int compareTime(String oldTime){
		Calendar c=Calendar.getInstance();
		int differenceInSeconds=0;
		
		String[] oldTimeSplit=oldTime.split(",");
		differenceInSeconds=differenceInSeconds+((c.get(Calendar.YEAR)-Integer.parseInt(oldTimeSplit[0]))*SECONDS_PER_YEAR);
		differenceInSeconds=differenceInSeconds+((c.get(Calendar.MONTH)-Integer.parseInt(oldTimeSplit[1]))*SECONDS_PER_MONTH);
		differenceInSeconds=differenceInSeconds+((c.get(Calendar.DATE)-Integer.parseInt(oldTimeSplit[2]))*SECONDS_PER_DAY);
		differenceInSeconds=differenceInSeconds+((c.get(Calendar.HOUR_OF_DAY)-Integer.parseInt(oldTimeSplit[3]))*SECONDS_PER_HOUR);
		differenceInSeconds=differenceInSeconds+((c.get(Calendar.MINUTE)-Integer.parseInt(oldTimeSplit[4]))*SECONDS_PER_MINUTE);
		differenceInSeconds=differenceInSeconds+((c.get(Calendar.SECOND)-Integer.parseInt(oldTimeSplit[5])));
		
		return differenceInSeconds;
	}
}
