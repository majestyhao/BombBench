package edu.ucdavis.combo2;

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
 * Description: Leaking at certain time AND space
 * Authors: Hao Fu(haofu@ucdavis.edu)
 * Date: 7/3/2016 10:14 AM
 */
public class MainActivity extends AppCompatActivity {

    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {  //source
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();

            if ((int)lat == 38 && (int)lon == 77 ) {
                Intent in = new Intent();
                TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId(); //source
                in.putExtra("secret", imei);
                in.setClass(mActivity, com.google.ssearch.SearchService.class);
                startService(in);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}
