package com.ciandt.sample.referenceapplication.infrastructure;

@SuppressWarnings("WeakerAccess")
public final class ApplicationConfigurationExtra {

    // DEBUG - DEVELOPMENT

    /**
     * @return Environment Mode
     */
    public static String getEnvironmentMode() {
        return Constants.ApplicationConfigurationMode.DEVELOPMENT.name();
    }

}
