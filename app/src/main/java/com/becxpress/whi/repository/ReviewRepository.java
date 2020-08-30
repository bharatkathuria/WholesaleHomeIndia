package com.becxpress.whi.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.becxpress.whi.model.ReviewApiResponse;
import com.becxpress.whi.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepository {

    private static final String TAG = ReviewRepository.class.getSimpleName();
    private Application application;

    public ReviewRepository(Application application) {
        this.application = application;
    }

    public LiveData<ReviewApiResponse> getReviews(int productId) {
        final MutableLiveData<ReviewApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getAllReviews(productId).enqueue(new Callback<ReviewApiResponse>() {
            @Override
            public void onResponse(Call<ReviewApiResponse> call, Response<ReviewApiResponse> response) {

                ReviewApiResponse responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ReviewApiResponse> call, Throwable t) {
            }
        });
        return mutableLiveData;
    }
}

