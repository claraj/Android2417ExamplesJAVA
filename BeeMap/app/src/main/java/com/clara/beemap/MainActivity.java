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

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
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

    interface BeeLocationCallback {
        void haveLocation();
        void noLocation();
    }

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

        Log.d(TAG, "Request markers " );
        LiveData<List<Bee>> liveBees = mBeeModel.getRecentBees(100);  // number of results to return
        liveBees.observe(this, new Observer<List<Bee>>() {
            @Override
            public void onChanged(List<Bee> updatedBees) {
                bees = updatedBees;
                drawMarkers();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "Map ready");

        this.map = googleMap;

        updateMapLocationUI();

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false); // no directions

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                mMinneapolis, mDefaultZoom));

        // Set location bounds to Minneapolis
        map.setLatLngBoundsForCameraTarget(mMinneapolisBounds);
        map.setMinZoomPreference(8);

        map.setOnInfoWindowLongClickListener(this);
    }

    private void addBee() {

        getDeviceLocation(new BeeLocationCallback() {
            @Override
            public void haveLocation() {

                if ( mLastKnownLocation != null) {
                    Bee bee = new Bee(
                            mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude(),
                            new Date());   //now

                    Log.d(TAG, "Added bee: " + bee);

                    mBeeModel.insert(bee);
                    Toast.makeText(MainActivity.this, "Added bee!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error adding bee, no location available", Toast.LENGTH_SHORT).show();
                }
            }

            public void noLocation() {
                Toast.makeText(MainActivity.this, "Error adding bee, no location available", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void addMarkerForBee(Bee bee) {

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

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        int tag = (int) marker.getTag();
        Log.d(TAG, "Marker long press with tag " + tag);
        mBeeModel.delete((int)marker.getTag());
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Have location permission");
            mLocationPermission = true;

            addFab.setEnabled(true);

            getDeviceLocation(null);
            updateMapLocationUI();
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

                getDeviceLocation(null);
               // getLocationUpdates();
                updateMapLocationUI();

            } else {
                Toast.makeText(this, "Location permission must be granted for Bee Map to work. Please enable in settings.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateMapLocationUI() {

        if (map == null) { return; }

        Log.d(TAG, "update location ui" );


            if (mLocationPermission) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
            }
    }

    private void getDeviceLocation(final BeeLocationCallback callback) {
        try {
            if (mLocationPermission) {
                FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(this);
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Got location ");
                            mLastKnownLocation = (Location) task.getResult();
                            if (callback != null || mLastKnownLocation != null) {
                                callback.haveLocation();
                                updateMapLocationUI();   // draws location controls on map
                                return;
                            }
                        }

                        mLastKnownLocation = null;
                        if (callback != null) { callback.noLocation(); }
                        updateMapLocationUI();   // draws location controls on map
                    }
                });
            } else {
                mLastKnownLocation = null;
            }
        } catch (SecurityException se) {
            Log.e(TAG, "Error getting device location ", se);
            mLastKnownLocation = null;
        }
    }
}
