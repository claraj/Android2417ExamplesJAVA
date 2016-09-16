package com.clara.hellonotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MAIN ACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button showNotificationNow = (Button) findViewById(R.id.notification_now);
		Button showNotificationWithPendingIntent = (Button) findViewById(R.id.notification_with_pending_intent_button);

		showNotificationNow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showNotificationNow();
			}
		});

		showNotificationWithPendingIntent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showNotificationLinksBackToApp();
			}
		});
	}

	private void showNotificationNow() {

		Log.i(TAG, "Creating notification to show now");

		// Build notification. You *MUST* set the small icon, content title, content text
		// or notification will silently be ignored
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				.setSmallIcon(android.R.drawable.star_on)
				.setContentTitle("Hello!")
				.setContentText("This is a notification");

		//Build the notification
		Notification notification = builder.build();

		//Get reference to the NotificationManager service
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		int notificationId = 0;   //You may use this to manage or cancel the Notification from the app

		//Request Notification is issued - it should show in the status bar
		notificationManager.notify(notificationId, notification);
	}


	private void showNotificationLinksBackToApp() {
		Log.i(TAG, "Creating notification with Pending Intent");

		// Create a regular Intent that to launch this Activity.
		// You could launch another Activity in your app if desired.
		Intent launchThisActivity = new Intent(this, MainActivity.class);

		int requestId = (int) System.currentTimeMillis(); //just need a unique number

		//Wrap this Intent in a PendingIntent - an Intent that is saved to be activated at a later time
		PendingIntent pendingIntent = PendingIntent.
				getActivity(this, requestId, launchThisActivity, PendingIntent.FLAG_CANCEL_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				.setSmallIcon(android.R.drawable.btn_plus)   //Typically your app icon, or use Android system drawables
				.setContentTitle("Hello!")
				.setContentText("Click me to launch the app")
				.setContentIntent(pendingIntent);

		//Build the notification
		Notification notification = builder.build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		int notificationId = 1;   //You may use this to manage or cancel the Notification from the app

		//Request Notification is issued - it should show in the status bar
		notificationManager.notify(notificationId, notification);
	}


}
