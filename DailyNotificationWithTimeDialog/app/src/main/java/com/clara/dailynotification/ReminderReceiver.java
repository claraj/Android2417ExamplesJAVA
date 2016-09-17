package com.clara.dailynotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


public class ReminderReceiver extends BroadcastReceiver {
	private static final String TAG = "REMINDER RECEIVER";

	@Override
	public void onReceive(Context context, Intent intent) {

		//Create and issue a notification with the task the user wants to do daily.

		//The task the user wishes to be reminded of
		String task = intent.getStringExtra(ConfigureReminderActivity.EXTRA_TASK);
		//In this app, it will be "daily", or "testing". Could be hourly, twice daily...
		String interval = intent.getStringExtra(ConfigureReminderActivity.EXTRA_INTERVAL);

		Log.d(TAG, "**** BEEP BEEP **** Alarm received for " + task + " interval " + interval);

		String notificationMessage = interval + " reminder to " + task;

		Intent returnToApp = new Intent(context, ConfigureReminderActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, returnToApp, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationManager notificationManager = (NotificationManager)
				context.getSystemService(Context.NOTIFICATION_SERVICE);

		//Build notification
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setSmallIcon(android.R.drawable.star_on)    //TODO swap for app icon
				.setContentTitle("Daily Reminder")
				.setContentText(notificationMessage)
				.setContentIntent(pendingIntent);

		Notification notification = builder.build();

		int n_id = 0;   //Used by our app to identify a particular notification. It doesn't need to be unique system-wide.

		//Cancel a previous notification, if there is one.

		notificationManager.cancel(n_id);

		//And issue new notification
		notificationManager.notify(n_id, notification);



	}
}

