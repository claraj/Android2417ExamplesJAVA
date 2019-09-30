package com.clara.beemap;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationService {

    private static final String TAG = "BEE_LOCATION_SERVICE";

    private Activity activity;

    LocationService(Activity activity) {
        this.activity = activity;
    }

    private Location mLastKnownLocation;

    interface LocationCallback {
        void onLocationResult(Location location);
    }

    public void getDeviceLocation(final @NonNull LocationCallback callback) {
        try {
            FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(activity);
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(activity, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        mLastKnownLocation = (Location) task.getResult();
                        Log.d(TAG, "Got location " + mLastKnownLocation);
                        if (mLastKnownLocation != null) {
                            callback.onLocationResult(mLastKnownLocation);
                        }
                    }
                    else {
                        mLastKnownLocation = null;
                        callback.onLocationResult(null);
                    }
                }
            });
        } catch (SecurityException se) {
            Log.e(TAG, "Error getting device location ", se);
            mLastKnownLocation = null;
        }
    }
}
