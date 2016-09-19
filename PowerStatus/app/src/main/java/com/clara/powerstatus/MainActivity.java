package com.clara.powerstatus;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

	PowerReceiver mPowerReceiver;
	IntentFilter mConnectFilter;
	IntentFilter mDisconnectFilter;

	TextView mPowerStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPowerStatus = (TextView) findViewById(R.id.power_status);

		//Create a filter for the current charging status. This is a sticky broadcast
		IntentFilter currentStat = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		// Have to register a receiver to read it, but since it's a sticky broadcast,
		// it's always available, and can read from it any time. registerReceiver returns
		// an Intent, and we can read data from this Intent about the current battery status.
		Intent batteryStatus = registerReceiver(null, currentStat);

		//Read status. Call notifyPowerChanged to update TextView.
		int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

		//Is phone charging, or fully charged?
		notifyPowerConnected(status == BatteryManager.BATTERY_STATUS_CHARGING
				|| status == BatteryManager.BATTERY_STATUS_FULL);
	}


	//PowerReceiver will call this method
	public void notifyPowerConnected(boolean connected) {
		String powerText = connected ? "POWER CONNECTED" : "POWER DISCONNECTED";
		mPowerStatus.setText(powerText);
	}


	@Override
	protected void onResume(){
		super.onResume();

		//Create a BroadcastReceiver - PowerReceiver - to receive the desired broacasts.
		//One BroadcastReceiver can handle more than one broadcast.
		mPowerReceiver = new PowerReceiver();

		//Create IntentFilters - we are only interested in two broadcasts, not all of them
		mConnectFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
		mDisconnectFilter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);

		//And register receivers for the IntentFilters.
		registerReceiver(mPowerReceiver, mConnectFilter);
		registerReceiver(mPowerReceiver, mDisconnectFilter);
	}


	@Override
	protected void onPause() {
		super.onPause();

		//Unregister, because if Activity is paused, no point updating the display.
		//One call will unregister both for this BroadcastReceiver object.
		unregisterReceiver(mPowerReceiver);
	}

}
