package com.ciandt.sample.referenceapplication.infrastructure;

/**
 * Created by alisson on 2/10/16.
 */
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
}
