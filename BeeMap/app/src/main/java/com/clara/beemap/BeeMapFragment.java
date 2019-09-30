package com.clara.beemap;


import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.format.DateFormat;
import android.util.Log;

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

    private static final String TAG = "BEE_MAP_FRAGMENT";

    private float mDefaultZoom = 11;
    private float minZoom = 8;
    private float lat = 44.97f, lng = -93.26f;      // minneapolis
    private LatLng mMinneapolis = new LatLng(lat, lng);
    private LatLngBounds mMinneapolisBounds = new LatLngBounds( new LatLng(lat-1, lng-1), new LatLng(lat+1, lng+1));

    private BeeViewModel mBeeModel;
    private List<Bee> bees;
    private List<Integer> beeIds;  //  List of beeId values makes it easier to check if every bee has a marker

    private List<Marker> beeMarkers;
    private List<Integer> beeMarkerTags;  // List of Tags belonging to live markers to make it easier to check if a marker for a given Bee is on the map

    private GoogleMap map;

    public static BeeMapFragment newInstance() {
        return new BeeMapFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMapAsync(this);

        beeIds = new ArrayList<>();
        beeMarkers = new ArrayList<>();
        beeMarkerTags = new ArrayList<>();

        mBeeModel = ViewModelProviders.of(this).get(BeeViewModel.class);

        Log.d(TAG, "Requesting bee data");

        LiveData<List<Bee>> liveBees = mBeeModel.getRecentBees(50);  // number of results to return

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

        hasLocationPermission(false);

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false); // no directions

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mMinneapolis, mDefaultZoom));

        // Set location bounds to Minneapolis
        map.setLatLngBoundsForCameraTarget(mMinneapolisBounds);
        map.setMinZoomPreference(minZoom);    // User can't zoom out more than the Twin Cities
        map.setOnInfoWindowLongClickListener(this);
    }


    public void hasLocationPermission(boolean shouldShowLocation) {
        map.setMyLocationEnabled(shouldShowLocation);
        map.getUiSettings().setMyLocationButtonEnabled(shouldShowLocation);
    }


    @Override
    public void onInfoWindowLongClick(Marker marker) {
        int tag = (int) marker.getTag();
        Log.d(TAG, "Marker long press with tag " + tag + " will delete");
        mBeeModel.delete((int)marker.getTag());
    }


    private Marker addMarkerForBee(Bee bee) {
        LatLng position = new LatLng(bee.getLatitude(), bee.getLongitude());
        Date date = bee.getDate();

        String titleString = DateFormat.getDateFormat(this.getContext()).format(date) + " "
                +  DateFormat.getTimeFormat(this.getContext()).format(date) + " (id " + bee.getId() + ")";

        Marker marker = map.addMarker(new MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bee_map_icon_small))
                .anchor(0.5f, 0.5f)   // Center icon on location
                .title("Bee sighting on " + titleString)
                .snippet("Long press to delete"));

        marker.setTag(bee.getId());
        Log.d(TAG, "Marker added " + marker.getTag());
        return marker;
    }


    private void drawMarkers() {
        if (map == null) { Log.d(TAG, "Can't draw markers, map not ready");  return; }
        if (bees == null )  { Log.d(TAG, "Can't draw markers, bee data not ready"); return; }

        Log.d(TAG, "Have data and drawing "+ bees.size() +" markers");

        removeUnusedMarkers();
        addNewMarkers();
    }


    private void addNewMarkers() {
        for (Bee bee: bees) {
            if (!beeMarkerTags.contains(bee.getId())) {
                Marker marker = addMarkerForBee(bee);
                beeMarkers.add(marker);
                beeMarkerTags.add( (int) marker.getTag());
            }
        }
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
        for (Marker marker : markersToRemove) {
            Log.d(TAG, "Removing" + marker + " " + marker.getTag());
            beeMarkers.remove(marker);
            beeMarkerTags.remove( (Integer) marker.getTag());
            marker.remove();
        }
    }
}
