package com.ciandt.sample.referenceapplication.repository.database.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

@SuppressWarnings("WeakerAccess")
public class BaseDAO {

    protected final Context mContext;

    protected BaseDAO(final Context context) {
        mContext = context;
    }

    @SuppressWarnings("SameParameterValue")
    protected Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs,
                           final String sortOrder) {

        ContentResolver contentResolver = mContext.getContentResolver();

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
    }
}
