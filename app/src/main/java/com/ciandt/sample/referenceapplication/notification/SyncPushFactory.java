package com.ciandt.sample.referenceapplication.notification;

import android.content.Context;

import com.ciandt.sample.referenceapplication.infrastructure.MyLog;

public class SyncPushFactory {

    public static SyncPushNotification createSyncPush(Context ctx, String action, String extra) {
        if (action == null) {
            MyLog.error("New push notification of type sync does not have 'action' atribute");
            return null;
        }

        // TODO: create the logic to return the correct sync push notification
        return null;
    }
}
