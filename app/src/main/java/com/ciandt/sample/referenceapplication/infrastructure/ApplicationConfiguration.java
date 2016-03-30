package com.ciandt.sample.referenceapplication.infrastructure;

import android.annotation.SuppressLint;

import com.ciandt.sample.referenceapplication.BuildConfig;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ApplicationConfiguration {

    @SuppressLint("UseSparseArrays")
    private static final Map<String, String> applicationParameterCacheMap = new HashMap<>();

    static {
        applicationParameterCacheMap.put(Constants.ApplicationConfigurationParameter.DEFAULT_HOST, BuildConfig.DEFAULT_HOST);

        applicationParameterCacheMap.put(Constants.ApplicationConfigurationParameter.ANALYTICS_TRACK_ID, BuildConfig.ANALYTICS_TRACK_ID);

        applicationParameterCacheMap.put(Constants.ApplicationConfigurationParameter.ENVIRONMENT_MODE, ApplicationConfigurationExtra.getEnvironmentMode());
        applicationParameterCacheMap.put(Constants.ApplicationConfigurationParameter.LOG_LEVEL, String.valueOf(BuildConfig.LOG_LEVEL));
    }

    /**
     * @return Tlc Brazil site base url
     */
    public static String getDefaultHost() {
        return applicationParameterCacheMap.get(Constants.ApplicationConfigurationParameter.DEFAULT_HOST);
    }

    /**
     * @return Analytics Track ID
     */
    public static String getAnalyticsTrackId() {
        return applicationParameterCacheMap.get(Constants.ApplicationConfigurationParameter.ANALYTICS_TRACK_ID);
    }

    /**
     * @return Environment Mode
     */
    public static String getEnvironmentMode() {
        return applicationParameterCacheMap.get(Constants.ApplicationConfigurationParameter.ENVIRONMENT_MODE);
    }

    /**
     * @return Log Level
     */
    public static String getLogLevel() {
        return applicationParameterCacheMap.get(Constants.ApplicationConfigurationParameter.LOG_LEVEL);
    }
}
