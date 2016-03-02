package com.ciandt.sample.referenceapplication.notification;

import android.content.Context;

import com.ciandt.sample.referenceapplication.infrastructure.MyLog;


public abstract class PushNotification {

    protected Context mContext;

    protected PushNotification(Context ctx) {
        mContext = ctx.getApplicationContext();
    }

    /**
     * Trigger the work to be done when the push arrives.
     * This method is supposed to be called in a background thread
     */
    public void triggerAction() {
        MyLog.debug("Push notification action triggered at base class.");
    }
}
