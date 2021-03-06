package com.becxpress.whi.view;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.becxpress.whi.R;
import com.becxpress.whi.ViewModel.StatusViewModel;
import com.becxpress.whi.adapter.StatusAdapter;
import com.becxpress.whi.databinding.ActivityStatusBinding;
import com.becxpress.whi.model.CustomerOrder;
import com.becxpress.whi.model.Product;
import com.becxpress.whi.storage.LoginUtils;

import static com.becxpress.whi.utils.Constant.ORDER;
import static com.becxpress.whi.utils.Constant.PRODUCT;

public class StatusActivity extends AppCompatActivity  implements StatusAdapter.StatusAdapterOnClickHandler {

    private int productId;
    private ActivityStatusBinding binding;
    private StatusAdapter statusAdapter;
    private StatusViewModel statusViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_status);

        statusViewModel = ViewModelProviders.of(this).get(StatusViewModel.class);

        CustomerOrder order = (CustomerOrder) getIntent().getSerializableExtra(ORDER);
        binding.orderDate.setText(order.getOrderDate());
        binding.orderNumber.setText(order.getOrderNumber());
        binding.shippingAddress.setText(order.getShippingAddress());
        binding.userName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        binding.orderStatus.setText(order.getOrderStatus());
        binding.deliveryStatus.setText(order.getDeliveryStatus());
        binding.itemCount.setText(String.valueOf(order.getTotalQuantity()));
        binding.orderAmount.setText(String.valueOf(order.getOrderAmount()));
        binding.shippingAmount.setText(String.valueOf(order.getShippingAmount()));
        binding.totalAmount.setText(String.valueOf(order.getTotalAmount()));
        binding.paymentStatus.setText(order.getTransactionId());

        setUpRecyclerViews();

        getProducts(order.getOrderNumber());

    }

    private void setUpRecyclerViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.productList.setHasFixedSize(true);
        binding.productList.setLayoutManager(layoutManager);
        binding.productList.setItemAnimator(null);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.productList.addItemDecoration(dividerItemDecoration);
    }

    private void getProducts(String orderNumber) {

        statusViewModel.getProductsOfOrder(orderNumber).observe((LifecycleOwner) this, statusApiResponse -> {
            statusAdapter = new StatusAdapter(getApplicationContext(), statusApiResponse.getProducts(),this);
            binding.productList.setAdapter(statusAdapter);
            statusAdapter.notifyDataSetChanged();
        });

    }

    @Override
    public void onClick(Product currentProduct) {
        Intent intent = new Intent(StatusActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (currentProduct));
        startActivity(intent);
    }
}
