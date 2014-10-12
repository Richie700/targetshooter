package com.tanqbay.targetshooter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class DebugNotifier{
	
	public DebugNotifier(){
		
	}
	
	public static void notify(String message){
		
		NotificationCompat.Builder mBuilder =
        new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.bomb1)
        .setContentTitle("Error")
        .setContentText(message);
        
 NotificationCompat.BigTextStyle bigTextStyle =
        new NotificationCompat.BigTextStyle();
 
 bigTextStyle.bigText(message);
 
 mBuilder.setStyle(bigTextStyle);
 
	// Creates an explicit intent for an Activity in your app
	Intent resultIntent = new Intent(this, ResultActivity.class);
	
	// The stack builder object will contain an artificial back stack for the
	// started Activity.
	// This ensures that navigating backward from the Activity leads out of
	// your application to the Home screen.
	TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	// Adds the back stack for the Intent (but not the Intent itself)
	stackBuilder.addParentStack(ResultActivity.class);
	// Adds the Intent that starts the Activity to the top of the stack
	stackBuilder.addNextIntent(resultIntent);
	PendingIntent resultPendingIntent =
	        stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_UPDATE_CURRENT
	        );
	mBuilder.setContentIntent(resultPendingIntent);
	NotificationManager mNotificationManager =
	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	// mId allows you to update the notification later on.
	mNotificationManager.notify(mId, mBuilder.build());
		}
	
}