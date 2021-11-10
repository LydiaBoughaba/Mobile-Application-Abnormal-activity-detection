package com.boughaba.abnormaldetection.service;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import com.boughaba.abnormaldetection.utils.LocationUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;
import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.List;

public class LocationService {

    

    private static final String TAG = "LocationService";

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Location lastKnowLocation;
    private Activity activity;

    public LocationService(Activity activity) {
        this.activity = activity;
        initLocationSettings();
    }

    // Init Location Settings
    private void initLocationSettings() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "initLocationSettings: ");
            return;
        }
        requestLocationUpdates();
    }

    // Get Location updates
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private void requestLocationUpdates() {
        mLocationRequest = LocationUtils.getLocationRequest();
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, activity.getMainLooper());
    }

    // Get Last Known location
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private void requestLastKnownLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Location CallBack
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                Location location = locationList.get(locationList.size() - 1);
                if (location != null) {
                    lastKnowLocation = location;
                }
            }
        }
    };

    public void cancelLocationRequest(){
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }


    // Call This to get the last known location
    public Location getLastKnowLocation() {
        return lastKnowLocation;
    }
}
