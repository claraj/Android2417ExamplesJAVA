package com.clara.hellobroadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	TextView mResultTV;

	SlowTaskDoneBroadcastReceiver receiver;
	IntentFilter filter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button startSlowTask = (Button) findViewById(R.id.start_slow_task_activity);
		mResultTV = (TextView) findViewById(R.id.slow_task_result);

		//Create two things: a receiver, the custom class below
		receiver = new SlowTaskDoneBroadcastReceiver(this);
		//and an Intent filter, to filter out everything but the broadcasts we are interested in.
		//not the string is the same as the one used to create the Intent in the SlowTaskActivity.
		filter = new IntentFilter("com.clara.hellobroadcasts.SLOW_TASK_DONE");

		startSlowTask.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//Regular starting another Activity
				Intent startSlow = new Intent(MainActivity.this, SlowTaskActivity.class);
				startActivity(startSlow);

			}
		});

	}

	//Need to register, and unregister the receiver as the Activity stops and starts.
	// (Alternative - can also register receivers in the manifest. See docs)
	protected void onResume(){
		super.onResume();
		registerReceiver(receiver, filter);
	}

	protected void onPause(){
		super.onPause();
		unregisterReceiver(receiver);
	}

	// The BroadcastReceiver calls this when a broadcast is received. Only an Activity can update
	// its own UI, so the BroadcastReceiver can't update the TextView.
	private void notifyTaskDone() {
		mResultTV.setText("Slow task is done");
	}

	//The BroadcastReceiver. This is the object that will be notified when a Broadcast is sent.
	//It needs to store a reference to the MainActivity, so it can tell MainActivity that the broadcast was received, and
	// and then MainActivity can do whatever is needed, for example, update the UI.

	//Subclass BroadcastReceiver, and put code for whatever you want to happen in the onReceive method.
	class SlowTaskDoneBroadcastReceiver extends BroadcastReceiver {

		//Constructor to save a reference to the MainActivity. Need this to notify MainActivity when broadcast is received.
		MainActivity activity;
		SlowTaskDoneBroadcastReceiver(MainActivity activity ) {
			this.activity = activity;
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			activity.notifyTaskDone();
		}
	}


}
