package se.chalmers.dat255.craycray.notifications;

import se.chalmers.dat255.craycray.R;
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
	
	public NotificationSender(Context context){
		this.ctx = context;
	}
	
	public void sendDeadNotification(){
		//Set activity shown when clicked.
		Intent intent = new Intent();
		PendingIntent pIntent = PendingIntent.getActivity(ctx, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK );
		
		//Make notification.
		Notification noti = new Notification.Builder(ctx)
		.setContentTitle("You're a bad person")
		.setContentText("Your sweet CrayCray died.")
		.setSmallIcon(R.drawable.ic_launcher)
		.setContentIntent(pIntent)
		.build();		
		
		//Send notification.
		NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(DEAD_NOTI, noti);
		
	}

}
