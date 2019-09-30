package com.clara.beemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.Manifest;

import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.clara.beemap.db.Bee;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MAIN_ACTIVITY";

    private BeeViewModel mBeeModel;
    private FloatingActionButton addFab ;

    BeeMapFragment googleMapFragment;

    boolean mLocationPermission = false;
    int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

    LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFab = findViewById(R.id.fab_add);
        addFab.setEnabled(false);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBee();   // Add bee at the user's location
            }
        });


        locationService = new LocationService(this);

        Log.d(TAG, "Creating map");

        googleMapFragment = BeeMapFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace( R.id.fragment_container, googleMapFragment);
        ft.commit();

        getLocationPermission();

        mBeeModel = new BeeViewModel(getApplication());

    }


    private void addBee() {

        locationService.getDeviceLocation(new LocationService.LocationCallback() {

            @Override
            public void onLocationResult(Location location) {

                Log.d(TAG, "Have location " + location);


                if ( location != null) {
                    Bee bee = new Bee(
                            location.getLatitude(),
                            location.getLongitude(),
                            new Date());   //now

                    Log.d(TAG, "Added bee: " + bee);

                    mBeeModel.insert(bee);
                    Toast.makeText(MainActivity.this, "Added bee!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Error adding bee, no location available", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

        private void getLocationPermission() {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Have location permission");
                mLocationPermission = true;
                addFab.setEnabled(true);   // Only add bee if location will be available

                googleMapFragment.setHaveLocation(true);

            } else {
                Log.d(TAG, "About to request permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }



        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            mLocationPermission = false;
            if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Location permission granted" );

                    mLocationPermission = true;
                    addFab.setEnabled(true);

                    //getDeviceLocation(null);
                    // getLocationUpdates();
                    //updateMapLocationUI();

                    googleMapFragment.setHaveLocation(true);

                } else {
                    mLocationPermission = false;
                    Toast.makeText(this, "Location permission must be granted for Bee Map to work. Please enable in settings.", Toast.LENGTH_LONG).show();
                }
            }
        }

}
