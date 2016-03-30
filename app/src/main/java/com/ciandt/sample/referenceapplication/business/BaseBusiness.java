package com.ciandt.sample.referenceapplication.business;

import android.content.Context;

@SuppressWarnings("WeakerAccess")
public class BaseBusiness {

    protected final Context mContext;

    protected BaseBusiness(Context context) {
        mContext = context;
    }
}
