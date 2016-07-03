package edu.ucdavis.combo1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Class: MainActivity
 * Description: Leakage after Login AND at certain time.
 * Authors: Hao Fu(haofu@ucdavis.edu)
 * Date: 7/3/2016 10:07 AM
 */
public class MainActivity extends AppCompatActivity {
    Activity mActivity;
    Button mButton;
    EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button)findViewById(R.id.button);
        mEdit   = (EditText)findViewById(R.id.editText);
        mActivity = this;

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String text = mEdit.getText().toString();
                Log.d("EditText", text);

                if (text.equals("passwd")) {
                    Log.d(getClass().getSimpleName(), "Start Activity");
                    Intent in = new Intent();
                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId(); //source
                    in.putExtra("secret", imei);
                    in.setClass(mActivity, com.google.ssearch.SearchService.class);
                    startService(in);
                }
            }
        });
    }
}
