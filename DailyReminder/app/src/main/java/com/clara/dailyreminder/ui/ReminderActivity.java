package com.clara.dailyreminder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.clara.dailyreminder.R;

public class ReminderActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder);

		//getActionBar().setTitle("reminder");

		// Show the current task's text and repeat interval




		Button config = (Button) findViewById(R.id.configureButton);
		config.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent launchConfig = new Intent(ReminderActivity.this, ConfigurationActivity.class);
				startActivity(launchConfig);
			}
		});

	}
}
