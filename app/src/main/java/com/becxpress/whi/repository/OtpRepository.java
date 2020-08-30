package com.becxpress.whi.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.becxpress.whi.model.Otp;
import com.becxpress.whi.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpRepository {

    private static final String TAG = OtpRepository.class.getSimpleName();
    private Application application;

    public OtpRepository(Application application) {
        this.application = application;
    }

    public LiveData<Otp> isEmailExist(String email) {
        final MutableLiveData<Otp> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().isEmailExist(email).enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(Call<Otp> call, Response<Otp> response) {

                mutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<Otp> call, Throwable t) {
            }
        });

        return mutableLiveData;
    }

    public LiveData<Otp> getOtpCode(String email) {
        final MutableLiveData<Otp> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().getOtp(email).enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(Call<Otp> call, Response<Otp> response) {

                mutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<Otp> call, Throwable t) {
            }
        });

        return mutableLiveData;
    }

    public LiveData<Otp> isAccountExist(String email) {

        final MutableLiveData<Otp> mutableLiveData = new MutableLiveData<>();

        RetrofitClient.getInstance().getApi().isAccountExist(email).enqueue(new Callback<Otp>() {
            @Override
            public void onResponse(Call<Otp> call, Response<Otp> response) {

                mutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(Call<Otp> call, Throwable t) {
            }
        });

        return mutableLiveData;
    }

}