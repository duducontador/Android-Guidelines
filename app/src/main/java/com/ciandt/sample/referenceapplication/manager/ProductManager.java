package com.ciandt.sample.referenceapplication.manager;

import android.content.Context;
import android.os.AsyncTask;

import com.ciandt.sample.referenceapplication.business.ProductBusiness;
import com.ciandt.sample.referenceapplication.entity.Product;
import com.ciandt.sample.referenceapplication.infrastructure.OperationListener;
import com.ciandt.sample.referenceapplication.infrastructure.OperationResult;

import java.util.List;

public class ProductManager extends BaseManager {

    private final ProductBusiness mProductBusiness;

    public ProductManager(Context context) {
        super(context);
        mProductBusiness = new ProductBusiness(mContext);
    }

    public void getAllProducts(final OperationListener<List<Product>> listener) {

        AsyncTask<Void, Integer, OperationResult<List<Product>>> task =
                new AsyncTask<Void, Integer, OperationResult<List<Product>>>() {

                    @Override
                    protected OperationResult<List<Product>> doInBackground(Void... params) {

                        return mProductBusiness.getAllProducts();
                    }

                    @Override
                    protected void onPostExecute(OperationResult<List<Product>> operationResult) {

                        removeFromTaskList(this);
                        if (listener != null) {
                            int error = operationResult.getError();
                            if (error != OperationResult.NO_ERROR) {
                                listener.onError(error);
                            } else {
                                listener.onSuccess(operationResult.getResult());
                            }
                        }
                    }

                    @Override
                    protected void onCancelled() {

                        removeFromTaskList(this);
                        if (listener != null) {
                            listener.onCancel();
                        }
                    }
                };

        // Task execution
        addToTaskList(task);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void getProductsOnSale(final OperationListener<List<Product>> listener) {

        AsyncTask<Void, Integer, OperationResult<List<Product>>> task =
                new AsyncTask<Void, Integer, OperationResult<List<Product>>>() {

                    @Override
                    protected OperationResult<List<Product>> doInBackground(Void... params) {

                        return mProductBusiness.getProductsOnSale();
                    }

                    @Override
                    protected void onPostExecute(OperationResult<List<Product>> operationResult) {

                        removeFromTaskList(this);
                        if (listener != null) {
                            int error = operationResult.getError();
                            if (error != OperationResult.NO_ERROR) {
                                listener.onError(error);
                            } else {
                                listener.onSuccess(operationResult.getResult());
                            }
                        }
                    }

                    @Override
                    protected void onCancelled() {

                        removeFromTaskList(this);
                        if (listener != null) {
                            listener.onCancel();
                        }
                    }
                };

        // Task execution
        addToTaskList(task);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}