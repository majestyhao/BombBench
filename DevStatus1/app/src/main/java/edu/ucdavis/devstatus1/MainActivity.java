package edu.ucdavis.devstatus1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.ssearch.SearchService;

/**
 * Class: MainActivity
 * Description: Leak when onLowMemory
 * Authors: Hao Fu(haofu@ucdavis.edu)
 * Date: 7/1/2016 10:34 AM
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onLowMemory() {
        Intent in = new Intent();
        in.setClass(this, SearchService.class);
        startService(in);
    }
}
