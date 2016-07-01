package edu.ucdavis.time6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.safesys.remover.JmAdV2;

/**
 * Class: MainActivity
 * Description: Sensitive data is sent out when meet the requirement of TimerTask.
 * Authors：Hao Fu(haofu@ucdavis.edu)
 * Date: 7/1/2016 9:51 AM
 */
public class MainActivity extends AppCompatActivity {
    private JmAdV2 mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mAd = new JmAdV2(this);
        this.mAd.startAd();
    }
}
