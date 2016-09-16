package com.clara.dailynotification;

import android.app.Notification;
import android.app.NotificationManager;
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

		//The task
		String task = intent.getStringExtra(ConfigureReminderActivity.EXTRA_TASK);
		//"Daily", or "testing"
		String interval = intent.getStringExtra(ConfigureReminderActivity.EXTRA_INTERVAL);

		Log.d(TAG, "Alarm received for " + task + " interval " + interval);

		String notificationMessage = interval + " reminder to " + task;

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		builder.setSmallIcon(android.R.drawable.star_on)
				.setContentTitle("Daily Reminder")
				.setContentText(notificationMessage);

		Notification notification = builder.build();

		int id = 0;
		notificationManager.notify(id, notification);

	}
}
