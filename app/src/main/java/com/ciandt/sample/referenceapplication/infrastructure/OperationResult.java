package com.ciandt.sample.referenceapplication.infrastructure;

/**
 * Created by athila on 11/11/15.
 */
public class OperationResult<T> {
    public static final int NO_ERROR = -1;

    private T mResult;
    private int mError = NO_ERROR;

    public int getError() {
        return mError;
    }

    public void setError(int error) {
        mError = error;
    }

    public T getResult() {
        return mResult;
    }

    public void setResult(T result) {
        mResult = result;
    }
}
