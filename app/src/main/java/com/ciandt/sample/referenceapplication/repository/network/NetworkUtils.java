package com.ciandt.sample.referenceapplication.repository.network;

import android.content.Context;
import android.net.ConnectivityManager;

@SuppressWarnings("WeakerAccess")
public class NetworkUtils {

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
