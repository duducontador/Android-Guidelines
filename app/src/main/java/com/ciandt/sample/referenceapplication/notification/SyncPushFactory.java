package com.ciandt.sample.referenceapplication.notification;

import android.content.Context;

import com.ciandt.sample.referenceapplication.infrastructure.MyLog;

public class SyncPushFactory {

    public static SyncPushNotification createSyncPush(Context ctx, String tag, String extra) {
        if (tag == null) {
            MyLog.error("New push notification of type sync does not have 'tag' atribute");
            return null;
        }

        // TODO: create the logic to return the correct sync push notification, like the "alert" push implementation
        return null;
    }
}
