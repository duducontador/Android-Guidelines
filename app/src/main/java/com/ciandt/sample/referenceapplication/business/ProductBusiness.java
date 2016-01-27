package com.ciandt.sample.referenceapplication.business;

import android.content.Context;
import android.database.Cursor;

import com.ciandt.sample.referenceapplication.entity.Product;
import com.ciandt.sample.referenceapplication.infrastructure.OperationResult;
import com.ciandt.sample.referenceapplication.repository.database.DatabaseConstants;
import com.ciandt.sample.referenceapplication.repository.database.dao.ProductDAO;
import com.ciandt.sample.referenceapplication.repository.database.table.ProductTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alisson on 27/01/16.
 */
public class ProductBusiness extends BaseBusiness {

    private ProductDAO mProductDAO;

    public ProductBusiness(Context context) {
        super(context);

        mProductDAO = new ProductDAO(mContext);
    }

    public OperationResult<List<Product>> getAllProducts() {

        String[] projection = ProductTable.ALL_COLUMNS;
        String sortOrder = ProductTable.ORDER_BY;

        Cursor cursor = mProductDAO.getAllProducts(projection, sortOrder);
        List<Product> productList = getProductList(cursor);

        OperationResult<List<Product>> result = new OperationResult<>();

        if (!productList.isEmpty()) {
            result.setResult(productList);
        } else {
            result.setError(DatabaseConstants.Error.NO_RESULT_FOUND);
        }

        return result;
    }

    public OperationResult<List<Product>> getProductsOnSale() {

        String[] projection = ProductTable.ALL_COLUMNS;
        String selection = ProductTable.WHERE_VALUE_ON_SALE;
        String[] selectionArgs = new String[]{"50"};
        String sortOrder = ProductTable.ORDER_BY;

        Cursor cursor = mProductDAO.getProductsOnSale(projection, selection, selectionArgs, sortOrder);
        OperationResult<List<Product>> result = new OperationResult<>();

        List<Product> productList = getProductList(cursor);

        if (!productList.isEmpty()) {
            result.setResult(productList);
        } else {
            result.setError(DatabaseConstants.Error.NO_RESULT_FOUND);
        }

        return result;
    }

    private List<Product> getProductList(final Cursor cursor) {

        List<Product> productList = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Product product = new Product(cursor);
                productList.add(product);
            }
            cursor.close();
        }

        return productList;
    }
}
