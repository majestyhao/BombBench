package edu.ucdavis.smsreceived;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Class: MainActivity
 * Description: Leaking when receive certain SMS
 * Authors: Hao Fu(haofu@ucdavis.edu)
 * Date: 7/1/2016 3:07 PM
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
