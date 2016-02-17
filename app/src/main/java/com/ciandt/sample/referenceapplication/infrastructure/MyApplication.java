package com.ciandt.sample.referenceapplication.infrastructure;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "oUZjvMyLohNCAlAmoi8rWQdUq1MXyDNxHvjTwVUM", "q3ToEHBRnZnYA5GXQfMxTod5r1rrJqpVcNGc9yaA");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
