package edu.ucdavis.time6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.safesys.remover.JmAdV2;

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
