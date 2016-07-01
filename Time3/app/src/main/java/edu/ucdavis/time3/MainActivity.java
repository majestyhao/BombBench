package edu.ucdavis.time3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.ssearch.SearchService;

/**
 * Class: MainActivity
 * Description:  Sensitive data is sent out when the period between now and last is larger than a
 * specfic time
 * Authors：Hao Fu(haofu@ucdavis.edu)
 * Date: 6/30/2016 6:35 PM
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
