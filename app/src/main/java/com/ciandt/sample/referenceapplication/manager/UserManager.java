package com.ciandt.sample.referenceapplication.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.ciandt.sample.referenceapplication.business.UserBusiness;
import com.ciandt.sample.referenceapplication.entity.User;
import com.ciandt.sample.referenceapplication.infrastructure.OperationListener;
import com.ciandt.sample.referenceapplication.infrastructure.OperationResult;

public class UserManager extends BaseManager {

    private final UserBusiness mUserBusiness;

    public UserManager(Context context) {
        super(context);
        mUserBusiness = new UserBusiness(mContext);
    }

    public void login(final String userName, final String password, final OperationListener<User> listener) {
        AsyncTask<Void, Integer, OperationResult<User>> task = new AsyncTask<Void, Integer, OperationResult<User>>() {

            @Override
            protected OperationResult<User> doInBackground(Void... params) {
                // TODO: implements the logic for deciding where to fetch the data from (backend or database)

                return mUserBusiness.login(userName, password);
            }

            @Override
            protected void onPostExecute(OperationResult<User> operationResult) {
                removeFromTaskList(this);
                if (listener != null) {
                    int error = operationResult.getError();
                    if (error != OperationResult.NO_ERROR) {
                        listener.onError(error);
                    } else {
                        listener.onSuccess(operationResult.getResult());
                    }
                }
            }

            @Override
            protected void onCancelled() {
                removeFromTaskList(this);
                if (listener != null) {
                    listener.onCancel();
                }
            }
        };

        // Task execution
        addToTaskList(task);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
