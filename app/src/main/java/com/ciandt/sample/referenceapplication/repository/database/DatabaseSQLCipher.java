package com.ciandt.sample.referenceapplication.repository.database;

import android.content.Context;

import com.ciandt.sample.referenceapplication.repository.database.table.ProductTable;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * Created by felipets on 01/03/16.
 */
public class DatabaseSQLCipher extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ciandtandroidguidelines_encripytion.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseSQLCipher(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(final SQLiteDatabase db) {
        db.execSQL(ProductTable.SQL_CREATE);
    }

    @Override
    public final void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

        if ((oldVersion == 1) && (newVersion == 2)) {
            // Creating new tables
            //db.execSQL(NewTable.SQL_CREATE);

            // And altering
            //db.execSQL("ALTER TABLE " + ProductTable.TABLE_NAME + " ADD " + ProductTable.TYPE + " TEXT");
        }
    }

}
