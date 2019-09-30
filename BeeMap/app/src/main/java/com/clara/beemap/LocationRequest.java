package com.clara.beemap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationRequest {

    Context context;

    LocationRequest(Context context) {
        this.context = context;
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Have location permission");
            mLocationPermission = true;

            addFab.setEnabled(true);   // Only add bee if location will be available

            getDeviceLocation(null);
          //  updateMapLocationUI();

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
                            if (callback != null && mLastKnownLocation != null) {
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
