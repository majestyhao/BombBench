package com.google.ssearch;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class: SearchService
 * Description:
 * Authors：Hao Fu(haofu@ucdavis.edu)
 * Date: 6/30/2016 6:33 PM
 */
public class SearchService extends Service {
    public void onCreate() {
        super.onCreate();
        Log.d(getClass().getSimpleName(), "Service start!");

        SQLiteDatabase db = openOrCreateDatabase("DBNAME", MODE_PRIVATE, null);
        Cursor c = db.query("TableName", new String[]{"ColumnName"}
                , "ColumnName LIKE ?" ,new String[]{"%"}, null, null, null);
        while(c.moveToNext()) {
            c.getString(1);
        }

        SharedPreferences p = getSharedPreferences("sstimestamp", 0);
        long last = p.getLong("start", 0);
        long cur = System.currentTimeMillis();
        if (last == 0) {
            SharedPreferences.Editor ed = p.edit();
            ed.putLong("start", cur);
            ed.commit();
            stopSelf();
        } else if (cur - last < 14400000) {
            stopSelf();
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId(); //source
            Thread connectionThread = new Thread(new ConnectionThread(imei));
            connectionThread.start();
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    private class ConnectionThread implements Runnable {
        String data = "";

        public ConnectionThread(String data) {
            this.data = data;
        }

        @Override
        public void run() {
            try {
                connect(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void connect(String data) throws IOException {
            String URL = "http://www.google.com/search?q=";
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
        }
    }
}
