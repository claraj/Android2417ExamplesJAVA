package com.clara.helloalarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {

	private static final String TAG = "RECEIVER";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "*** Beep beep! Alarm Received ***");
		//Can get data sent with the Intent here
		Log.d(TAG, "Delay was: " + intent.getIntExtra("DELAY", 0) );
	}
}
