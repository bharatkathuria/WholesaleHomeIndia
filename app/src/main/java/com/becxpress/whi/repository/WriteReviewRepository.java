package com.becxpress.whi.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.becxpress.whi.model.Review;
import com.becxpress.whi.net.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewRepository {

    private static final String TAG = WriteReviewRepository.class.getSimpleName();
    private Application application;

    public WriteReviewRepository(Application application) {
        this.application = application;
    }

    public LiveData<ResponseBody> writeReview(Review review) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().addReview(review).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ResponseBody responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

        return mutableLiveData;
    }
}
