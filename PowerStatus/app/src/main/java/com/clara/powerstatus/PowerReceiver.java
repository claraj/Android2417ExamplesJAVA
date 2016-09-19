package com.clara.powerstatus;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {

		if (context instanceof MainActivity) {

			if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
				((MainActivity) context).notifyPowerConnected(true);
			}

			if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
				((MainActivity) context).notifyPowerConnected(false);
			}
		}

		else {
			//Ignore. No activity on screen.
			android.util.Log.d("POWER RECEIVER",
					"charging status change received in context " + context.toString());
		}

	}
}

