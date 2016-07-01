package edu.ucdavis.smsreceived2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Process;
import android.widget.Toast;

public class SmsService extends Service {

    private SmsObserver mObserver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "SmsService 服务器启动了....", Toast.LENGTH_LONG).show();
        // 在这里启动
        ContentResolver resolver = getContentResolver();
        mObserver = new SmsObserver(resolver, new SmsHandler(this), getApplicationContext());
        resolver.registerContentObserver(Uri.parse("content://sms"), true,mObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getContentResolver().unregisterContentObserver(mObserver);
        Process.killProcess(Process.myPid());
    }
}
