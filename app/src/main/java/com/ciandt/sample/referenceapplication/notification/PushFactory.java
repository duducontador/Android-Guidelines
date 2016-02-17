package com.ciandt.sample.referenceapplication.notification;

import android.content.Context;

import com.ciandt.sample.referenceapplication.infrastructure.MyLog;

import org.json.JSONObject;

public class PushFactory {

    private static final String KEY_ALERT = "alert";
    private static final String KEY_SYNC = "sync";
    private static final String KEY_TAG = "tag";
    private static final String KEY_ACTION = "action";
    private static final String KEY_EXTRA = "extra";

    public static PushNotification createPushNotification(Context ctx, JSONObject pushJson) {
        // Type "alert" means that the push will be showed to the user through a notification
        JSONObject alertMessage = pushJson.optJSONObject(KEY_ALERT);
        if (alertMessage != null) {
            // The push is of type "alert"
            String tag = alertMessage.optString(KEY_TAG);
            String extra = alertMessage.optString(KEY_EXTRA);
            AlertPushNotification alertPush = AlertPushFactory.createAlertPush(ctx, tag, extra);

            if (alertPush != null) {
                String userAction = alertMessage.optString(KEY_ACTION);
                alertPush.setUserAction(userAction);
            }

            return alertPush;
        }

        // Type "sync" means that the push is silent and will trigger a sync
        JSONObject syncMessage = pushJson.optJSONObject(KEY_SYNC);
        if (syncMessage != null) {
            // The push is of type "sync"
            String action = syncMessage.optString(KEY_ACTION);
            String extra = syncMessage.optString(KEY_EXTRA);

            return SyncPushFactory.createSyncPush(ctx, action, extra);
        }

        MyLog.error("Invalid push notification: "+pushJson);
        return null;
    }

    public static PushNotification createMixPanelPushNotification(Context ctx, String message) {
        // Type "alert" means that the push will be showed to the user through a notification
        if (message != null) {
            // The push is of type "mixpanel"
            String tag = AlertPushFactory.TAG_MIXPANEL;
            AlertPushNotification alertPush = AlertPushFactory.createAlertPush(ctx, tag, message);

            if (alertPush != null) {
                String userAction = AlertPushNotification.ACTION_MIXPANEL;
                alertPush.setUserAction(userAction);
            }

            return alertPush;
        }

        MyLog.error("Invalid push notification");
        return null;
    }
}
