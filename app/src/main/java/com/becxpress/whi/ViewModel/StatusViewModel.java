package com.becxpress.whi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.becxpress.whi.model.StatusApiResponse;
import com.becxpress.whi.repository.StatusRepository;

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
