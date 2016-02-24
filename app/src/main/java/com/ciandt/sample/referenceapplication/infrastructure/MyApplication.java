package com.ciandt.sample.referenceapplication.infrastructure;

import android.app.Application;
import android.content.Intent;

import com.ciandt.sample.referenceapplication.notification.GcmRegistrationService;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: These are Parse specific stuff necessary to push registration. Use your own server specific registration logic
        Parse.initialize(this, "oUZjvMyLohNCAlAmoi8rWQdUq1MXyDNxHvjTwVUM", "q3ToEHBRnZnYA5GXQfMxTod5r1rrJqpVcNGc9yaA");
        ParseInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                // start registration
                startService(new Intent(MyApplication.this, GcmRegistrationService.class));
            }
        });
    }
}
