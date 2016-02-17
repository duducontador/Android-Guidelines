package com.ciandt.sample.referenceapplication.infrastructure;

public class Constants {

    public enum ApplicationConfigurationMode {
        DEVELOPMENT, STAGE, PRODUCTION
    }

    public interface ApplicationConfigurationParameter {

        String DEFAULT_HOST = "default_host";
        String ANALYTICS_TRACK_ID = "analytics_track_id";
        String ENVIRONMENT_MODE = "environment_mode";
        String LOG_LEVEL = "log_level";
    }

    // TODO: use the keys your server are expecting
    public interface Gcm {
        String JSON_KEY_DEVICE_TYPE = "deviceType";
        String JSON_KEY_PUSH_TYPE = "pushType";
        String JSON_KEY_DEVICE_TOKEN = "deviceToken";
        String JSON_KEY_SENDER_ID = "GCMSenderId";
    }

    public interface QuickstartPreferences {
        String SENT_TOKEN_TO_SERVER = "sent_gcm_token_to_server";
        String REGISTRATION_COMPLETE = "com.ciandt.sample.referenceapplication.GCM_REGISTRATION_COMPLETE";
    }
}
