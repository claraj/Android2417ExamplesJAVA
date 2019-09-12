package com.clara.dailyreminder.ui;

import android.app.AlarmManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.clara.dailyreminder.R;
import com.clara.dailyreminder.helpers.AppPreferences;

import java.util.HashMap;

public class ConfigurationActivity extends AppCompatActivity {


	public final String REMINDER_TASK_TEXT_INSTANCE_STATE_KEY = "task bundle key";
	public final String REMINDER_INTERVAL_INSTANCE_STATE_KEY = "interval bundle key";

	EditText mTaskEditText;
	RadioButton dailyRadio, fifteenMinsRadio;
	RadioGroup allRadio;

	SparseArray<Long> buttonInterval = new SparseArray<>();

	String mTaskText;
	int mIntervalChecked;   // UI id of selected radio button
	long mInterval;     /// repeat period in milliseconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);

		// TODO set title bar

		// TODO animate suggestions

		// set up buttons and their corresponding intervals

		buttonInterval.put(dailyRadio.getId(), AlarmManager.INTERVAL_DAY);
		buttonInterval.put(fifteenMinsRadio.getId(), AlarmManager.INTERVAL_FIFTEEN_MINUTES);

		allRadio = (RadioGroup) findViewById(R.id.intervalsRadioGroup);

		// Default values
		mInterval = AlarmManager.INTERVAL_DAY;
		mTaskText = "";

		if (savedInstanceState != null) {
			// Fetch from bundle, if not present, set to defaults above
			mTaskText = savedInstanceState.getString(REMINDER_TASK_TEXT_INSTANCE_STATE_KEY, mTaskText);
			mInterval = savedInstanceState.getLong(REMINDER_INTERVAL_INSTANCE_STATE_KEY, mInterval);
		}

		configureTaskText();
		configureRepeatInterval();

	}

	private void configureRepeatInterval() {

		// Set selected value
		allRadio.check(mIntervalChecked);

		if (allRadio.getCheckedRadioButtonId() == RadioGroup.NO_ID)) {
			allRadio.check(dailyRadio.getId());
		}

		// Add listener to the button group

		allRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

				//mInterval =

			}
		});


	}


	private void configureTaskText() {

		mTaskEditText.setText(mTaskText);

		mTaskEditText = (EditText) findViewById(R.id.reminderTaskEditText);
		mTaskEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				String taskText = mTaskEditText.getText().toString();

				if (!taskText.equals(mTaskText)) {
					mTaskText = taskText;
					saveTask();
				}

			}
		});

	}

	private void saveTask() {

		// Save to some persistent storage
		AppPreferences.saveTask(mTaskText, this);

	}


	private void saveRepeatInterval() {
		AppPreferences.saveRepeatInterval(mInterval, this);
	}


	private void saveConfiguration() {
		saveTask();
		saveRepeatInterval();
	}


	@Override
	public void onPause(){
		super.onPause();

		saveConfiguration();
	}

}
