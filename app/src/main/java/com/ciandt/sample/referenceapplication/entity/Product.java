package com.ciandt.sample.referenceapplication.entity;

import android.database.Cursor;

import com.ciandt.sample.referenceapplication.repository.database.table.ProductTable;

import org.json.JSONObject;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Product {

    public static final String JSON_KEY_DESCRIPTION = "description";
    public static final String JSON_KEY_VALUE = "value";
    public static final String JSON_KEY_ID = "id";

    private long objectId;
    private String description;
    private double value;
    private String productId;

    public Product(final JSONObject jsonObject) {

        description = jsonObject.optString(JSON_KEY_DESCRIPTION);
        value = jsonObject.optDouble(JSON_KEY_VALUE);
        productId = jsonObject.optString(JSON_KEY_ID);
    }

    public Product(final Cursor cursor) {

        objectId = cursor.getLong(cursor.getColumnIndex(ProductTable.ID));
        description = cursor.getString(cursor.getColumnIndex(ProductTable.PRODUCT_DESCRIPTION));
        value = cursor.getDouble(cursor.getColumnIndex(ProductTable.PRODUCT_VALUE));
        productId = cursor.getString(cursor.getColumnIndex(ProductTable.PRODUCT_ID));
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
