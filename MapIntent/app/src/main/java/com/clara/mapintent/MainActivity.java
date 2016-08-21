package com.clara.mapintent;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	static final String TAG = "Map Intent";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final EditText mapSearchBox = (EditText) findViewById(R.id.map_search_box);
		Button mapSearchButton = (Button) findViewById(R.id.map_search_button);

		mapSearchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String mapSearchString = mapSearchBox.getText().toString();

				//If mapSearchString is blank, display warning Snackbar and don't launch Map.
				if (mapSearchString.length() == 0) {
					Snackbar.make(findViewById(android.R.id.content), "Enter a location", Snackbar.LENGTH_SHORT).show();
					return;
				}

				Geocoder geocoder = new Geocoder(MainActivity.this);  //Create a Geocoder, tell it what Activity it belongs to

				try {
					List<Address> addressList = geocoder.getFromLocationName(mapSearchString, 1);  // Second arg is numer of results requested. We only want the first
					//Verify that there is at least one result
					if (addressList.size() == 1) {

						// To launch a mapping application, need a URI in the form geo:lat,long
						// where lat and long are the latitude and longitude of the location
						// Various other ways to format the Uri, such as with a street address. see
						// https://developer.android.com/guide/components/intents-common.html#Maps
						Address firstAddress = addressList.get(0);

						Log.d(TAG, "First address is " + firstAddress);    // No System.out.println() ! Use Log for debugging statements

						//For the Map, lat and long are needed. A URI of the format geo:lat,long is required
						String geoUriString = String.format("geo:%f,%f", firstAddress.getLatitude(), firstAddress.getLongitude());
						Log.d(TAG, "Geo URI string is  " + geoUriString);

						Uri geoUri = Uri.parse(geoUriString);
						Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);

						//Compare and contrast Toast vs. Snackbar - Toast will still be visible while Maps launch; Snackbar is tied to MainActivity
						Toast.makeText(MainActivity.this, "Launching Map", Toast.LENGTH_LONG);
						startActivity(mapIntent);

					} else {
						//addressList is empty. Snackbar indicating no results found for location
						Snackbar.make(findViewById(android.R.id.content), "No results found for that location", Snackbar.LENGTH_LONG).show();
					}

				} catch (IOException ioe) {
					Log.e(TAG, "Error during geocoding", ioe);    //Log also useful for errors.
					Snackbar.make(findViewById(android.R.id.content), "Sorry, an error occurred", Snackbar.LENGTH_SHORT).show();
				}
			}
		});
	}
}
