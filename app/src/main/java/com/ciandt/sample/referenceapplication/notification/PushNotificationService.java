package com.ciandt.sample.referenceapplication.notification;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

public class PushNotificationService extends GcmListenerService {

    private static final String KEY_SNS_ROOT = "default"; // SNS put the message under the key "default" within the extras

    public PushNotificationService() {
        super("BTPushNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        // has effect of unparcelling Bundle
        if (!extras.isEmpty()) {
            /*
             * Filter messages based on message type. Since it is likely that
             * GCM will be extended in the future with new message types, just
             * ignore any message types you're not interested in, or that you
             * don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                // do nothing...
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                // do nothing...
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                String pushMessage;
                if (intent.getExtras().containsKey(Constants.MixPanel.MESSAGE_TYPE_MIXPANEL)) {
                    pushMessage = intent.getExtras().getString(Constants.MixPanel.MESSAGE_TYPE_MIXPANEL);
                    handleMixPanelNotification(pushMessage);
                } else {
                    pushMessage = extras.getString(KEY_SNS_ROOT);
                    handleNotification(pushMessage);
                }
                BTLiveLog.debug("New push notification arrived:");
                BTLiveLog.debug(pushMessage);
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        BTPushNotificationReceiver.completeWakefulIntent(intent);
    }

    private void handleMixPanelNotification(String message) {
        PushNotification push = PushFactory.createMixPanelPushNotification(this, message);
        if (push != null) {
            push.triggerAction();
        }
    }

    private void handleNotification(String jsonData) {
        if (jsonData == null) {
            return;
        }
        
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(jsonData);
        } catch (JSONException e) {
            BTLiveLog.error("New push notification with invalid json format: "+jsonData);
            return;
        }

        PushNotification push = PushFactory.createPushNotification(this, jsonObj);
        if (push != null) {
            push.triggerAction();
        }
    }
}
