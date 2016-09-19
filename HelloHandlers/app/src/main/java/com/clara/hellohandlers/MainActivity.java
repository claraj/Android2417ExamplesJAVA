package com.clara.hellohandlers;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	TextView tv;
	private final String CLOCK_TICK_KEY = "tick";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		tv = (TextView) findViewById(R.id.hello_thread);

		/*
		*
		* "Thread handlers are implemented in the main thread of an application
		* and are primarily used to make updates to the user interface in response
		* to messages sent by another thread running within the application’s
		* process."
		*
		* clara note - you can't update the UI from another Thread. You have to send a message
		* to a handler to do it. Handler Messages contain a data Bundle - data in key-value pairs
		*
		* "Handlers are subclassed from the Android Handler class and can be
		* used either by specifying a Runnable to be executed when required by the
		* thread, or by overriding the handleMessage() callback method within the
		* Handler subclass which will be called when messages are sent to the handler
		* by a thread. "
		* http://www.techotopia.com/index.php/A_Basic_Overview_of_Android_Threads_and_Thread_handlers
		*
		* Also,
		* https://developer.android.com/training/multiple-threads/communicate-ui.html
		* Has bunch of examples for threading in Android.
		*/

		final Handler handler = new Handler() {    //Android docs advise making this static so there's only one of these Handlers ever.
			@Override
			public void handleMessage(Message msg) {
				int tick = msg.getData().getInt(CLOCK_TICK_KEY);
				tv.setText("Tick " + tick);					// Handler is allowed to modify UI
			}
		};


		//The thing that does the work
		Runnable runnable = new Runnable() {
			@Override

			public void run() {

				synchronized (this) {  //This code can only be accessed by this object at one time.

					int tick = 0;

					//Count to ten. send a message to handler
					for (int x = 0; x < 10; x++) {
						try {
							wait(1000);     //This might throw InterruptedException
							tick++;
							Message m = new Message();
							Bundle b = new Bundle();
							b.putInt(CLOCK_TICK_KEY, tick);
							m.setData(b);
							handler.sendMessage(m);    //Send a Message with data to the application's Handler.

						} catch (InterruptedException ie) {
							//whatever
							android.util.Log.d("Runnable", "The thread was interrupted.");
							break;  //stop loop
						}
					}
				}
			}
		};


		//The thing that manages threading. Start a new thread to run the runnable
		final Thread thread = new Thread(runnable);
		thread.start();


		Button stopButton = (Button) findViewById(R.id.stop_button);
		stopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Stop thread by interrupting it.
				// wait() in Runnable.run throws InterruptedException
				// clean up (if needed) and stop thread's task.
				// http://stackoverflow.com/questions/10961714/how-to-properly-stop-the-thread-in-java
				thread.interrupt();
			}
		});

	}
}
