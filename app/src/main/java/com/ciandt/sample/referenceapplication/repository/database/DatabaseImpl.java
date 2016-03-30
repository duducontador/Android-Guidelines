package com.ciandt.sample.referenceapplication.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ciandt.sample.referenceapplication.repository.database.table.ProductTable;

@SuppressWarnings("WeakerAccess")
public class DatabaseImpl extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ciandt_android_guide_lines.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseImpl(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(final SQLiteDatabase db) {
        db.execSQL(ProductTable.SQL_CREATE);

        // TODO: remove the mock insert
        String sqlMock1 = "INSERT INTO product VALUES(1, 'Mouse Apple', 250.90, 'M15');";
        String sqlMock2 = "INSERT INTO product VALUES(2, 'Other Mouse', 30.70, 'M17');";

        db.execSQL(sqlMock1);
        db.execSQL(sqlMock2);
    }

    @Override
    public final void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

        //if ((oldVersion == 1) && (newVersion == 2)) {
            // Creating new tables
            //db.execSQL(NewTable.SQL_CREATE);

            // And altering
            //db.execSQL("ALTER TABLE " + ProductTable.TABLE_NAME + " ADD " + ProductTable.TYPE + " TEXT");
        //}
    }

}
