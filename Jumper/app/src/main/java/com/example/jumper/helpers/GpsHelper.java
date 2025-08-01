package com.example.jumper.helpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

/**
 * Created by fyoshida on 2017/02/19.
 */

public class GpsHelper implements LocationListener {
    private Context context;
    public int status;
    public double latitude;
    public double longitude;
    public double altitude;
    public float accuracy;
    public float time;
    public float speed;
    public float bearing;

    LocationManager locationManager;

    public GpsHelper(LocationManager locationManager, Context context) {
        this.locationManager = locationManager;
        this.context = context;
    }

    public void start() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    public void stop(){
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
        accuracy=location.getAccuracy();
        altitude=location.getAltitude();
        time=location.getTime();
        speed=location.getSpeed();
        bearing=location.getBearing();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        this.status=status;
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
