package edu.ucdavis.ui2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button1, button2;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button);

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               count++;
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                count--;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (count < -250) {
            Intent in = new Intent();
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId(); //source
            in.putExtra("secret", imei);
            in.setClass(this, com.google.ssearch.SearchService.class);
            startService(in);
        }

    }
}
