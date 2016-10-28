package com.clara.hellobroadcasts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SlowTaskActivity extends AppCompatActivity {

	private static final String TAG = "SLOW TASK ACTIVITY";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slow_task);

		Button doSlowTask = (Button) findViewById(R.id.do_slow_task);

		doSlowTask.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				//Create a new Thread. A Runnable task is one that can run on another thread,
				//and the UI and other code can run in parallel.  Whatever code is in the
				// run() method will run in the new Thread.

				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {

						//Pretend to do a time consuming task by sleeping for 4 seconds
						//In the real world, this could be decoding a Bitmap, time consuming database access, a call over the network...
						Log.d(TAG, "Slow task starting");
						try {
							Thread.sleep(4000);
						} catch (InterruptedException ie) {
							Log.e(TAG, "Slow task interrupted");
						}

						Log.d(TAG, "Slow task complete");

						//Create an intent tagged with the String argument. Use package name so should be unique on this device
						//The string will be used as a filter. Code (e.g.) an Activity that wishes to know when
						//the task is done, will create an Intent filter with the same string, and register with the Android system
						// to receive Broadcasts containing that STring.
						// When this Broadcast is sent, the Android system will notify all registered Broadcast Receivers.
						Intent intent = new Intent("com.clara.hellobroadcasts.SLOW_TASK_DONE");
						//And send a Broadcast containing this event.
						sendBroadcast(intent);
					}

				});

				//Start the thread to run in the background
				thread.start();

				//And end this activity, leaving the Thread running in the background
				SlowTaskActivity.this.finish();

			}
		});
	}
}
