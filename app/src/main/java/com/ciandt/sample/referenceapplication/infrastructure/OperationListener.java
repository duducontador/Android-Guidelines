package com.ciandt.sample.referenceapplication.infrastructure;

// Abstract class and not an Interface, so the client does not need
// to follow the exact contract provided by this class.
// This will prevent empty implementations on UI layer
public abstract class OperationListener<T> {

    public void onSuccess(T result){}

    // Note: If necessary, you can create an error entity to hold further information
    // like error description, error message, etc
    public void onError(int error){}

    public void onCancel(){}

    public void onProgressUpdate(int progress){}
}
