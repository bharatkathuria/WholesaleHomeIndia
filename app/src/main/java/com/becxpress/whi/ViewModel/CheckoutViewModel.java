package com.becxpress.whi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.becxpress.whi.model.Order;
import com.becxpress.whi.repository.CheckoutRepository;

import okhttp3.ResponseBody;

public class CheckoutViewModel  extends AndroidViewModel {

    private CheckoutRepository checkoutRepository;

    public CheckoutViewModel(@NonNull Application application) {
        super(application);
        checkoutRepository = new CheckoutRepository(application);
    }

    public LiveData<ResponseBody> addOrder(Order order) {
        return checkoutRepository.addOrder(order);
    }

    public LiveData<ResponseBody> updatePaymentInfo(String orderId, String paymentId, String email, float amount) {
            return checkoutRepository.updatePaymentInfo(orderId,paymentId,email,amount);
    }
}
