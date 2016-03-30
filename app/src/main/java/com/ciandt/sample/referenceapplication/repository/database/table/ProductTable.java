package com.ciandt.sample.referenceapplication.repository.database.table;

/**
 * This interface represents the columns and SQLite statements for the
 * ProductTable. This table is represented in the sqlite database as Location column.
 */
@SuppressWarnings({"unused"})
public interface ProductTable {

    String TABLE_NAME = "product";
    String TABLE_NAME_ALIAS = "p";

    String ID = "_id";
    String PRODUCT_DESCRIPTION = "product_description";
    String PRODUCT_VALUE = "product_value";
    String PRODUCT_ID = "product_id";

    String[] ALL_COLUMNS = new String[]{
            ID,
            PRODUCT_DESCRIPTION,
            PRODUCT_VALUE,
            PRODUCT_ID};

    String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ","
            + PRODUCT_DESCRIPTION + " TEXT" + ","
            + PRODUCT_VALUE + " REAL" + ","
            + PRODUCT_ID + " TEXT"
            + ")";

    String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " ("
            + PRODUCT_DESCRIPTION + ","
            + PRODUCT_VALUE + ","
            + PRODUCT_ID +
            ") VALUES (?, ?, ?)";

    String WHERE_ID_EQUALS = ID + "=?";

    String WHERE_PRODUCT_ID_EQUALS = PRODUCT_ID + "=?";

    String WHERE_VALUE_ON_SALE = PRODUCT_VALUE + "<=?";

    String ORDER_BY = PRODUCT_DESCRIPTION + " ASC";

    String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;
}