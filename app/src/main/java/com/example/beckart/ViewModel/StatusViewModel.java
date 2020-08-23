package com.example.beckart.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.beckart.model.ProductApiResponse;
import com.example.beckart.model.StatusApiResponse;
import com.example.beckart.repository.StatusRepository;

public class StatusViewModel extends AndroidViewModel {

    private StatusRepository statusRepository;

    public StatusViewModel(@NonNull Application application) {
        super(application);
        statusRepository = new StatusRepository(application);
    }

    public LiveData<StatusApiResponse> getProductsOfOrder(String orderNumber) {
        return statusRepository.getProductsOfOrder(orderNumber);
    }
}
