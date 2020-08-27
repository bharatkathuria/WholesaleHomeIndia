package com.example.beckart.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.annotation.Nullable;

import com.example.beckart.R;
import com.example.beckart.ViewModel.ProductViewModel;
import com.example.beckart.adapter.ProductAdapter;
import com.example.beckart.databinding.ActivityAllCookwareBinding;
import com.example.beckart.model.Product;
import com.example.beckart.storage.LoginUtils;

import static com.example.beckart.utils.Constant.PRODUCT;

public class AllCookwareActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler,LifecycleOwner,LifecycleObserver{

    private ActivityAllCookwareBinding binding;
    private ProductAdapter cookwareAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_cookware);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.all_cookware));

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadCookware("cookware",userID);

        setupRecyclerViews();

        getAllCookware();
    }

    private void setupRecyclerViews() {
        // cookware
        binding.allCookwareRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        binding.allCookwareRecyclerView.setHasFixedSize(true);
        cookwareAdapter = new ProductAdapter(this, this);
    }

    public void getAllCookware() {

        productViewModel.cookwarePagedList.observe(this, new Observer<PagedList<Product>>() {
            @Override
            public void onChanged(@Nullable PagedList<Product> products) {
                cookwareAdapter.submitList(products);
            }
        });

        binding.allCookwareRecyclerView.setAdapter(cookwareAdapter);
        cookwareAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllCookwareActivity.this, com.example.beckart.view.DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }

}
