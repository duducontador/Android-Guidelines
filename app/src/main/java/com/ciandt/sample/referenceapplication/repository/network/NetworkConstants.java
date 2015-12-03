package com.ciandt.sample.referenceapplication.repository.network;

/**
 * Created by athila on 11/11/15.
 */
public class NetworkConstants {

    // TODO: map your own errors
    public static final class Error {
        public static final int GENERIC_CONNECTION_ERROR = 7000;
        public static final int CONNECTION_TIMEOUT_ERROR = 7001;
        public static final int NO_CONNECTION_AVAILABLE = 7002;
        public static final int INTERNAL_ERROR = 7003;
    }

    public static final class Endpoints {
        public static final String LOGIN = "login";
    }
}
