package com.ciandt.sample.referenceapplication.notification;

import android.os.Bundle;

import com.ciandt.sample.referenceapplication.infrastructure.MyLog;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

public class PushNotificationService extends GcmListenerService {

    // TODO: handle the json notification on your needs
    private static final String KEY_PUSH_MESSAGE = "data";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        if (!data.isEmpty()) {
            // TODO: ATHILA - handle notification
            String pushMessage;
            pushMessage = data.getString(KEY_PUSH_MESSAGE);
            handleNotification(pushMessage);
            MyLog.debug("New push notification arrived:");
            MyLog.debug(pushMessage);
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
            MyLog.error("New push notification with invalid json format: "+jsonData);
            return;
        }

        PushNotification push = PushFactory.createPushNotification(this, jsonObj);
        if (push != null) {
            push.triggerAction();
        }
    }
}
