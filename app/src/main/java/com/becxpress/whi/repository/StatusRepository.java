package com.becxpress.whi.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.becxpress.whi.model.StatusApiResponse;
import com.becxpress.whi.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusRepository {

    private static final String TAG = CartRepository.class.getSimpleName();
    private Application application;

    public StatusRepository(Application application) {
        this.application = application;
    }

    public LiveData<StatusApiResponse> getProductsOfOrder(String orderNumber) {
        final MutableLiveData<StatusApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getProductsOfOrder(orderNumber).enqueue(new Callback<StatusApiResponse>() {
            @Override
            public void onResponse(Call<StatusApiResponse> call, Response<StatusApiResponse> response) {

                StatusApiResponse statusApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(statusApiResponse);
                }
            }

            @Override
            public void onFailure(Call<StatusApiResponse> call, Throwable t) {
            }
        });
        return mutableLiveData;
    }
}
