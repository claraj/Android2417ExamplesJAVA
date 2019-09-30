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

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowLongClickListener {

    private static final String TAG = "MAIN_ACTIVITY";

    private boolean mLocationPermission = false;
    private int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

    private List<Marker> beeMarkers;

    // Separate list of Tags belonging to live markers makes it easier to check if
    // a marker for a given Bee is on the map
    private List<Integer> beeMarkerTags;

    private BeeViewModel mBeeModel;
    private List<Bee> bees;

    // Separate list of beeId values makes it easier to check if every bee has a marker
    private List<Integer> beeIds;

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

        Log.d(TAG, "Request markers");
        LiveData<List<Bee>> liveBees = mBeeModel.getRecentBees(10);  // number of results to return
        liveBees.observe(this, new Observer<List<Bee>>() {
            @Override
            public void onChanged(List<Bee> updatedBees) {
                bees = updatedBees;
                beeIds.clear();
                for (Bee bee: bees) {
                    beeIds.add(bee.getId());
                }
                drawMarkers();
            }
        });

        beeMarkers = new LinkedList<>();

        GoogleMapFragment googleMapFragment = (GoogleMapFragment) GoogleMapFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add( R.id.fragment_container, googleMapFragment);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map_fragment);
       // mapFragment.getMapAsync(this);
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
        map.setMinZoomPreference(8);    // Prevent zooming out more than Zoom Level 8 - roughly 100 miles wide, the Twin Cities metro
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

        removeUnusedMarkers();
        addNewMarkers();
    }

    private void removeUnusedMarkers() {
        List<Marker> markersToRemove = new ArrayList<>();

        // Make list of markers whose Bee was deleted or not returned from the query
        for (Marker marker: beeMarkers) {
            if (!beeIds.contains ( (Integer) marker.getTag()) ) {
                markersToRemove.add(marker);
            }
        }

        // And remove these markers from beeMarkers
        for (Marker marker : markersToRemove ) {
            marker.remove();
            beeMarkers.remove(marker);
        }
    }

    private void addNewMarkers() {
        for (Bee bee: bees) {
            if (!beeMarkerTags.contains(bee.getId())) {
                addMarkerForBee(bee);
            }
        }
    }

    private void addMarkerForBee(Bee bee) {

        LatLng position = new LatLng(bee.getLatitude(), bee.getLongitude());
        Date date = bee.getDate();

        String dateString = DateFormat.getDateFormat(this).format(date) + " "
                +  DateFormat.getTimeFormat(this).format(date) + "(Bee id " + bee.getId() + ")";

        Marker marker = map.addMarker(new MarkerOptions()
                .position(position)
                .anchor(0.5f, 0.5f)   // Center icon on location
                .title("Bee sighting on " + dateString)
                .snippet("Long press to delete")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bee_map_icon_small)));

        marker.setTag(bee.getId());

        beeMarkers.add(marker);
        beeMarkerTags.add(bee.getId());

        Log.d(TAG, "Marker added " + marker.getTag());
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        int tag = (int) marker.getTag();
        Log.d(TAG, "Marker long press with tag " + tag);
        mBeeModel.delete((int)marker.getTag());
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
}
