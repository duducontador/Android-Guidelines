package com.ciandt.sample.referenceapplication.business;

import android.content.Context;

import com.ciandt.sample.referenceapplication.R;
import com.ciandt.sample.referenceapplication.infrastructure.Constants;
import com.ciandt.sample.referenceapplication.infrastructure.OperationResult;
import com.ciandt.sample.referenceapplication.repository.network.BackendIntegrator;
import com.ciandt.sample.referenceapplication.repository.network.ConnectionParameters;
import com.ciandt.sample.referenceapplication.repository.network.NetworkConstants;
import com.parse.ParseInstallation;

import org.json.JSONException;
import org.json.JSONObject;

public class GcmBusiness extends BaseBusiness {

    private BackendIntegrator mBackendIntegrator;

    public GcmBusiness(Context context) {
        super(context);

        mBackendIntegrator = new BackendIntegrator(mContext);
    }

    public OperationResult<Void> sendRegistrationToServer(String gcmToken) {
        // TODO: this example uses Parse.com as our push server. You must send your registration token to your own server
        String endpoint = NetworkConstants.Endpoints.PARSE_REGISTRATION + "/" + ParseInstallation.getCurrentInstallation().getObjectId();
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put(Constants.Gcm.JSON_KEY_DEVICE_TYPE, "android");
            requestBody.put(Constants.Gcm.JSON_KEY_PUSH_TYPE, "gcm");
            requestBody.put(Constants.Gcm.JSON_KEY_SENDER_ID, mContext.getString(R.string.gcm_defaultSenderId));
            requestBody.put(Constants.Gcm.JSON_KEY_DEVICE_TOKEN, gcmToken);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        final ConnectionParameters connectionParams = new ConnectionParameters.Builder()
                .scheme(ConnectionParameters.Scheme.HTTPS)
                .endpoint(endpoint)
                .operationMethod(ConnectionParameters.OperationMethod.PUT)
                .requestBody(requestBody.toString())
                .build();

        OperationResult<JSONObject> rawResult = mBackendIntegrator.execute(connectionParams);
        OperationResult<Void> result = new OperationResult<>();

        int error = rawResult.getError();
        if(error != OperationResult.NO_ERROR) {
            result.setError(error);
        }

        return result;
    }
}
