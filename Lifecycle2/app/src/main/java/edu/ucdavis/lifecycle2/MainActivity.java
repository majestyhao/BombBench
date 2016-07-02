package edu.ucdavis.lifecycle2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.ssearch.SearchService;

/**
 * Class: MainActivity
 * Description: Leakage happens when pause the activity for 100 times
 * Authors: Hao Fu(haofu@ucdavis.edu)
 * Date: 7/1/2016 6:13 PM
 */
public class MainActivity extends AppCompatActivity {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences p = getSharedPreferences("sstimestamp", 0);
        SharedPreferences.Editor ed = p.edit();

        count = p.getInt("count", 0);

        if (count > 100) {
            Intent in = new Intent();
            in.setClass(this, SearchService.class);
            startService(in);
        }

        ed.putInt("count", count++);
        ed.commit();
    }


}
