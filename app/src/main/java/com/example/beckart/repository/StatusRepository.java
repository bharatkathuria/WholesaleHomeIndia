package com.example.beckart.repository;

import android.app.Application;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.beckart.model.CartApiResponse;
import com.example.beckart.model.StatusApiResponse;
import com.example.beckart.net.RetrofitClient;

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
                Log.d(TAG, "onResponse: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

                StatusApiResponse statusApiResponse = response.body();

                if (response.body() != null) {
                    Log.d(TAG, "onResponse: bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                    mutableLiveData.setValue(statusApiResponse);
//                    Log.d(TAG, String.valueOf(response.body().getProductsInCart()));
                }
            }

            @Override
            public void onFailure(Call<StatusApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
