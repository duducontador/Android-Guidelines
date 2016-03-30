package com.ciandt.sample.referenceapplication.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.TaskStackBuilder;

import com.ciandt.sample.referenceapplication.R;
import com.ciandt.sample.referenceapplication.ui.main.MainActivity;

@SuppressWarnings({"CanBeFinal","WeakerAccess"})
public abstract class AlertPushNotification extends PushNotification {

    public static final int TRIAL_EXPIRING_ID = 1;

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

    protected AlertPushNotification(Context ctx) {
        super(ctx);

        // TODO: change these default values to your own and overwrite them on the specific implementations
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

    // Put the message into a notification and post it.
    protected void sendNotification(@SuppressWarnings("SameParameterValue") int notificationId) {
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
