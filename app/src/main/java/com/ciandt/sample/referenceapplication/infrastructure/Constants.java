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

    public interface SecurityKeys {
        String ALIAS = new String(new char[]{'S', '&', 'C', 'u', 'R', '1', 't', '!', 'S', 't', 'o',
                'r', 'e', 'C', 'I', 'T', '1', '8', '7', '2', '3', '4', 'R', 'S', 'A' });
        String PREFERENCE_NAME = new String(new char[]{'a', 's', 'd', 'f', 'c', 'a', 'f', 'd', 'g',
                's', 'f', 'v', 'c', 'x', 'e', 'r', 'd', 't', 'y', 'j', 'h', 'g', 'f', 'd', 's', 'e' });
        String CIPHER_TYPE = new String(new char[]{'R', 'S', 'A', '/', 'E', 'C', 'B', '/', 'P',
                'K', 'C', 'S', '1', 'P', 'a', 'd', 'd', 'i', 'n', 'g' });
        String TRANSFORMATION = new String(new char[]{'A', 'E', 'S', '/', 'C', 'B', 'C', '/', 'P',
                'K', 'C', 'S', '5', 'P', 'a', 'd', 'd', 'i', 'n', 'g' });
        String KEY_TRANSFORMATION = new String(new char[]{'A', 'E', 'S', '/', 'E', 'C', 'B', '/',
                'P', 'K', 'C', 'S', '5', 'P', 'a', 'd', 'd', 'i', 'n', 'g' });
        String SECRET_KEY_HASH_TRANSFORMATION = new String(new char[]{'S', 'H', 'A', '-', '2',
                '5', '6' });
        String CHARSET = new String(new char[]{'U', 'T', 'F', '-', '8' });
        String CIPHER_PROVIDER = new String(new char[]{'A', 'n', 'd', 'r', 'o', 'i', 'd', 'O', 'p', 'e', 'n',
                'S', 'S', 'L' });
        String HDR2 = new String(new char[]{'a', 's', 'p', 'i', 'n', 'o', 'c', 'd', 'i', 'n', 'i', 's', 'd',
                'n', 'o', 'n', 'd', 'd', 'o', 'c', 'n', 'i', 'o', 's', 'd', 'n' });
        String ANDROID_KEY_STORE = new String(new char[]{'A', 'n', 'd', 'r', 'o', 'i', 'd', 'K',
                'e', 'y', 'S', 't', 'o', 'r', 'e' });
        String SECURITY_KEY_NAME = new String(new char[]{'C', 'N', '=', 'C', 'I', 'T', 'K', 'K',
                'e', 'y', 'N', 'a', 'm', 'e', ',', ' ', 'O', '=', 'A', 'n', 'd', 'r', 'o', 'i', 'd',
                ' ', 'A', 'u', 't', 'h', 'o', 'r', 'i', 't', 'y' });
        String GENERIC_KEY = new String(new char[]{'u', 'i', 'v', 'u', 'b', 'y', 'c', 'f', 'r', 'f',
                'c', 'n', 'b', 'c', 'd', 'd', 'c', 'v', 'b', 'n' });
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
