package com.becxpress.whi.view;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.content.res.Configuration;
//import androidx.databinding.DataBindingUtil;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;

import com.becxpress.whi.R;
import com.becxpress.whi.ViewModel.ProductViewModel;
import com.becxpress.whi.adapter.ProductAdapter;
import com.becxpress.whi.databinding.ActivityAllCookersBinding;
import com.becxpress.whi.model.Product;
import com.becxpress.whi.storage.LoginUtils;

import static com.becxpress.whi.utils.Constant.PRODUCT;

public class AllCookersActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler,LifecycleOwner,LifecycleObserver{

    private ActivityAllCookersBinding binding;
    private ProductAdapter cookerAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_cookers);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.all_cookers));

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadCookers("pressure cooker",userID);

        setupRecyclerViews();

        getAllCookers();
    }

    private void setupRecyclerViews() {
        // Laptops
        binding.allCookersRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        binding.allCookersRecyclerView.setHasFixedSize(true);
        cookerAdapter = new ProductAdapter(this, this);
    }

    public void getAllCookers() {

        productViewModel.cookerPagedList.observe(this, new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(@Nullable PagedList<Product> products) {
                cookerAdapter.submitList(products);
            }
        });

        binding.allCookersRecyclerView.setAdapter(cookerAdapter);
        cookerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllCookersActivity.this, com.becxpress.whi.view.DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }

}
