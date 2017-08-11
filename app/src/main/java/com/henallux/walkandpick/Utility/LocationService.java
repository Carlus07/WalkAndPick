package com.henallux.walkandpick.Utility;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.henallux.walkandpick.Activity.MainActivity;
import com.henallux.walkandpick.Application;

public class LocationService extends IntentService implements LocationListener {

    private boolean running = true;
    private LocationManager locationManager;
    private Location location;

    public LocationService() {
        super("LocationService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        String coordinate = intent.getStringExtra("Place");
        String[] coordinates = coordinate.split(",");

        //Obtention de la référence du service
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        //Si le GPS est disponible, on s'y abonne
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            abonnementGPS();
        }
        try
        {
            while (running)
            {
                Thread.sleep(10000);
                if (location != null)
                {
                    double meter = DistanceBetweenCoordinates(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]), location.getLatitude(), location.getLongitude());
                    if (meter <= 15)
                    {
                        running = false;
                        //Lancer l'activiter pour faire une description de l'endroid
                    }
                    Log.i("Test", "Distance : "+meter);
                }
                Log.i("Test", "Pas de signal gps");
            }
        }
        catch(Throwable t)
        {
            stopSelf();
        }
    }

    /**
     * Méthode permettant de s'abonner à la localisation par GPS.
     */
    public void abonnementGPS() {
        //On s'abonne
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("Test", "Pas d'accès au gps");
        }
        else locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }
    /**
     * Méthode permettant de se désabonner de la localisation par GPS.
     */
    public void desabonnementGPS() {
        //Si le GPS est disponible, on s'y abonne
        locationManager.removeUpdates(this);
        stopSelf();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        //On affiche dans un Toat la nouvelle Localisation
        final StringBuilder msg = new StringBuilder("lat : ");
        msg.append(location.getLatitude());
        msg.append( "; lng : ");
        msg.append(location.getLongitude());

        Log.i("Test", msg.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        //Si le GPS est activé on s'abonne
        if("gps".equals(provider)) {
            abonnementGPS();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Si le GPS est désactivé on se désabonne
        if("gps".equals(provider)) {
            desabonnementGPS();
        }
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
