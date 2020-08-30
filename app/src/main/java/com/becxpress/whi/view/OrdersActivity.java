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
import com.becxpress.whi.ViewModel.OrderViewModel;
import com.becxpress.whi.adapter.OrderAdapter;
import com.becxpress.whi.databinding.ActivityOrdersBinding;
import com.becxpress.whi.model.CustomerOrder;
import com.becxpress.whi.storage.LoginUtils;

import static com.becxpress.whi.utils.Constant.ORDER;

public class OrdersActivity extends AppCompatActivity implements OrderAdapter.OrderAdapterOnClickHandler {


    private ActivityOrdersBinding binding;
    private OrderViewModel orderViewModel;
    private OrderAdapter orderAdapter;
    private boolean track;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders);

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        track = getIntent().getBooleanExtra("track",false);

        setUpRecycleView();

        getOrders();
    }

    private void setUpRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.orderList.setLayoutManager(layoutManager);
        binding.orderList.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.orderList.addItemDecoration(dividerItemDecoration);
    }

    private void getOrders() {
        orderViewModel.getOrders(LoginUtils.getInstance(this).getUserInfo().getId(),track).observe((LifecycleOwner) this, orderApiResponse -> {
            orderAdapter = new OrderAdapter(getApplicationContext(), orderApiResponse.getOrderList(),this);
            binding.orderList.setAdapter(orderAdapter);
            orderAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(CustomerOrder order) {
        Intent intent = new Intent(OrdersActivity.this, com.becxpress.whi.view.StatusActivity.class);
        // Pass an object of order class
        intent.putExtra(ORDER, order);
        startActivity(intent);
    }
}
