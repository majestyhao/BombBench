package edu.ucdavis.smsreceived2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentService = new Intent(this, SmsService.class);
        startService(intentService);
        Toast.makeText(this, "启动service中.....", Toast.LENGTH_LONG).show();
    }
}
