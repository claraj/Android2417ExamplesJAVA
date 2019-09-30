package com.clara.beemap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.clara.beemap.db.Bee;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Date;

public class LocationService {

    private static final String TAG = "BEE_LOCATION_SERVICE";

    Activity context;

    LocationService(Activity context) {
        this.context = context;
    }

    Location mLastKnownLocation;
    boolean mLocationPermission;

    interface LocationCallback {
        void onLocationResult(Location location);
    }


  void getDeviceLocation(final @NonNull LocationCallback callback) {
    try {
        if (mLocationPermission) {
            FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(context);

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(context, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        mLastKnownLocation = (Location) task.getResult();
                        Log.d(TAG, "Got location " + mLastKnownLocation);
                        if (mLastKnownLocation != null) {
                            callback.onLocationResult(mLastKnownLocation);
                            //updateMapLocationUI();   // draws location controls on map
                            return;
                        }
                    }
                    else {
                        mLastKnownLocation = null;
                        callback.onLocationResult(null);
                        //updateMapLocationUI();   // draws location controls on map
                    }
                }
            });
        } else {
            mLastKnownLocation = null;
            callback.onLocationResult(null);
        }

    } catch (SecurityException se) {
        Log.e(TAG, "Error getting device location ", se);
        mLastKnownLocation = null;
    }
    }
}
