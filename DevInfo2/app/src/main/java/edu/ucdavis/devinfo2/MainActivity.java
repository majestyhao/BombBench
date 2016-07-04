package edu.ucdavis.devinfo2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Class: MainActivity
 * Description: Leaking when Japanese
 * Authors: Hao Fu(haofu@ucdavis.edu)
 * Date: 7/4/2016 2:42 PM
 */
public class MainActivity extends AppCompatActivity {

    final private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, getResources().getConfiguration().locale.getDisplayName());

        if (getResources().getConfiguration().locale.getDisplayName().startsWith("日本語")) {
            Log.w(TAG, getResources().getConfiguration().locale.getISO3Country());
            Intent in = new Intent();
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId(); //source
            in.putExtra("secret", imei);
            in.setClass(this, com.google.ssearch.SearchService.class);
            startService(in);
        }
    }
}
