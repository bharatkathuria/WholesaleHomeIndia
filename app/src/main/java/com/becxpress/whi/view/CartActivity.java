package com.becxpress.whi.view;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.becxpress.whi.R;
import com.becxpress.whi.ViewModel.CartViewModel;
import com.becxpress.whi.adapter.CartAdapter;
import com.becxpress.whi.databinding.ActivityCartBinding;
import com.becxpress.whi.model.Product;
import com.becxpress.whi.storage.CartUtils;
import com.becxpress.whi.storage.LoginUtils;

import java.util.List;

import static com.becxpress.whi.utils.Constant.PRODUCT;
import static com.becxpress.whi.utils.InternetUtils.isNetworkConnected;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private CartAdapter cartAdapter;
    private List<Product> productList;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.cart));

        setUpRecyclerView();

        getProductsInCart();
    }

    private void setUpRecyclerView() {
        binding.productsInCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productsInCart.setHasFixedSize(true);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
    }

    private void getProductsInCart() {
        if (isNetworkConnected(this)) {
            cartViewModel.getProductsInCart(LoginUtils.getInstance(this).getUserInfo().getId()).observe((LifecycleOwner) this, cartApiResponse -> {
                if (cartApiResponse != null) {
                    productList = cartApiResponse.getProductsInCart();
                    if (productList.size() == 0) {
                        binding.noBookmarks.setVisibility(View.VISIBLE);
                        binding.emptyCart.setVisibility(View.VISIBLE);
                        binding.bottomBar.setVisibility(View.GONE);
                    } else {
                        binding.bottomBar.setVisibility(View.VISIBLE);
                        binding.bottomBar.setVisibility(View.VISIBLE);
                        binding.productsInCart.setVisibility(View.VISIBLE);
                    }
                    cartAdapter = new CartAdapter(getApplicationContext(), productList, product -> {
                        Intent intent = new Intent(CartActivity.this, com.becxpress.whi.view.DetailsActivity.class);
                        // Pass an object of product class
                        intent.putExtra(PRODUCT, (product));
                        startActivity(intent);
                    }, this,binding);
                }
                binding.totalPrice.setText(String.valueOf(getTotal(productList)));
                binding.checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent shippingIntent = new Intent(CartActivity.this, com.becxpress.whi.view.ShippingAddressActivity.class);
                        shippingIntent.putExtra("orderData", cartAdapter.getOrderData());
                        shippingIntent.putExtra("orderAmount", Float.parseFloat(binding.totalPrice.getText().toString()));
                        shippingIntent.putExtra("quantity", cartAdapter.getOrderQuantity());
                        startActivity(shippingIntent);
                    }
                });
                binding.loadingIndicator.setVisibility(View.GONE);
                binding.productsInCart.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            });
        } else {
            binding.emptyCart.setVisibility(View.VISIBLE);
            binding.bottomBar.setVisibility(View.GONE);
            binding.loadingIndicator.setVisibility(View.GONE);
            binding.emptyCart.setText(getString(R.string.no_internet_connection));
        }
    }

    private double getTotal(List<Product> productList) {
        CartUtils cartUtils = CartUtils.getInstance(getApplicationContext());
        double total =0;
        for(Product p:productList){
            int q = cartUtils.getQuantity(String.valueOf(p.getProductId()));
            total+=(p.getProductPrice()*q);
        }
        return total;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProductsInCart();
    }



}
