package com.clara.beemap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.Manifest;

import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.clara.beemap.db.Bee;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.Date;
import java.util.List;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        Observer<List<Bee>>,
        GoogleMap.OnInfoWindowLongClickListener {

    private static final String TAG = "MAIN_ACTIVITY";

    private boolean mLocationPermission = false;
    private int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private Location mLastKnownLocation;

    private float mDefaultZoom = 11;

    private float lat = 44.97f, lng = -93.26f;      // minneapolis
    private LatLng mMinneapolis = new LatLng(lat, lng);
    private LatLngBounds mMinneapolisBounds = new LatLngBounds( new LatLng(lat-1, lng-1), new LatLng(lat+1, lng+1));

    private GoogleMap map;

    private BeeViewModel mBeeModel;
    private List<Bee> bees;

    private FloatingActionButton addFab ;

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

        getLocationPermission();

        mBeeModel = new BeeViewModel(getApplication());
        requestMarkersForMap();
       // addExample();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "Map ready");

        this.map = googleMap;

        getDeviceLocation();
        updateLocationUI();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                mMinneapolis, mDefaultZoom));

        // Set location bounds to minneapolis
        map.setLatLngBoundsForCameraTarget(mMinneapolisBounds);
        map.setMinZoomPreference(8);

        map.setOnInfoWindowLongClickListener(this);
         }


    @Override
    public void onChanged(List<Bee> bees) {
        Log.d(TAG, "Bees: " + bees );
        this.bees = bees;
        drawMarkers();
    }


    private void addBee() {

        if (mLastKnownLocation == null) {
            Log.d(TAG, "No location known");
            return;
        }

        Bee bee = new Bee(
                mLastKnownLocation.getLatitude(),
                mLastKnownLocation.getLongitude(),
                new Date()    //now
                );

        Log.d(TAG, "Added bee: " + bee);

        mBeeModel.insert(bee);
        Toast.makeText(this, "Added bee!", Toast.LENGTH_SHORT).show();

       // addMarkerForBee(bee);
    }


    private void drawMarkers() {
        if (map == null) { Log.d(TAG, "Map not ready");  return; }
        if (bees == null )  { Log.d(TAG, "Data not ready"); return; }

        Log.d(TAG, "Have data and drawing "+ bees.size() +" markers");

        map.clear();

        for (Bee bee : bees ) {
            addMarkerForBee(bee);
        }
    }

    private void addExample() {
        mBeeModel.insert(new Bee(45, -93, new Date()));
        mBeeModel.insert(new Bee(45.1, -93.3, new Date()));
        mBeeModel.insert(new Bee(45.2, -93.2, new Date()));
        mBeeModel.insert(new Bee(45.3, -93.1, new Date()));
    }

    private void requestMarkersForMap() {
        Log.d(TAG, "Request markers " );

      // = mBeeModel.getRecentBees(location.latitude, location.longitude, 5);
        LiveData<List<Bee>> liveBees = mBeeModel.getRecentBees(100);  // number of results to return
        liveBees.observe(this, this);
    }

    private void addMarkerForBee(Bee bee) {

        Log.d(TAG, bee.toString());
        LatLng position = new LatLng(bee.getLatitude(), bee.getLongitude());
        Date date = bee.getDate();

        String dateString = DateFormat.getDateFormat(this).format(date) + " " +  DateFormat.getTimeFormat(this).format(date) + " bee id " + bee.getId();

        Marker marker = map.addMarker(new MarkerOptions()
                .position(position)
                .anchor(0.5f, 0.5f)
                .title("Bee sighting on " + dateString)
                .snippet("Long press to delete")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bee_map_icon_small)));

        marker.setTag(bee.getId());
        Log.d(TAG, "Marker added " + marker.getTag());

    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Have location permission");
            mLocationPermission = true;

            addFab.setEnabled(true);

            getDeviceLocation();
            updateLocationUI();
           // requestMarkersForMap();

        } else {
            Log.d(TAG, "About to request permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION },
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
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

                getDeviceLocation();
                updateLocationUI();

            } else {
                Toast.makeText(this, "Location permission must be granted for Bee Map to work. Please enable in settings.", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void updateLocationUI() {

        if (map == null) { return; }

        Log.d(TAG, "update location ui" );

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false); // no directions

        try {
            if (mLocationPermission) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
               // getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e(TAG, "Error attempting to show user location controls on map ", e);
        }
    }


    private void getDeviceLocation() {
        try {
            if (mLocationPermission) {
                FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(this);
                Task locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Got location ");
                            mLastKnownLocation = (Location) task.getResult();
                        }
                        else {
                            mLastKnownLocation = null;
                        }
                        //moveMap();   // move to location or default
                        updateLocationUI();   // draws location stuff on map
                    }
                });
            }
        } catch (SecurityException se) {
            Log.e(TAG, "Error getting device location ", se);
        }
    }






    @Override
    public void onInfoWindowLongClick(Marker marker) {
        int tag = (int) marker.getTag();
        Log.d(TAG, "Marker long press with tag " + tag);

        mBeeModel.delete((int)marker.getTag());

       // marker.remove();
    }


}
