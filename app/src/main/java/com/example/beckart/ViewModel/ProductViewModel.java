package com.example.beckart.ViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.example.beckart.model.Product;
import com.example.beckart.net.CookerDataSourceFactory;
import com.example.beckart.net.CookwareDataSourceFactory;
import com.example.beckart.net.ProductDataSource;
import com.example.beckart.net.ProductDataSourceFactory;

import static com.example.beckart.net.CookwareDataSourceFactory.cookwareDataSource;
import static com.example.beckart.net.CookerDataSourceFactory.cookerDataSource;
import static com.example.beckart.net.ProductDataSourceFactory.productDataSource;


public class ProductViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Product>> productPagedList;
    private LiveData<PageKeyedDataSource<Integer, Product>> productLiveDataSource;

    public LiveData<PagedList<Product>> cookwarePagedList;
    private LiveData<PageKeyedDataSource<Integer, Product>> cookwareLiveDataSource;

    public LiveData<PagedList<Product>> cookerPagedList;
    private LiveData<PageKeyedDataSource<Integer, Product>> cookerLiveDataSource;

    // Get PagedList configuration
    private final static PagedList.Config  pagedListConfig =
            (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(ProductDataSource.PAGE_SIZE)
                    .build();

    public void loadCutlery(String category, int userId){
        // Get our database source factory
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory(category,userId);

        // Get the live database source from database source factory
        productLiveDataSource = productDataSourceFactory.getProductLiveDataSource();

        // Build the paged list
        productPagedList = (new LivePagedListBuilder(productDataSourceFactory, pagedListConfig)).build();
    }

    public void loadCookware(String category, int userId){
        // Get our database source factory
        CookwareDataSourceFactory cookwareDataSourceFactory = new CookwareDataSourceFactory(category,userId);

        // Get the live database source from database source factory
        productLiveDataSource = cookwareDataSourceFactory.getCookwareLiveDataSource();

        // Build the paged list
        cookwarePagedList = (new LivePagedListBuilder(cookwareDataSourceFactory, pagedListConfig)).build();
    }

    public void invalidate(){
        if(productDataSource != null) productDataSource.invalidate();
        if(cookwareDataSource!= null) cookwareDataSource.invalidate();
        if(cookerDataSource!= null) cookerDataSource.invalidate();
    }

    public void loadCookers(String category, int userId) {

        CookerDataSourceFactory cookerDataSourceFactory = new CookerDataSourceFactory(category,userId);

        // Get the live database source from database source factory
        productLiveDataSource = cookerDataSourceFactory.getCookerLiveDataSource();

        // Build the paged list
        cookerPagedList = (new LivePagedListBuilder(cookerDataSourceFactory, pagedListConfig)).build();

    }
}
