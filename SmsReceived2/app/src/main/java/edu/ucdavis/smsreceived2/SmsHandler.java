package edu.ucdavis.smsreceived2;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

/**
 * Description:
 * Authors: Hao Fu(haofu@ucdavis.edu)
 * Date: 7/1/2016
 */
public class SmsHandler extends Handler {
    private Context mcontext;

    public SmsHandler(Context context) {
        this.mcontext = context;
    }

    @Override
    public void handleMessage(Message msg) {
        SmsObserver.SmsInfo smsInfo = (SmsObserver.SmsInfo) msg.obj;

        if (smsInfo.action == 1) {
            ContentValues values = new ContentValues();
            values.put("read", "1");
            mcontext.getContentResolver().update(
                    Uri.parse("content://sms/inbox"), values, "thread_id=?",
                    new String[] { smsInfo.thread_id });
        } else if (smsInfo.action == 2) {
            Uri mUri = Uri.parse("content://sms/");
            mcontext.getContentResolver().delete(mUri, "_id=?",
                    new String[] { smsInfo._id });
        }
    }
}
