package com.becxpress.whi.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.becxpress.whi.model.ProductApiResponse;
import com.becxpress.whi.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {

    private static final String TAG = SearchRepository.class.getSimpleName();
    private Application application;

    public SearchRepository(Application application) {
        this.application = application;
    }

    public LiveData<ProductApiResponse> getResponseDataBySearch(String keyword, int userId) {
        final MutableLiveData<ProductApiResponse> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance()
                .getApi().searchForProduct(keyword, userId)
                .enqueue(new Callback<ProductApiResponse>() {
                    @Override
                    public void onResponse(Call<ProductApiResponse> call, Response<ProductApiResponse> response) {

                        ProductApiResponse productApiResponse = response.body();

                        if (response.body() != null) {
                            mutableLiveData.setValue(productApiResponse);
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductApiResponse> call, Throwable t) {
                    }
                });
        return mutableLiveData;
    }
}
