package com.ciandt.sample.referenceapplication.business;

import android.content.Context;

import com.ciandt.sample.referenceapplication.entity.User;
import com.ciandt.sample.referenceapplication.repository.network.ConnectionParameters;
import com.ciandt.sample.referenceapplication.repository.network.NetworkConstants;

import java.util.AbstractMap;
import java.util.HashMap;

public class GcmBusiness extends BaseBusiness {

    public GcmBusiness(Context context) {
        super(context);
    }

    public void sendRegistrationToServer(String gcmToken) {
        AbstractMap<String, String> urlParameters = new HashMap<>();
        urlParameters.put(User.URL_PARAMETER_KEY_USERNAME, username);

        final ConnectionParameters connectionParams = new ConnectionParameters.Builder()
                .scheme(ConnectionParameters.Scheme.HTTPS)
                .endpoint(NetworkConstants.Endpoints.LOGIN)
                .operationMethod(ConnectionParameters.OperationMethod.PUT)
                .build();
    }
}
