package com.ciandt.sample.referenceapplication.business;

import android.content.Context;

import com.ciandt.sample.referenceapplication.entity.User;
import com.ciandt.sample.referenceapplication.infrastructure.OperationResult;
import com.ciandt.sample.referenceapplication.repository.network.BackendIntegrator;
import com.ciandt.sample.referenceapplication.repository.network.ConnectionParameters;
import com.ciandt.sample.referenceapplication.repository.network.NetworkConstants;

import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.HashMap;

public class UserBusiness extends BaseBusiness {

    private BackendIntegrator mBackendIntegrator;

    public UserBusiness(Context context) {
        super(context);

        mBackendIntegrator = new BackendIntegrator(mContext);
    }

    public OperationResult<User> login(String username, String password) {
        AbstractMap<String, String> urlParameters = new HashMap<>();
        urlParameters.put(User.URL_PARAMETER_KEY_USERNAME, username);
        urlParameters.put(User.URL_PARAMETER_KEY_PASSWORD, password);

        final ConnectionParameters connectionParams = new ConnectionParameters.Builder()
                .scheme(ConnectionParameters.Scheme.HTTPS)
                .endpoint(NetworkConstants.Endpoints.LOGIN)
                .operationMethod(ConnectionParameters.OperationMethod.GET)
                .urlParameters(urlParameters)
                .build();

        OperationResult<JSONObject> rawResult = mBackendIntegrator.execute(connectionParams);
        OperationResult<User> result = new OperationResult<>();

        int error = rawResult.getError();
        if(error != OperationResult.NO_ERROR) {
            result.setError(error);
        } else {
            User user = new User(rawResult.getResult());
            result.setResult(user);
        }

        return result;
    }
}
