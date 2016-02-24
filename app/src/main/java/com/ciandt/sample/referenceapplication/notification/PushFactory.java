package com.ciandt.sample.referenceapplication.notification;

import android.content.Context;

import com.ciandt.sample.referenceapplication.infrastructure.MyLog;

import org.json.JSONObject;

public class PushFactory {

    // Push type "alert": this type of push will pop a local notification to the user
    private static final String KEY_ALERT = "alert";

    // Push type "sync" must be used to perform a silent sync with no user awareness
    private static final String KEY_SYNC = "sync";

    // TODO: these json keys are bound to the push sent by the server. Replace them or add more entries to your own needs
    private static final String KEY_TAG = "tag";
    private static final String KEY_EXTRA = "extra";

    public static PushNotification createPushNotification(Context ctx, JSONObject pushJson) {
        // Type "alert" means that the push will be showed to the user through a notification
        JSONObject alertMessage = pushJson.optJSONObject(KEY_ALERT);
        if (alertMessage != null) {
            // The push is of type "alert"
            String tag = alertMessage.optString(KEY_TAG);
            String extra = alertMessage.optString(KEY_EXTRA);

            return AlertPushFactory.createAlertPush(ctx, tag, extra);
        }

        // Type "sync" means that the push is silent and will trigger a sync
        JSONObject syncMessage = pushJson.optJSONObject(KEY_SYNC);
        if (syncMessage != null) {
            // The push is of type "sync"
            String extra = syncMessage.optString(KEY_EXTRA);
            String tag = syncMessage.optString(KEY_TAG);

            return SyncPushFactory.createSyncPush(ctx, tag, extra);
        }

        MyLog.error("Invalid push notification: "+pushJson);
        return null;
    }
}
