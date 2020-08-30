package com.becxpress.whi.net;

//import androidx.lifecycle.MutableLiveData;
//import androidx.paging.DataSource;
//import androidx.paging.PageKeyedDataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.becxpress.whi.model.Product;


public class CookerDataSourceFactory extends DataSource.Factory{

    // Creating the mutable live database
    private MutableLiveData<PageKeyedDataSource<Integer, Product>> cookerLiveDataSource = new MutableLiveData<>();

    public static com.becxpress.whi.net.ProductDataSource cookerDataSource;

    private String category;
    private int userId;

    public CookerDataSourceFactory(String category, int userId){
        this.category = category;
        this.userId = userId;
    }

    @Override
    public DataSource<Integer, Product> create() {
        // Getting our Data source object
        cookerDataSource = new com.becxpress.whi.net.ProductDataSource(category,userId);

        // Posting the Data source to get the values
        cookerLiveDataSource.postValue(cookerDataSource);

        // Returning the Data source
        return cookerDataSource;
    }


    // Getter for Product live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Product>> getCookerLiveDataSource() {
        return cookerLiveDataSource;
    }
}