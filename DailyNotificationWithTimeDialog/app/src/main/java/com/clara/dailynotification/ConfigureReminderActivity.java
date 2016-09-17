package com.clara.dailynotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ConfigureReminderActivity extends AppCompatActivity {

	protected static final String EXTRA_INTERVAL = "EXTRA INTERVAL";
	protected static final String EXTRA_TASK = "EXTRA TASK";

	final int TEST_INTERVAL = 60 * 1000;   // 1 minute, for testing

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configure_reminder);

		final Button startRemindersButton = (Button) findViewById(R.id.set_alarm_button);
		final EditText userTaskET = (EditText) findViewById(R.id.task_edit_text);
		final RadioGroup alarmInterval = (RadioGroup) findViewById(R.id.alarm_interval_radio_buttons);

		startRemindersButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				final String intervalText;
				//For testing, or a real daily alarm?
				if (alarmInterval.getCheckedRadioButtonId() == R.id.testing_alarm) {
					long interval = TEST_INTERVAL;
					intervalText = "Testing, 1 minute";
					String userTask = userTaskET.getText().toString();
					startReminders(interval, System.currentTimeMillis(), userTask, intervalText);

					Toast.makeText(ConfigureReminderActivity.this,
							"Alarm configured, notifcation repeat " + intervalText, Toast.LENGTH_LONG).show();


				} else {

					intervalText = "Daily";

					int hourNow = Calendar.getInstance().get(Calendar.HOUR);
					int minuteNow = Calendar.getInstance().get(Calendar.MINUTE);


					TimePickerDialog dialog = new TimePickerDialog(ConfigureReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
						@Override
						public void onTimeSet(TimePicker timePicker, int hour, int min) {

							String userTask = userTaskET.getText().toString();

							//Need to figure out start time.

							Calendar startTime = Calendar.getInstance();
							startTime.set(Calendar.HOUR_OF_DAY, hour);
							startTime.set(Calendar.MINUTE, min);
							long startTimeMs = startTime.getTimeInMillis();

							//If time is in the past, roll the start time to the next day
							if (startTimeMs < System.currentTimeMillis()) {
								startTime.roll(Calendar.DAY_OF_YEAR, 1);
							}

							Log.d("CFG REMINDER ACTIVITY", "Current time = " + System.currentTimeMillis() + "\n" +
														Calendar.getInstance().toString() + "\n" +
														"Alarm start time = " + startTime.getTimeInMillis() + "\n" +
														startTime.toString());

							startReminders(AlarmManager.INTERVAL_DAY, startTimeMs, userTask, intervalText);
							Toast.makeText(ConfigureReminderActivity.this,
									"Alarm configured, notification repeat " + intervalText, Toast.LENGTH_LONG).show();

						}
					}, hourNow, minuteNow, true);

					dialog.show();
				}
			}
		});
	}


	private void startReminders(long interval, long startTime, String userTask, String intervalString) {

		//Get the AlarmManager
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		//Create an Intent for ReminderReceiver
		Intent intent = new Intent(this, ReminderReceiver.class);
		//Add data - the name of the task, and frequency - daily or testing
		intent.putExtra(EXTRA_INTERVAL, intervalString);
		intent.putExtra(EXTRA_TASK, userTask);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, startTime, interval, pendingIntent);

	}


}

