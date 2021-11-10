package com.boughaba.abnormaldetection.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationRequest;

public class LocationUtils {

    private static final int LOCATION_PERMISSION_CODE = 101;
    private static final int LOCATION_UPDATE_INTERVAL = 1000;
    private static final int LOCATION_UPDATE_FASTEST_INTERVAL = 800;

    public static LocationRequest getLocationRequest() {
        return LocationRequest.create()
                .setInterval(LOCATION_UPDATE_INTERVAL)
                .setFastestInterval(LOCATION_UPDATE_FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public static void requestForLocationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity
                        , new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}
                        , LOCATION_PERMISSION_CODE);
            } else {
                ActivityCompat.requestPermissions(activity
                        , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                                , Manifest.permission.ACCESS_FINE_LOCATION}
                        , LOCATION_PERMISSION_CODE);
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ActivityCompat.requestPermissions(activity
                    , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                            , Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.ACCESS_BACKGROUND_LOCATION}
                    , LOCATION_PERMISSION_CODE);
        } else {
            ActivityCompat.requestPermissions(activity
                    , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                            , Manifest.permission.ACCESS_FINE_LOCATION}
                    , LOCATION_PERMISSION_CODE);
        }
    }

    public static boolean checkLocationAccuracy(Activity activity) {
        try {
            int locationMode = Settings.Secure.getInt(activity.getContentResolver(), Settings.Secure.LOCATION_MODE);
            switch (locationMode) {
                case Settings.Secure.LOCATION_MODE_OFF:
                case Settings.Secure.LOCATION_MODE_BATTERY_SAVING:
                    return false;
                case Settings.Secure.LOCATION_MODE_SENSORS_ONLY:
                case Settings.Secure.LOCATION_MODE_HIGH_ACCURACY:
                    return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkLocationPermissions(Context context) {
        int resultA = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION));
        int resultB = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION));
        int resultC = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (resultA == PackageManager.PERMISSION_GRANTED
                    && resultB == PackageManager.PERMISSION_GRANTED
                    && resultC == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        } else {
            if (resultA == PackageManager.PERMISSION_GRANTED
                    && resultB == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }
}
