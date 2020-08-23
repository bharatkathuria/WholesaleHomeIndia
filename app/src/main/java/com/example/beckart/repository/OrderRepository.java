package com.example.beckart.repository;

import android.app.Application;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.beckart.model.CustomerOrderApiResponse;
import com.example.beckart.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {

    private static final String TAG = OrderRepository.class.getSimpleName();
    private Application application;

    public OrderRepository(Application application) {
        this.application = application;
    }

    public LiveData<CustomerOrderApiResponse> getOrders(int userId,boolean track) {
        final MutableLiveData<CustomerOrderApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getOrders(userId,track).enqueue(new Callback<CustomerOrderApiResponse>() {
            @Override
            public void onResponse(Call<CustomerOrderApiResponse> call, Response<CustomerOrderApiResponse> response) {
                Log.d("onResponse", "" + response.code());

                CustomerOrderApiResponse responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<CustomerOrderApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


        return mutableLiveData;
    }


}