package com.ciandt.sample.referenceapplication.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.TaskStackBuilder;

import com.ciandt.sample.referenceapplication.R;
import com.ciandt.sample.referenceapplication.ui.main.MainActivity;

public abstract class AlertPushNotification extends PushNotification {

    public static final int NEW_TRAINING_ID = 1;
    public static final int NEW_BADGE_ID = 2;
    public static final int NEW_DAN_ID = 3;
    public static final int NEW_TRIAL_EXTENDED=4;
    public static final int DOWNLOAD_NOT_ENOUGH_SPACE = 5;
    public static final int PRODUCT_BLOCKED_ID = 6;
    public static final int TRIAL_EXPIRING_ID = 7;
    public static final int PAYMENT_FAILED_ID = 8;
    public static final int WAITING_NETWORK = 9;
    public static final int MIXPANEL_ID = 10;
    public static final int CUSTOM_MESSAGE_ID = 11;

    // these values are agreed with the backend. Do not change them.
    protected static final String ACTION_NEW_TRAINING = "btfitTraining";
    protected static final String ACTION_GAMEFICATION = "btfitGamefication";
    protected static final String ACTION_MIXPANEL = "btfitMixPanel";
    protected static final String ACTION_CUSTOM_MESSAGE = "btfitCustomMessage";

    /**
     * Action to be triggered when user clicks on the notification
     */
    protected String mUserAction;

    /**
     * Message to be shown on the notification body
     */
    protected String mMessage;

    /**
     * Notification title
     */
    protected String mTitle;

    /**
     * Ticker text of the notification
     */
    protected String mTickerText;

    /**
     * PendingIntent containing the activity to be launched on notification click
     */
    protected PendingIntent mPendingIntent;

    protected Resources mRes;

    protected AlertPushNotification(Context ctx) {
        super(ctx);
        mRes = mContext.getResources();

        // TODO: change these values to your own
        mTitle = "New Push";
        mTickerText = "You have a new push notification";
        mMessage = "Congratulations! You have a new Push Notification";

        // Default action
        Intent actionIntent = new Intent(mContext, MainActivity.class);
        // Set unique action to prevent issues involving multiple pending intents
        actionIntent.setAction(Long.toString(System.currentTimeMillis()));

        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(actionIntent);

        mPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void setUserAction(String userAction) {
        mUserAction = userAction;
    }

    // Put the message into a notification and post it.
    protected void sendNotification(int notificationId) {
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(mContext)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle(mTitle)
        .setStyle(new Notification.BigTextStyle().bigText(mMessage))
        .setContentText(mMessage)
        .setTicker(mTickerText)
        .setContentIntent(mPendingIntent)
        .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // TODO: set your own color
            builder.setColor(0xff0000);
        }

        notificationManager.notify(notificationId, builder.build());
    }
}
