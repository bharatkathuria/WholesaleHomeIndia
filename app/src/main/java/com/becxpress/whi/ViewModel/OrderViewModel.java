package com.becxpress.whi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.becxpress.whi.model.CustomerOrderApiResponse;
import com.becxpress.whi.repository.OrderRepository;

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

