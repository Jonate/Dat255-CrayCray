package se.chalmers.dat255.craycray.notifications;

import se.chalmers.dat255.craycray.R;
import se.chalmers.dat255.craycray.activity.MainActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Sends notifications.
 */
public class NotificationSender {

	private Context ctx;

	private final int DEAD_NOTI = 0;
	private final int DIRTY_NOTI = 1;
	private final int ILL_NOTI = 2;

	/**
	 * Creates a NotificationSender with the given Context.
	 * @param context
	 */
	public NotificationSender(Context context){
		this.ctx = context;

	}

	/**
	 * Creates and sends a notification telling the user
	 * CrayCray has died.
	 */
	public void sendDeadNotification(){	
		//Set activity shown when clicked.
		Intent intent = new Intent(ctx, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(ctx, DEAD_NOTI, intent, Intent.FLAG_ACTIVITY_NEW_TASK);

		//Make notification.
		Notification noti = new Notification.Builder(ctx)
		.setContentTitle("You're a bad person")
		.setContentText("Your sweet CrayCray died.")
		.setSmallIcon(R.drawable.dead_baby)
		.setContentIntent(pIntent)
		.build();		

		//Send notification.
		NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(DEAD_NOTI, noti);

	}

	/**
	 * Creates and sends a notification telling the user
	 * CrayCray is Dirty.
	 */
	public void sendDirtyNotification(){
		//Set activity shown when clicked.
		Intent intent = new Intent(ctx, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(ctx, DIRTY_NOTI, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		//Make notification.
		Notification noti = new Notification.Builder(ctx)
		.setContentTitle("I'm Dirty")
		.setContentText("*hihi*")
		.setSmallIcon(R.drawable.dirty_baby)
		.setContentIntent(pIntent)
		.build();		

		//Send notification.
		NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(DIRTY_NOTI, noti);
	}
	
	/**
	 * Creates and sends a notification telling the user
	 * CrayCray is Ill.
	 */
	public void sendIllNotification(){
		//Set activity shown when clicked.
		Intent intent = new Intent(ctx, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(ctx, ILL_NOTI, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		//Make notification.
		Notification noti = new Notification.Builder(ctx)
		.setContentTitle("I'm feeling sick :(")
		.setContentText("Please, please cuuuuuure me!")
		.setSmallIcon(R.drawable.sick_baby)
		.setContentIntent(pIntent)
		.build();		

		//Send notification.
		NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(ILL_NOTI, noti);
	}

}
