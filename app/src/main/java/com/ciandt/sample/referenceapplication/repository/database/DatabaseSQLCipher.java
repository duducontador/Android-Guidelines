package com.ciandt.sample.referenceapplication.repository.database;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.ciandt.sample.referenceapplication.infrastructure.SecurityPreferences;
import com.ciandt.sample.referenceapplication.repository.database.table.ProductTable;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import static com.ciandt.sample.referenceapplication.infrastructure.SecurityPreferences.*;

/**
 * Created by felipets on 01/03/16.
 */
public class DatabaseSQLCipher extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ciandtandroidguidelines_encripytion.db";
    private static final int DATABASE_VERSION = 1;

    private DatabaseSQLCipher(final Context context) {
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

    public static SQLiteDatabase getDatabase(final Context context) {
        DatabaseSQLCipher sqlCipher = new DatabaseSQLCipher(context);
        SQLiteDatabase database = sqlCipher.getWritableDatabase(getKeyDataBase(context));
        return database;
    }


    private static String getKeyDataBase(Context context) {

        String secureKey;
        final String keyPreferences = "secureKey_sqlchipher";

        try {

            SecurityPreferences preferences = new SecurityPreferences(context);
            secureKey = preferences.getStoredString(keyPreferences);

            if (TextUtils.isEmpty(secureKey)) {
                secureKey = Base64.encodeToString(generateKey().getEncoded(), Base64.DEFAULT);
                preferences.storeString(keyPreferences, secureKey);
            }

        } catch (NoSuchAlgorithmException e) {
            throw new SecurityPreferences.SecurePreferencesException(e);
        }

        return secureKey;
    }


    private static SecretKey generateKey() throws NoSuchAlgorithmException {
        // Generate a 256-bit key
        final int outputKeyLength = 256;

        SecureRandom secureRandom = new SecureRandom();
        // Do *not* seed secureRandom! Automatically seeded from system entropy.
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(outputKeyLength, secureRandom);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

}
