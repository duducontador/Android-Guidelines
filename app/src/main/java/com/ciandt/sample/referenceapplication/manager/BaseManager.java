package com.ciandt.sample.referenceapplication.manager;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class BaseManager {
    private final List<AsyncTask<?, ?, ?>> mTaskList;
    protected final Context mContext;

    protected BaseManager(Context context) {
        mContext = context;
        mTaskList = new ArrayList<>();
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
