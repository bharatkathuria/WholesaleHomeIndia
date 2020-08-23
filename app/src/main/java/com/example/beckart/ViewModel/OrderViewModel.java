package com.example.beckart.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.beckart.model.CustomerOrderApiResponse;
import com.example.beckart.repository.OrderRepository;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository orderRepository;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository(application);
    }

    public LiveData<CustomerOrderApiResponse> getOrders(int userId, boolean track) {

        return orderRepository.getOrders(userId,track);
    }
}

