package com.ciandt.sample.referenceapplication.repository.database.dao;

import android.content.Context;
import android.database.Cursor;

import com.ciandt.sample.referenceapplication.repository.database.AndroidGuidelinesContentProvider;
import com.ciandt.sample.referenceapplication.repository.database.table.ProductTable;

@SuppressWarnings({"ConstantConditions", "JavaDoc", "unused"})
public class ProductDAO extends BaseDAO {

    public ProductDAO(final Context context) {
        super(context);
    }

    /**
     * Get Product by Id
     * select {@param projection} from ProductTable where id = ?
     *
     * @param projection
     * @return cursor
     */
    public Cursor getProductById(final String[] projection, final String productId) {

        return query(AndroidGuidelinesContentProvider.PRODUCT_CONTENT_URI, projection, ProductTable.WHERE_PRODUCT_ID_EQUALS, new String[]{productId}, null);
    }

    /**
     * Get all Products
     * select {@param projection} from ProductTable order by {@param sortOrder}
     *
     * @param projection
     * @param sortOrder
     * @return cursor
     */
    public Cursor getAllProducts(final String[] projection, final String sortOrder) {

        return query(AndroidGuidelinesContentProvider.PRODUCT_CONTENT_URI, projection, null, null, sortOrder);
    }

    /**
     * Get Products by a condition
     * select {@param projection} from ProductTable where {@param selection}{@param selectionArgs} order by {@param sortOrder}
     *
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return cursor
     */
    public Cursor getProductsOnSale(final String[] projection, final String selection, final String[] selectionArgs,
                                         final String sortOrder) {

        return query(AndroidGuidelinesContentProvider.PRODUCT_CONTENT_URI, projection, selection, selectionArgs, sortOrder);
    }

    /** in case of customized query {@link AndroidGuidelinesContentProvider.query} */
    /*public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs,
                        final String sortOrder) {

        return query(uri, projection, selection, selectionArgs, sortOrder);
    }*/

}
