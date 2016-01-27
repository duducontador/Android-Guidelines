package com.ciandt.sample.referenceapplication.repository.database.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by alisson on 27/01/16.
 */
public class BaseDAO {

    protected Context mContext;

    protected BaseDAO(final Context context) {
        mContext = context;
    }

    protected Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs,
                        final String sortOrder) {

        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);

        return cursor;
    }
}
