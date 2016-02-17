package com.ciandt.sample.referenceapplication.infrastructure;

/**
 * Created by alisson on 2/10/16.
 */
public final class ApplicationConfigurationExtra {

    // RELEASE - PRODUCTION

    /**
     * @return Environment Mode
     */
    public static String getEnvironmentMode() {
        return Constants.ApplicationConfigurationMode.PRODUCTION.name();
    }

}
