package com.becxpress.whi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.becxpress.whi.model.Ordering;
import com.becxpress.whi.repository.OrderingRepository;

import okhttp3.ResponseBody;

public class OrderingViewModel extends AndroidViewModel {

    private OrderingRepository orderingRepository;

    public OrderingViewModel(@NonNull Application application) {
        super(application);
        orderingRepository = new OrderingRepository(application);
    }

    public LiveData<ResponseBody> orderProduct(Ordering ordering) {
        return orderingRepository.orderProduct(ordering);
    }

}
