package com.ciandt.sample.referenceapplication.repository.network;

import com.ciandt.sample.referenceapplication.infrastructure.ApplicationConfiguration;

import java.util.AbstractMap;
@SuppressWarnings({"unused"})
public class ConnectionParameters {
    private final String mScheme;
    private final String mHost;
    private final String mEndpoint;
    private final int mConnectionTimeout;
    private final boolean mEnableSecurity;
    private final String mOperationMethod;
    private final String mRequestBody;
    private final AbstractMap<String, String> mUrlParameters;

    private ConnectionParameters(Builder builder) {
        mScheme = builder.mScheme;
        mHost = builder.mHost;
        mEndpoint = builder.mEndpoint;
        mConnectionTimeout = builder.mConnectionTimeout;
        mEnableSecurity = builder.mEnableSecurity;
        mOperationMethod = builder.mOperationMethod;
        mRequestBody = builder.mRequestBody;
        mUrlParameters = builder.mUrlParameters;
    }

    public String getScheme() {
        return mScheme;
    }

    public String getHost() {
        return mHost;
    }

    public String getEndpoint() {
        return mEndpoint;
    }

    public int getConnectionTimeout() {
        return mConnectionTimeout;
    }

    public boolean isSecurityEnabled() {
        return mEnableSecurity;
    }

    public String getOperationMethod() {
        return mOperationMethod;
    }

    public String getRequestBody() {
        return mRequestBody;
    }

    public AbstractMap<String, String> getUrlParameters() {
        return mUrlParameters;
    }

    public static class Builder {
        private String mScheme;
        private String mHost;
        private String mEndpoint;
        private int mConnectionTimeout;
        private boolean mEnableSecurity;
        private String mOperationMethod;
        private String mRequestBody;
        private AbstractMap<String, String> mUrlParameters;

        public Builder() {
            // initialize with defaults
            mScheme = Defaults.SCHEME;
            mHost = Defaults.HOST;
            mEndpoint = Defaults.ENDPOINT;
            mConnectionTimeout = Defaults.CONNECTION_TIMEOUT;
            mEnableSecurity = Defaults.ENABLE_SECURITY;
            mOperationMethod = Defaults.OPERATION_METHOD;
            mRequestBody = Defaults.REQUEST_BODY;
            mUrlParameters = Defaults.URL_PARAMETERS;
        }

        public Builder scheme(@SuppressWarnings("SameParameterValue") String scheme) {
            this.mScheme = scheme;
            return this;
        }

        public Builder host(String host) {
            this.mHost = host;
            return this;
        }

        public Builder endpoint(String endpoint) {
            this.mEndpoint = endpoint;
            return this;
        }

        public Builder connectionTimeout(int connectionTimeout) {
            this.mConnectionTimeout = connectionTimeout;
            return this;
        }

        public Builder enableSecurity(boolean enableSecurity) {
            this.mEnableSecurity = enableSecurity;
            return this;
        }

        public Builder operationMethod(String operationMethod) {
            this.mOperationMethod = operationMethod;
            return this;
        }

        public Builder requestBody(String requestBody) {
            this.mRequestBody = requestBody;
            return this;
        }

        public Builder urlParameters(AbstractMap<String, String> urlParameters) {
            this.mUrlParameters = urlParameters;
            return this;
        }

        public ConnectionParameters build() {
            if (mScheme == null) {
                throw new IllegalArgumentException("Scheme must be supplied.");
            }

            if (mHost == null) {
                throw new IllegalArgumentException("Host must be supplied.");
            }

            if (mEndpoint == null) {
                throw new IllegalArgumentException("Endpoint must be supplied.");
            }

            if (mOperationMethod == null) {
                throw new IllegalArgumentException("Operation method must be supplied.");
            }

            if ((mOperationMethod.equals(OperationMethod.POST) || mOperationMethod.equals(OperationMethod.PUT))
                    && mRequestBody == null) {
                throw new IllegalArgumentException("POST and PUT operations must supply request body");
            }

            if (mEnableSecurity && !mScheme.equals(Scheme.HTTPS)) {
                throw new IllegalArgumentException("Security can only be enabled on https scheme");
            }

            return new ConnectionParameters(this);
        }
    }

    public static final class Defaults {

        public static final String HOST = ApplicationConfiguration.getDefaultHost();
        // TODO: Set your default scheme
        public static final String SCHEME = Scheme.HTTPS;
        // mEndpoint must be provided
        public static final String ENDPOINT = null;
        // default connection timeout to 20 secs
        public static final int CONNECTION_TIMEOUT = 20 * 1000;
        // connection security disabled by default
        public static final boolean ENABLE_SECURITY = false;
        // operation method must be provided
        public static final String OPERATION_METHOD = null;
        // request body is optional
        public static final String REQUEST_BODY = null;
        // mEndpoint parameters are optional
        public static final AbstractMap<String, String> URL_PARAMETERS = null;
    }

    public static final class OperationMethod {
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
    }

    public static final class Scheme {
        public static final String HTTP = "http";
        public static final String HTTPS = "https";
    }
}
