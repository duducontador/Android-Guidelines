package com.ciandt.sample.referenceapplication.entity;

import android.database.Cursor;

import org.json.JSONObject;

@SuppressWarnings("WeakerAccess")
public class User {
    public static final String JSON_KEY_ID = "objectId";
    public static final String JSON_KEY_NAME = "name";
    public static final String JSON_KEY_USERNAME = "username";

    public static final String URL_PARAMETER_KEY_USERNAME = "username";
    public static final String URL_PARAMETER_KEY_PASSWORD = "password";

    private String mId;
    private String mName;
    private String mUsername;

    @SuppressWarnings("unused")
    public User() {
        super();
    }

    /**
     * Constructor used for construct instances based on JSON returned from backend
     * @param json the json response from backend
     */
    public User(JSONObject json) {
        mId = json.optString(JSON_KEY_ID);
        mName = json.optString(JSON_KEY_NAME);
        mUsername = json.optString(JSON_KEY_USERNAME);
    }

    @SuppressWarnings({"UnusedParameters", "unused"})
    public User(Cursor cursor) {
        // TODO: construct the user entity from the data stored at local database
    }

    @SuppressWarnings("unused")
    public String getId() {
        return mId;
    }

    @SuppressWarnings("unused")
    public void setId(String id) {
        this.mId = id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return mName;
    }

    @SuppressWarnings("unused")
    public void setName(String name) {
        this.mName = name;
    }

    public String getUsername() {
        return mUsername;
    }

    @SuppressWarnings("unused")
    public void setUsername(String username) {
        this.mUsername = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mUsername='" + mUsername + '\'' +
                '}';
    }
}
