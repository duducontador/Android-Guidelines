package com.ciandt.sample.referenceapplication.notification;

import android.content.Context;

import com.ciandt.sample.referenceapplication.infrastructure.MyLog;

@SuppressWarnings("WeakerAccess")
public class SyncPushFactory {

    @SuppressWarnings({"UnusedParameters", "SameReturnValue"})
    public static SyncPushNotification createSyncPush(Context ctx, String tag, String extra) {
        if (tag == null) {
            MyLog.error("New push notification of type sync does not have 'tag' attribute");
            return null;
        }

        // TODO: create the logic to return the correct sync push notification, like the "alert" push implementation
        return null;
    }
}
