package com.becxpress.whi.repository;

import android.app.Application;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.becxpress.whi.model.CartApiResponse;
import com.becxpress.whi.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {

    private static final String TAG = CartRepository.class.getSimpleName();
    private Application application;

    public CartRepository(Application application) {
        this.application = application;
    }

    public LiveData<CartApiResponse> getProductsInCart(int userId) {
        final MutableLiveData<CartApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getProductsInCart(userId).enqueue(new Callback<CartApiResponse>() {
            @Override
            public void onResponse(Call<CartApiResponse> call, Response<CartApiResponse> response) {

                CartApiResponse cartApiResponse = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(cartApiResponse);
                }
            }

            @Override
            public void onFailure(Call<CartApiResponse> call, Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
