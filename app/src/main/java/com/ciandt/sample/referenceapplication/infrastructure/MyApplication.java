package com.ciandt.sample.referenceapplication.infrastructure;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ciandt.sample.referenceapplication.notification.setup.GcmRegistrationService;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

import net.sqlcipher.database.SQLiteDatabase;

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

    public void initSQLCipher() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLoadSQLCipher = sharedPreferences.getBoolean("isLoadSQLCipher", false);

        if (!isLoadSQLCipher) {
            SQLiteDatabase.loadLibs(this);
            sharedPreferences.edit()
                    .putBoolean("isLoadSQLCipher", true).commit();
        }
    }
}
