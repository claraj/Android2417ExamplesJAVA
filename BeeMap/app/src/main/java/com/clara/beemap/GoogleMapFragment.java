package com.clara.beemap;


import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleMapFragment extends SupportMapFragment implements OnMapReadyCallback {


    private Location mLastKnownLocation;

    private float mDefaultZoom = 11;

    private float lat = 44.97f, lng = -93.26f;      // minneapolis
    private LatLng mMinneapolis = new LatLng(lat, lng);
    private LatLngBounds mMinneapolisBounds = new LatLngBounds( new LatLng(lat-1, lng-1), new LatLng(lat+1, lng+1));

    private GoogleMap map;


    public GoogleMapFragment() {
        // Required empty public constructor
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_google_map, container, false);
//
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
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
}
