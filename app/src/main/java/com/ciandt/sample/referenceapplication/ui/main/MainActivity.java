package com.ciandt.sample.referenceapplication.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ciandt.sample.referenceapplication.R;
import com.ciandt.sample.referenceapplication.entity.Product;
import com.ciandt.sample.referenceapplication.infrastructure.OperationListener;
import com.ciandt.sample.referenceapplication.infrastructure.SecurityPreferences;
import com.ciandt.sample.referenceapplication.manager.ProductManager;
import com.ciandt.sample.referenceapplication.repository.database.DatabaseConstants;
import com.ciandt.sample.referenceapplication.ui.login.LoginActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProductManager mProductManager;

    private Button mButtonProductsAll;
    private Button mButtonProductsOnSale;
    private TextView mTextViewProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mProductManager = new ProductManager(this);

        mButtonProductsAll = (Button) findViewById(R.id.button_products_all);
        mButtonProductsOnSale = (Button) findViewById(R.id.button_products_on_sale);
        mTextViewProductList = (TextView) findViewById(R.id.textview_product_list);

        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SecurityPreferences preferences = new SecurityPreferences(this);
                preferences.removeStoredString("user");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {

        mButtonProductsAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllProductsFromDatabase();
            }
        });

        mButtonProductsOnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadProductsOnSaleFromDatabase();
            }
        });
    }

    private void loadAllProductsFromDatabase() {

        mProductManager.getAllProducts(new OperationListener<List<Product>>() {

            @Override
            public void onSuccess(List<Product> result) {

                StringBuilder strBuilder = new StringBuilder();
                for (Product product : result) {
                    strBuilder.append(product.getDescription()).append(", ");
                }

                mTextViewProductList.setVisibility(View.VISIBLE);
                mTextViewProductList.setText(strBuilder.substring(0, strBuilder.length() - 2));
            }

            @Override
            public void onError(int error) {

                mTextViewProductList.setVisibility(View.GONE);
                showErrorMessage(error);
            }

        });
    }

    private void loadProductsOnSaleFromDatabase() {

        mProductManager.getProductsOnSale(new OperationListener<List<Product>>() {

            @Override
            public void onSuccess(List<Product> result) {

                StringBuilder strBuilder = new StringBuilder();
                for (Product product : result) {
                    strBuilder.append(product.getDescription()).append(", ");
                }

                mTextViewProductList.setVisibility(View.VISIBLE);
                mTextViewProductList.setText(strBuilder.substring(0, strBuilder.length() - 2));
            }

            @Override
            public void onError(int error) {

                mTextViewProductList.setVisibility(View.GONE);
                showErrorMessage(error);
            }

        });
    }

    private void showErrorMessage(final int error) {

        int message;
        switch (error) {

            case DatabaseConstants.Error.NO_RESULT_FOUND:
                message = R.string.msg_no_result_found;
                break;

            // TODO: handle more specific errors
            default:
                message = R.string.error_generic;
        }

        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

}
