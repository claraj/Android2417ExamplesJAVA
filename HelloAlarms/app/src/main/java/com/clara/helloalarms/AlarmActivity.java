package com.clara.helloalarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AlarmActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		Button setAlarmButton = (Button) findViewById(R.id.set_alarm_button);
		final EditText alarmDelay = (EditText) findViewById(R.id.alarm_delay_et);
		setAlarmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int delay = Integer.parseInt(alarmDelay.getText().toString());
				setAlarm(delay);
			}
		});

		final Button setRepeatingAlarmButton = (Button) findViewById(R.id.set_repeating_alarm_button);
		setRepeatingAlarmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int interval = Integer.parseInt(alarmDelay.getText().toString());
				setRepeatingAlarm(interval);
			}
		});
	}

	private void setRepeatingAlarm(int interval) {

		Log.d("Alarm Activity", "Setting a repeating alarm with period " + interval + " seconds");
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent intent = new Intent(this, AlarmReceiver.class);
		intent.putExtra("DELAY", interval);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

		long firstAlarmTime = SystemClock.elapsedRealtime() + ( interval * 1000 );

		//setInexactRepeating: what type of Alarm, when should the Alarm start, interval between Alarms, and a pendingIntent
		alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, firstAlarmTime, (interval * 1000), pendingIntent);

	}

	private void setAlarm(int seconds) {

		Log.d("Alarm Activity", "Setting an alarm for " + seconds + " seconds");

		//Get a reference to the AlarmManager service
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		//Create an Intent with target = AlarmReceiver class
		Intent intent = new Intent(this, AlarmReceiver.class);
		//Optionally, you can add some data to the Intent. The AlarmReceiver will be able to read this
		intent.putExtra("DELAY", seconds);
		//Wrap in PendingIntent
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
		//Calculate time for Alarm to fire, in Unix time - milliseconds since 1/1/1970
		//SystemClock.elapsedRealTime returns the current time in milliseconds.
		// Calculate delay in milliseconds and add to current time.

		// Set a one-time alarm with our parameters. When the Alarm fires,
		// AlarmReceiver will have onReceive method called. Make sure you've
		// registered AlarmReceiver as a receiver in the manifest.
		// with the Intent inside the PendingIntent

		//Types of alarms
		// ELAPSED_REALTIME = Alarms relative to time since boot
		// Get the current elapsed realtime with SystemClock.elapsedRealtime()

		// RTC = Alarms relative to the actual time
		// Get the current time with System.currentTimeMillis()

		//So this alarm is relative the the uptime of the phone, and will fire at current time plus alarmTime

		//long alarmRelativeToElapsedRealTime = SystemClock.elapsedRealtime() + (seconds * 1000) ;
		//alarmMgr.set(AlarmManager.ELAPSED_REALTIME, alarmRelativeToElapsedRealTime, pendingIntent);

		//This does the same thing as the 2 lines above. Alarm is set relative to actual real world clock time.
		//long alarmRelativeToRTC = System.currentTimeMillis();
		//alarmMgr.set(AlarmManager.RTC, alarmRelativeToRTC, pendingIntent);

	}

}

