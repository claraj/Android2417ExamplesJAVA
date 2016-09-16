package com.clara.dailynotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ConfigureReminderActivity extends AppCompatActivity {

	protected static final String EXTRA_INTERVAL = "EXTRA INTERVAL";
	protected static final String EXTRA_TASK = "EXTRA TASK";

	final int TEST_INTERVAL = 60 * 1000;   // 1 minute, for testing

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configure_reminder);

		Button startRemindersButton = (Button) findViewById(R.id.set_alarm_button);
		final EditText userTaskET = (EditText) findViewById(R.id.task_edit_text);
		final RadioGroup alarmInterval = (RadioGroup) findViewById(R.id.alarm_interval_radio_buttons);

		startRemindersButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				int interval = 0;
				String intervalText;
				//For testing, or a real daily alarm?
				if (alarmInterval.getCheckedRadioButtonId() == R.id.testing_alarm) {
					interval = TEST_INTERVAL;
					intervalText = "Testing, 1 minute";

				} else {
					interval = 24 * 60 * 60 * 1000; //One day, in milliseconds
					intervalText = "Daily";
				}

				String userTask = userTaskET.getText().toString();
				startReminders(interval, userTask, intervalText);

				Toast.makeText(ConfigureReminderActivity.this,
						"Alarm configured, notifcation repeat " + intervalText, Toast.LENGTH_LONG).show(); ;

			}
		});


	}

	private void startReminders(int interval, String userTask, String intervalString) {

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		Intent intent = new Intent(this, ReminderReceiver.class);
		intent.putExtra(EXTRA_INTERVAL, interval);
		intent.putExtra(EXTRA_TASK, userTask);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

		long startTime = SystemClock.elapsedRealtime() + interval;
		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, startTime, interval, pendingIntent);


	}
}
