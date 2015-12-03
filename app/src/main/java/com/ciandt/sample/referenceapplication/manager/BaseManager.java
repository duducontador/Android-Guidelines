package com.ciandt.sample.referenceapplication.manager;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by athila on 02/12/15.
 */
public class BaseManager {
    private List<AsyncTask<?, ?, ?>> mTaskList;
    protected Context mContext;

    protected BaseManager(Context context) {
        mContext = context;
        mTaskList = new ArrayList<AsyncTask<?, ?, ?>>();
    }

    public void cancelOperations() {
        for (AsyncTask<?, ?, ?> task : mTaskList) {
            task.cancel(false);
        }
    }

    protected void addToTaskList(AsyncTask<?, ?, ?> task) {
        mTaskList.add(task);
    }

    protected void removeFromTaskList(AsyncTask<?, ?, ?> task) {
        mTaskList.remove(task);
    }
}
