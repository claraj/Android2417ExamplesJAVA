package com.clara.mapintent;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
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

    private EditText mPlaceNameText;
    private Button mMapButton;

    private static final String TAG = "MAP_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlaceNameText = findViewById(R.id.place_name);
        mMapButton = findViewById(R.id.map_button);

        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String placeName = mPlaceNameText.getText().toString();
                if (placeName.isEmpty()) {
                    return;
                }

                Log.d(TAG, "About to geocode this place name: " + placeName);

                // Geocode location, launch map app to show location
                Geocoder geocoder = new Geocoder(MainActivity.this);

                try {
                    List<Address> addresses = geocoder.getFromLocationName(placeName, 1);

                    if (addresses.isEmpty()) {
                        Toast noResults = Toast.makeText(MainActivity.this, "No places found for this name", Toast.LENGTH_LONG);
                        noResults.show();
                        return;
                    }

                    Address address = addresses.get(0);

                    Log.d(TAG, "First place found: " + address);

                    String geoUriString = String.format("geo:%f,%f", address.getLatitude(), address.getLongitude());

                    Log.d(TAG, geoUriString);

                    Uri geoUri = Uri.parse(geoUriString);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);
                    startActivity(mapIntent);

                } catch (IOException ioe) {

                    Log.e(TAG, "Geocoder getFromLocationName error", ioe);

                    Toast noResults = Toast.makeText(MainActivity.this, "Can't identify this place", Toast.LENGTH_LONG);
                    noResults.show();
                }
            }
        });

    }
}



