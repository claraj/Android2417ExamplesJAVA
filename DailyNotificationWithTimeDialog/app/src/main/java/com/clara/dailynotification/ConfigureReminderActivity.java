package com.clara.dailynotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

	EditText mUserTaskET;
	RadioGroup mIntervalRadioGroup;

	final static String TAG = "CFG REMINDER ACTIVITY";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configure_reminder);

		Button startRemindersButton = (Button) findViewById(R.id.set_alarm_button);
		final Button cancelRemindersButton = (Button) findViewById(R.id.cancel_alarm_button);
		mUserTaskET = (EditText) findViewById(R.id.task_edit_text);
		mIntervalRadioGroup = (RadioGroup) findViewById(R.id.alarm_interval_radio_buttons);

		startRemindersButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//Make sure task is entered, and a radio button is selec

				if (mUserTaskET.getText().toString().length() == 0) {
					Toast.makeText(ConfigureReminderActivity.this, "Enter a task", Toast.LENGTH_LONG).show();
					return;
				}

				if (mIntervalRadioGroup.getCheckedRadioButtonId() == -1) {
					Toast.makeText(ConfigureReminderActivity.this, "Select frequency", Toast.LENGTH_LONG).show();
					return;
				}

				configureReminders();
			}
		});

		cancelRemindersButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				cancelReminderAlarms();
			}
		});
	}

	private void cancelReminderAlarms() {

		//To cancel an Alarm, need to make a PendingIntent that's the same as the Alarm's PendingIntent, and then cancel that.

		Log.d(TAG, "Cancelling Alarm");

		Intent cancelIntent = new Intent(this, ReminderReceiver.class);
		PendingIntent cancel = PendingIntent.getBroadcast(this, 0, cancelIntent, 0);

		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.cancel(cancel);

		Toast.makeText(this, "Reminders cancelled", Toast.LENGTH_LONG).show();
	}


	private void configureReminders() {

		final String intervalText;
		//For testing, or a real daily alarm?
		if (mIntervalRadioGroup.getCheckedRadioButtonId() == R.id.testing_alarm) {
			long interval = TEST_INTERVAL;
			intervalText = "Testing, 1 minute";
			String userTask = mUserTaskET.getText().toString();
			startReminderAlarms(System.currentTimeMillis(), interval, userTask, intervalText);
			Toast.makeText(ConfigureReminderActivity.this,
					"Alarm configured, notification repeat " + intervalText, Toast.LENGTH_LONG).show();

		} else {

			intervalText = "Daily";

			int hourNow = Calendar.getInstance().get(Calendar.HOUR);
			int minuteNow = Calendar.getInstance().get(Calendar.MINUTE);

			TimePickerDialog dialog = new TimePickerDialog(ConfigureReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker timePicker, int hour, int min) {

					String userTask = mUserTaskET.getText().toString();

					//Need to figure out start time.

					Calendar startTime = Calendar.getInstance();
					startTime.set(Calendar.HOUR_OF_DAY, hour);
					startTime.set(Calendar.MINUTE, min);
					long startTimeMs = startTime.getTimeInMillis();

					//If time is in the past, (e.g. current time is 6pm and desired reminder time is 11am daily) roll the start time to the next day
					if (startTimeMs < System.currentTimeMillis()) {
						startTime.roll(Calendar.DAY_OF_YEAR, 1);
					}

					Log.d(TAG, "Current time = " + System.currentTimeMillis() + "\n" +
							Calendar.getInstance().toString() + "\n" +
							"Alarm start time = " + startTime.getTimeInMillis() + "\n" +
							startTime.toString());

					startReminderAlarms(startTimeMs, AlarmManager.INTERVAL_DAY, userTask, intervalText);
					Toast.makeText(ConfigureReminderActivity.this,
							"Alarm configured, notification repeat " + intervalText, Toast.LENGTH_LONG).show();

				}
			}, hourNow, minuteNow, true);

			dialog.show();
		}
	}


	private void startReminderAlarms(long startTime, long interval, String userTask, String intervalString) {

		Log.d(TAG, "Start reminder interval " + interval + " start " + startTime + " task " + userTask + " interval str " + intervalString);

		//Get the AlarmManager
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		//Create an Intent for ReminderReceiver
		Intent intent = new Intent(this, ReminderReceiver.class);
		//Add data - the name of the task, and frequency - daily or testing
		intent.putExtra(EXTRA_INTERVAL, intervalString);
		intent.putExtra(EXTRA_TASK, userTask);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

		//Since this alarm needs to occur daily, need to set with regard to actual RTC - wall clock time.
		alarmManager.setInexactRepeating(AlarmManager.RTC, startTime, interval, pendingIntent);

	}

}
