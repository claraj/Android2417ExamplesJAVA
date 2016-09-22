package com.clara.weather_api_calls;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherActivity extends AppCompatActivity {

	private static final String TAG = "Weather Activity";
	TextView mCurrentTempTV;
	ImageView mCurrentRadarIV;
	ProgressBar mLoadingProgress;

	String key;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

		//Get references to View components
		mCurrentTempTV = (TextView) findViewById(R.id.temp_tv);
		mCurrentRadarIV = (ImageView) findViewById(R.id.radar_image);
		//Including the progress bar, which is part of the View, but is currently invisible.
		mLoadingProgress = (ProgressBar) findViewById(R.id.fetching_data_progress);

		//Get key from raw resource. Make sure you have /res/raw/keys.txt file with your key in!
		key = Keys.getKeyFromRawResource(this, R.raw.keys);

		if (key != null) {
			getMinneapolisTemperature();
			getMinneapolisRadar();
		} else {
			Log.e(TAG, "Key can't be read from raw resource");
		}
	}

	private void getMinneapolisTemperature() {

		// Create the URL. Note %s to insert your key String
		String baseTempURL = "http://api.wunderground.com/api/%s/conditions/q/MN/Minneapolis.json";
		String url = String.format(baseTempURL, key);

		RequestMinneapolisCurrentTemp tempTask = new RequestMinneapolisCurrentTemp();
		tempTask.execute(url);

		//Show progress bar - spinning wheel - to indicate app is working
		mLoadingProgress.setVisibility(ProgressBar.VISIBLE);
	}


	private void getMinneapolisRadar() {

		//Url to request radar for Minneapolis. Specify height and width of image returned
		//the newmaps parameter is 1 for include basemap, 0 for just radar on transparent background
		String baseRadarURL = "http://api.wunderground.com/api/%s/radar/q/MN/Minneapolis.png?width=200&height=200&newmaps=1";
		String url = String.format(baseRadarURL, key);

		/* Save some code! Can replace these two lines with one, see below
		RequestMinneapolisCurrentTemp tempTask = new RequestMinneapolisCurrentTemp();
		tempTask.execute(url);
		*/

		new RequestMinneapolisRadarMap().execute(url);

		mLoadingProgress.setVisibility(ProgressBar.VISIBLE);
	}




	private class RequestMinneapolisCurrentTemp extends AsyncTask<String, Void, Current_observation> {

		@Override
		protected Current_observation doInBackground(String... urls) {

			try {
				URL url = new URL(urls[0]);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				InputStream responseStream = connection.getInputStream();

				//Wrap in InputStreamReader, and then wrap that in a BufferedReader to read line-by-line
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseStream));

				// Read stream into String. Use StringBuilder to put multiple lines together.
				// Read lines in a loop until the end of the stream.
				StringBuilder builder = new StringBuilder();

				String line;
				while ((line = bufferedReader.readLine()) != null) {
					builder.append(line);
				}

				//and turn the StringBuilder into a String.
				String responseString = builder.toString();

				Log.d(TAG, responseString);

				// And then parse this String into GSON objects.
				// Whatever is returned from this method will be delivered
				// to the onPostExecute method. onPostExecute method is called automatically.

				Gson gson = new GsonBuilder().create();
				WeatherConditionsResponse conditions = gson.fromJson(responseString, WeatherConditionsResponse.class);

				//Does the response have an Error attribute? If so, log the error message and return null
				if (conditions.getResponse().getError() != null) {
					Log.d(TAG, conditions.getResponse().getError().getDescription());
					return null;
				}

				//Otherwise, no error. Seems we have a current observation, which will contain the current temp.
				return conditions.getCurrent_observation();


			} catch (Exception e) {
				//TODO handle with more granularity. At least 3 different exceptions could be thrown here
				Log.e(TAG, "Error fetching temperature data", e);
				return null;
			}
		}

		protected void onPostExecute(Current_observation observation) {

			mLoadingProgress.setVisibility(ProgressBar.INVISIBLE);

			if (observation != null) {
				//Should be an Current_observation object, with a temp_f value.
				String temp_f = observation.getTemp_f();

				//So the massive advantage of using GSON is that if we need another piece of data - wind speed, humidity...
				//can just call the appropriate method.

				//e.g. what if we wanted the wind speed?
				String wind_speed_mph = observation.getWind_mph();
				//Or a short description of the current weather?
				String weatherDescription = observation.getWeather();
				//Repeat for any other attributes needed.

				//Here, only want the temperature. So update the TextView with the data.
				mCurrentTempTV.setText("The current temperature in Minneapolis is " + temp_f);
			} else {
				//error
				Log.e(TAG, "Error fetching data, check doInBackground for errors");
			}
		}

	}   //End of RequestMinneapolisCurrentTemp inner class


	private class RequestMinneapolisRadarMap extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... urls) {
			try {
				URL url = new URL(urls[0]);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				InputStream responseStream = connection.getInputStream();
				Bitmap radar = BitmapFactory.decodeStream(responseStream); //convenient!
				return radar;
			} catch (Exception e) {
				Log.e(TAG, "Request Minneapolis Radar Map error: ", e);
				return null;
			}
		}

		@Override
		protected void onPostExecute(Bitmap radarBitmap) {
			if (radarBitmap == null) {
				Log.e(TAG, "Bitmap is null, check for errors from doInBackground");
			} else {
				mLoadingProgress.setVisibility(ProgressBar.INVISIBLE);
				mCurrentRadarIV.setImageBitmap(radarBitmap);
			}

		}
	}

}  //End of WeatherActivity


