package se.chalmers.dat255.craycray.notifications;

import se.chalmers.dat255.craycray.R;
import se.chalmers.dat255.craycray.activity.MainActivity;
import se.chalmers.dat255.craycray.util.Constants;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Sends notifications.
 */
public class NotificationCreator {

	private Context ctx;
	private NotificationManager notiManager;

	/**
	 * Creates a NotificationSender with the given Context.
	 * @param context
	 */
	public NotificationCreator(Context ctx){
		this.ctx = ctx;
		this.notiManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	/**
	 * Creates and sends a notification telling the user
	 * CrayCray has died.
	 */
	public Notification createDeadNotification(){	
		//Set activity shown when clicked.
		Intent intent = new Intent(ctx, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(ctx, Constants.DEAD_NOTI, intent, Intent.FLAG_ACTIVITY_NEW_TASK);

		//Make notification.
		Notification noti = new Notification.Builder(ctx)
		.setContentTitle("You're a bad person")
		.setContentText("Your sweet CrayCray died.")
		.setSmallIcon(R.drawable.dead_baby)
		.setContentIntent(pIntent)
		.build();	
		
		noti.flags = Notification.FLAG_AUTO_CANCEL;
		
		return noti;

		//Send notification.
//		NotificationManager notiManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
//		notiManager.notify(Constants.DEAD_NOTI, noti);

	}

	/**
	 * Creates and sends a notification telling the user
	 * CrayCray is Dirty.
	 */
	public Notification createDirtyNotification(){
		//Set activity shown when clicked.
		Intent intent = new Intent(ctx, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(ctx, Constants.DIRTY_NOTI, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		//Make notification.
		Notification noti = new Notification.Builder(ctx)
		.setContentTitle("I'm Dirty")
		.setContentText("*hihi*")
		.setSmallIcon(R.drawable.dirty_baby)
		.setContentIntent(pIntent)
		.build();	
		
		noti.flags = Notification.FLAG_AUTO_CANCEL;

		return noti;
		
		//Send notification.
//		NotificationManager notiManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
//		notiManager.notify(Constants.DIRTY_NOTI, noti);
	}
	
	/**
	 * Creates and sends a notification telling the user
	 * CrayCray is Ill.
	 */
	public Notification createIllNotification(){
		//Set activity shown when clicked.
		Intent intent = new Intent(ctx, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(ctx, Constants.ILL_NOTI, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		//Make notification.
		Notification noti = new Notification.Builder(ctx)
		.setContentTitle("I'm feeling sick :(")
		.setContentText("Please, please cuuuuuure me!")
		.setSmallIcon(R.drawable.sick_baby)
		.setContentIntent(pIntent)
		.build();	
		
		noti.flags = Notification.FLAG_AUTO_CANCEL;
		
		return noti;

		//Send notification.
//		NotificationManager notiManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
//		notiManager.notify(Constants.ILL_NOTI, noti);
	}
	
	/**
	 * Removes all notifications from Notification Bar
	 */
	public void removeAllNotis(){
		notiManager.cancelAll();
	}
	

}
