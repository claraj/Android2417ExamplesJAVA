package com.clara.weather_api_calls;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

		//Get references to View components

		mCurrentTempTV = (TextView) findViewById(R.id.temp_tv);
		mCurrentRadarIV = (ImageView) findViewById(R.id.radar_image);

		//Make API calls to get data

		// Note %s to insert your key String
		String baseURL = "http://api.wunderground.com/api/%s/conditions/q/MN/Minneapolis.json";

		//Get key from raw resource
		String key = Keys.getKeyFromRawResource(this, R.raw.keys);

		//Make sure key isn't null
		if (key != null) {
			String url = String.format(baseURL, key);
			requestCurrentTemp(url);
		} else {
			Log.e(TAG, "Key can't be read from raw resource");
			mCurrentTempTV.setText("Key not found");
		}
	}


	private void requestCurrentTemp(String url) {
		//TODO

		RequestMinneapolisCurrentTemp requestTemp = new RequestMinneapolisCurrentTemp();
		requestTemp.execute(url);
	}


	private class RequestMinneapolisCurrentTemp extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... urls) {

			try {
				URL url = new URL(urls[0]);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				InputStream responseStream = connection.getInputStream();

				//Wrap in InputStreamReader, and then wrap that in a BufferedReader to read line-by-linr
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseStream));

				//Read stream into String. Use StringBuilder to put multiple lines together. This response
				// happens to be one line of JSON, but just in case it's formatted as multiple lines,
				// read in a loop until the end of the stream.
				StringBuilder builder = new StringBuilder();

				String line;
				while ((line = bufferedReader.readLine()) != null) {
					builder.append(line);
				}

				String responseString = builder.toString();

				Log.d(TAG, responseString);

				JSONObject json = new JSONObject(responseString);

				return json;

			} catch (Exception e) {
				//TODO handle with more granularity. At least 3 different exceptions could be thrown here
				Log.e(TAG, "Error fetching temperature data", e);
				return null;
			}
		}

		protected void onPostExecute(JSONObject json) {
			if (json != null) {
				try {
					if ( json.getJSONObject("response").has("error") ) {
						Log.e(TAG, "Error in response from WU " + json.getJSONObject("response")
								.getJSONObject("error")
								.getString("description"));
						return;
					}

					//Hopefully we have JSON and it's not reporting an error. Try and read desired data
					String temp_f = json.getJSONObject("current_observation").getString("temp_f");

					mCurrentTempTV.setText("The current temperature in Minneapolis is " + temp_f);

				} catch (JSONException je) {
					Log.e(TAG, "JSON parsing error", je);
				}
			}
		}

	}   //End of RequestMinneapolisCurrentTemp inner class


}
