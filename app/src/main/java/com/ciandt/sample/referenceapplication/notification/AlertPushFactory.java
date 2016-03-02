package com.ciandt.sample.referenceapplication.notification;

import android.content.Context;

import com.ciandt.sample.referenceapplication.infrastructure.MyLog;

public class AlertPushFactory {

    // TODO: these values are agreed with the backend. Define here your own Alert Push tags you wish to handle
    public static final String TAG_TRIAL_EXPIRING = "TRIAL_EXPIRING";


    public static AlertPushNotification createAlertPush(Context ctx, String tag, String extra) {
        if (tag == null) {
            MyLog.error("New push notification of type alert does not have 'tag' atribute");
            return null;
        }

        switch (tag) {
            case TAG_TRIAL_EXPIRING:
                try {
                    int remainingTime = Integer.parseInt(extra);
                    return new AlertPushTrialExpiring(ctx, remainingTime);
                } catch (NumberFormatException ex) {
                    MyLog.error("New push notification of type 'Trial Expiring' with invalid 'remainingTime' attribute: "+extra);
                    return null;
                }
        }

        MyLog.error("Cannot create alert notification of type: "+tag);
        return null;
    }
}
