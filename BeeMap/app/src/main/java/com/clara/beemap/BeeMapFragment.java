package com.clara.beemap;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeeMapFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowLongClickListener {

    private static final String TAG = "MAIN_ACTIVITY";

    private List<Marker> beeMarkers;

    // Separate list of Tags belonging to live markers makes it easier to check if
    // a marker for a given Bee is on the map
    private List<Integer> beeMarkerTags;

    private boolean mLocationPermission = false;

    private Location mLastKnownLocation;

    private float mDefaultZoom = 11;

    private float lat = 44.97f, lng = -93.26f;      // minneapolis
    private LatLng mMinneapolis = new LatLng(lat, lng);
    private LatLngBounds mMinneapolisBounds = new LatLngBounds( new LatLng(lat-1, lng-1), new LatLng(lat+1, lng+1));

    private GoogleMap map;

    public BeeMapFragment() {
        super();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_google_map, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public static BeeMapFragment newInstance() {
        return new BeeMapFragment();
    }

    private BeeViewModel mBeeModel;
    private List<Bee> bees;

    // Separate list of beeId values makes it easier to check if every bee has a marker
    private List<Integer> beeIds;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMapAsync(this);

        beeIds = new ArrayList<>();
        beeMarkers = new ArrayList<>();

         mBeeModel = ViewModelProviders.of(this).get(BeeViewModel.class);

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

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(TAG, "Map is ready");
        this.map = googleMap;

        updateMapLocationUI(false);

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false); // no directions

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                mMinneapolis, mDefaultZoom));

        // Set location bounds to Minneapolis
        map.setLatLngBoundsForCameraTarget(mMinneapolisBounds);
        map.setMinZoomPreference(8);    // Prevent zooming out more than Zoom Level 8 - roughly 100 miles wide, the Twin Cities metro
        map.setOnInfoWindowLongClickListener(this);
    }


    void setHaveLocation(boolean haveLocation) {
        updateMapLocationUI(haveLocation);
    }

    private void updateMapLocationUI(boolean haveLocation) {
        if (map == null) { return; }
        Log.d(TAG, "update location ui" );
        if (haveLocation) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            map.setMyLocationEnabled(false);
            map.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }




    @Override
    public void onInfoWindowLongClick(Marker marker) {
        int tag = (int) marker.getTag();
        Log.d(TAG, "Marker long press with tag " + tag);
        mBeeModel.delete((int)marker.getTag());
        //beeListener.onBeeDelete( (int) marker.getTag());
    }

    public void addMarkerForBee(Bee bee) {
        LatLng position = new LatLng(bee.getLatitude(), bee.getLongitude());
        Date date = bee.getDate();

        String dateString = DateFormat.getDateFormat(this.getContext()).format(date) + " "
                +  DateFormat.getTimeFormat(this.getContext()).format(date) + "(Bee id " + bee.getId() + ")";

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

    private void drawMarkers() {

        if (map == null) { Log.d(TAG, "Map not ready");  return; }
        if (bees == null )  { Log.d(TAG, "Data not ready"); return; }

        Log.d(TAG, "Have data and drawing "+ bees.size() +" markers");

        removeUnusedMarkers();
        addNewMarkers();
    }



    private void addNewMarkers() {
        for (Bee bee: bees) {
            if (!beeMarkerTags.contains(bee.getId())) {
                addMarkerForBee(bee);
            }
        }
    }

    //    private void addMarkerForBee(Bee bee) {
//
//        LatLng position = LocationService.getDeviceLocation();
//
//    }

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



}
