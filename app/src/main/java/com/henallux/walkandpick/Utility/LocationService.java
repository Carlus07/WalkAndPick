package com.henallux.walkandpick.Utility;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

public class LocationService extends Service
{
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public LocationManager locationManager;
    public MyLocationListener listener;
    public Location previousBestLocation = null;

    Intent intent;
    private String[] coordinate;

    @Override
    public void onCreate()
    {
        super.onCreate();
       // intent = getIntent();
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        String coordinates = intent.getStringExtra("Place");
        coordinate = coordinates.split(" ");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("Test", "Pas d'accès au gps");
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
        }
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        locationManager.removeUpdates(listener);
    }

    public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }

    public class MyLocationListener implements LocationListener
    {
        public void onLocationChanged(final Location location)
        {
            if(isBetterLocation(location, previousBestLocation))
            {
                Log.i("Test", "Latidue : "+location.getLatitude()+ " - Longitude : "+location.getLongitude());

                double meter = DistanceBetweenCoordinates(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1]), location.getLatitude(), location.getLongitude());
                if (meter <= 15)
                {
                    //Emettre la notification pour pouvoir revenir sur l'application
                    onDestroy();
                }
            }
        }

        public void onProviderDisabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
        }

        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }
        private double deg2rad(double x){
            return Math.PI*x/180;
        }

        private double DistanceBetweenCoordinates(double latitudeA, double longitudeA, double latitudeB, double longitudeB) {
            int earth_radius = 6378137;   // Terre = sphère de 6378km de rayon
            double radLatA = deg2rad(latitudeA);    // CONVERSION
            double radLongA  = deg2rad(longitudeA);
            double radLatB  = deg2rad(latitudeB);
            double radLongB  = deg2rad(longitudeB);
            double diffLong = (radLongB - radLongA) / 2;
            double diffLat = (radLatB - radLatA) / 2;
            double calcul = (Math.sin(diffLat) * Math.sin(diffLat)) + Math.cos(radLatA) * Math.cos(radLatB) * (Math.sin(diffLong) * Math.sin(diffLong
            ));
            double result = 2 * Math.atan2(Math.sqrt(calcul), Math.sqrt(1 - calcul));
            return (earth_radius * result);
        }
    }
}