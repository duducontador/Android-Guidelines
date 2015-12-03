package com.ciandt.sample.referenceapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.rule.ActivityTestRule;
import android.widget.Toast;

import com.ciandt.sample.referenceapplication.infrastructure.OperationResult;
import com.ciandt.sample.referenceapplication.repository.network.BackendIntegrator;
import com.ciandt.sample.referenceapplication.repository.network.ConnectionParameters;
import com.ciandt.sample.referenceapplication.ui.main.MainActivity;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by athila on 11/11/15.
 */
public class BackendIntegratorTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private Context mContext;

    @Before
    public void setup() {
        mContext = mActivityRule.getActivity();
    }

    @Test
    public void testConnection() {
        final CountDownLatch signal = new CountDownLatch(1);

        final ConnectionParameters connectionParams = new ConnectionParameters.Builder()
                .scheme(ConnectionParameters.Scheme.HTTPS)
                .host("api.github.com")
                .endpoint("users/athilahs")
                .operationMethod(ConnectionParameters.OperationMethod.GET)
                .build();

        final BackendIntegrator integrator = new BackendIntegrator(mContext);

        new AsyncTask<Void, Integer, OperationResult<JSONObject>>() {
            @Override
            protected OperationResult<JSONObject> doInBackground(Void... params) {
                return integrator.execute(connectionParams);
            }

            @Override
            protected void onPostExecute(OperationResult<JSONObject> operationResult) {
                if (operationResult.result != null) {
                    Toast.makeText(mContext, "Result: \n" + operationResult.result, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "Error: "+operationResult.error, Toast.LENGTH_LONG).show();
                }

                signal.countDown();
            }
        }.execute();

        try {
            signal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
