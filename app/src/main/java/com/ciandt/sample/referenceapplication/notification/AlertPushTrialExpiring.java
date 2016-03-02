package com.ciandt.sample.referenceapplication.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;

import com.ciandt.sample.referenceapplication.R;
import com.ciandt.sample.referenceapplication.ui.main.MainActivity;

public class AlertPushTrialExpiring extends AlertPushNotification {

    public AlertPushTrialExpiring(Context ctx, int remaining) {
        super(ctx);

        // TODO: implement your own logic
        mTitle = ctx.getString(R.string.push_trial_expiring_title);
        mMessage = ctx.getString(R.string.push_trial_expiring_message, remaining);

        // Default action
        Intent actionIntent = new Intent(mContext, MainActivity.class);
        // Set unique action to prevent issues involving multiple pending intents
        actionIntent.setAction(Long.toString(System.currentTimeMillis()));
        actionIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        // The stack builder object will contain an artificial back stack for the started Activity.
        // This ensures that navigating backward from the Activity leads out of your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(actionIntent);

        mPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void triggerAction() {
        sendNotification(TRIAL_EXPIRING_ID);
    }
}
