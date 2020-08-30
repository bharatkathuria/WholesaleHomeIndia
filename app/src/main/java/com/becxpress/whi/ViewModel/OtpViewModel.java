package com.becxpress.whi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.becxpress.whi.model.Otp;
import com.becxpress.whi.repository.OtpRepository;

public class OtpViewModel extends AndroidViewModel {

    private OtpRepository otpRepository;

    public OtpViewModel(@NonNull Application application) {
        super(application);
        otpRepository = new OtpRepository(application);
    }

    public LiveData<Otp> getOtpCode(String email) {
        return otpRepository.getOtpCode(email);
    }

    public LiveData<Otp> isEmailExist(String userEmail) {
        return otpRepository.isEmailExist(userEmail);
    }

    public LiveData<Otp> isAccountExist(String email) {
        return otpRepository.isAccountExist(email);
    }
}
