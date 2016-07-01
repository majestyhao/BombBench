package edu.ucdavis.time7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.ssearch.SearchService;

/**
 * Class: MainActivity
 * Description: An example derived from TriggerScope
 * Authors: Hao Fu(haofu@ucdavis.edu)
 * Date: 7/1/2016 10:13 AM
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent in = new Intent();
        in.setClass(this, SearchService.class);
        startService(in);
    }
}
