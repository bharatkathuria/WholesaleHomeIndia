package com.example.beckart.net;

//import androidx.lifecycle.MutableLiveData;
//import androidx.paging.DataSource;
//import androidx.paging.PageKeyedDataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.example.beckart.model.Product;


public class CookwareDataSourceFactory extends DataSource.Factory{

    // Creating the mutable live database
    private MutableLiveData<PageKeyedDataSource<Integer, Product>> cookwareLiveDataSource = new MutableLiveData<>();

    public static com.example.beckart.net.ProductDataSource cookwareDataSource;

    private String category;
    private int userId;

    public CookwareDataSourceFactory(String category, int userId){
        this.category = category;
        this.userId = userId;
    }

    @Override
    public DataSource<Integer, Product> create() {
        // Getting our Data source object
        cookwareDataSource = new com.example.beckart.net.ProductDataSource(category,userId);

        // Posting the Data source to get the values
        cookwareLiveDataSource.postValue(cookwareDataSource);

        // Returning the Data source
        return cookwareDataSource;
    }


    // Getter for Product live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Product>> getCookwareLiveDataSource() {
        return cookwareLiveDataSource;
    }
}