package com.ciandt.sample.referenceapplication.notification;

import android.content.Context;

import com.ciandt.sample.referenceapplication.infrastructure.MyLog;

public class AlertPushFactory {

    // these values are agreed with the backend. Do not change them.
    // More information: https://sites.google.com/a/ciandt.com/btlive/projeto-BTLive/desenvolvimento/push-notification?pli=1
    // They will be used to get appropriate text message from the strings.xml for the notification
    public static final String TAG_NEW_TRAINING = "BTFIT_NEW_TRAINING";
    public static final String TAG_NEW_BADGE = "BTFIT_NEW_BADGE";
    public static final String TAG_NEW_DAN = "BTFIT_NEW_DAN";
    public static final String TAG_TRIAL_EXTENDED = "BTFIT_TRIAL_EXTENDED";
    public static final String TAG_PRODUCT_BLOCKED = "BTFIT_PRODUCT_BLOCKED";
    public static final String TAG_PAYMENT_FAILED = "BTFIT_PAYMENT_FAILED";
    public static final String TAG_TRIAL_EXPIRING = "BTFIT_PRODUCT_TRIAL_EXPIRING";
    public static final String TAG_MIXPANEL = "BTFIT_MIXPANEL";
    public static final String TAG_CUSTOM_MESSAGE = "BTFIT_CUSTOM_MESSAGE";


    public static AlertPushNotification createAlertPush(Context ctx, String tag, String extra) {
        if (tag == null) {
            MyLog.error("New push notification of type alert does not have 'tag' atribute");
            return null;
        }

//        if (tag.equals(TAG_NEW_TRAINING)) {
//            return new AlertPushNewTraining(ctx, programId);
//        }

        MyLog.error("Cannot create alert notification of type: "+tag);
        return null;
    }
}
