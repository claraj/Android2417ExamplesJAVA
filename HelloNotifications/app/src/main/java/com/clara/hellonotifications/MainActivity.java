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
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MAIN ACTIVITY";

	private static final String EXTRA_DATE_NOTIFICATION = "Date Notification Created";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button showNotificationNow = (Button) findViewById(R.id.notification_now);
		Button showNotificationWithPendingIntent = (Button) findViewById(R.id.notification_with_pending_intent_button);
		Button showNotificationWithIntentWithData = (Button) findViewById(R.id.notification_with_pending_intent_with_data_button);

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

		showNotificationWithIntentWithData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showNotificationLinksBackWithData();
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
				.setContentText("This is a notification")
				.setAutoCancel(true);

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

		int requestId = 1; //just need a unique number

		//Wrap this Intent in a PendingIntent - an Intent that is saved to be activated at a later time
		PendingIntent pendingIntent = PendingIntent.
				getActivity(this, requestId, launchThisActivity, PendingIntent.FLAG_CANCEL_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				.setSmallIcon(android.R.drawable.btn_plus)   //Typically your app icon, or use Android system drawables
				.setContentTitle("Hello!")
				.setContentText("Click me to launch the app")
				.setContentIntent(pendingIntent)
				.setAutoCancel(true);

		//Build the notification
		Notification notification = builder.build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		int notificationId = 1;   //You may use this to manage or cancel the Notification from the app

		//Request Notification is issued - it should show in the status bar
		notificationManager.notify(notificationId, notification);
	}


	private void showNotificationLinksBackWithData() {

		Log.i(TAG, "Creating notification with Pending Intent that contains data");

		// Create a regular Intent that to launch this Activity.
		// You could launch another Activity in your app if desired.
		Intent launchThisActivity = new Intent(this, MainActivity.class);

		int requestId = 2; //just need a unique number

		// Add any data as extras. Example: the notification may provide some
		// unique data to the user, that when the user taps on the notification, the app will need.
		// Here, will send String with the time the Notification was created
		String timeThisNotificationWasCreated = new Date().toString();  // Defaults to now

		launchThisActivity.putExtra(EXTRA_DATE_NOTIFICATION, timeThisNotificationWasCreated);

		//Wrap this Intent in a PendingIntent - an Intent that is saved to be activated at a later time
		PendingIntent pendingIntent = PendingIntent.
				getActivity(this, requestId, launchThisActivity, PendingIntent.FLAG_CANCEL_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				.setSmallIcon(android.R.drawable.btn_plus)   //Typically your app icon, or use Android system drawables
				.setContentTitle("Hello!")
				.setContentText("Click me to launch the app and send data")
				.setContentIntent(pendingIntent)
				.setAutoCancel(true);

		//Build the notification
		Notification notification = builder.build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		int notificationId = 2;   //You may use this to manage or cancel the Notification from the app

		//Request Notification is issued - it should show in the status bar
		notificationManager.notify(notificationId, notification);
	}


	@Override
	protected void onResume() {
		super.onResume();

		Intent intent = getIntent();
		TextView showNotificationCreationTime = (TextView) findViewById(R.id.show_intent_data);

		if (intent.getExtras() != null) {
			//If there is data in the intent, get the Extra set when the Notification was created and display
			String dateCreated = intent.getStringExtra(EXTRA_DATE_NOTIFICATION);
			showNotificationCreationTime.setText("The notification that launched this app was created at " + dateCreated);
		} else {
			showNotificationCreationTime.setText("No data found in Intent");
		}
	}
}


