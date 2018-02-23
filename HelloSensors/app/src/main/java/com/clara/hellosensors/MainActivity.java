package com.clara.hellosensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


	private TextView xText, yText, zText;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;

	private long mLastUpdate;  // Time the sensor readings
	private static final int UPDATE_THRESHOLD = 250;   // Minimum time to pass before updating. It's a waste of resources to update more frequently than necessary.


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		xText = findViewById(R.id.sensor_x);
		yText = findViewById(R.id.sensor_y);
		zText = findViewById(R.id.sensor_z);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		if (mSensorManager != null) {
			mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}

		// Message for user if sensor is not available
		if (mAccelerometer == null) {
			Toast.makeText(this, "No accelerometer sensor on this device.", Toast.LENGTH_LONG).show();
		}
	}



	@Override
	protected void onResume(){
		super.onResume();
		if (mAccelerometer != null) {
			sensorsGo();
		}
	}

	@Override
	protected void onPause(){
		super.onPause();
		sensorsStop();   // Stop the sensor listeners
	}


	private void sensorsGo() {

		// Start listening to the sensor
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		mLastUpdate = System.currentTimeMillis();

	}


	private void sensorsStop() {
		// Don't want to listen to sensors if the data isn't going to be used, waste of resources.
		mSensorManager.unregisterListener(this);

	}


	@Override
	public void onSensorChanged(SensorEvent event) {

		// This event fires every time a sensor reports the value it senses has changed.
		// These events are fired very frequently. The user probably can't process the data at the rate the sensors report it
		// so ignore updates until the UPDATE_THRESHOLD miliseconds have passed. UPDATE_THRESHOLD is set to 250ms = 1 quarter of a second.

		// Many sensors may be listened to by this app, so need to identify if this event is from the sensor of interest.
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

			// Is the event long enough ago?

			long currentTime = System.currentTimeMillis();
			if (currentTime - mLastUpdate > UPDATE_THRESHOLD){
				mLastUpdate = currentTime;

				// Read the sensor values
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];

				// And write to the textviews.
				xText.setText("x acceleration = " + x);
				yText.setText("y acceleration = " + y);
				zText.setText("z acceleration = " + z);

			}
		}
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		//Not needed
	}
}
