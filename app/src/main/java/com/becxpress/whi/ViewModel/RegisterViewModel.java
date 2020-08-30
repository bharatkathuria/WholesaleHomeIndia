package com.becxpress.whi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.becxpress.whi.model.RegisterApiResponse;
import com.becxpress.whi.model.User;
import com.becxpress.whi.repository.RegisterRepository;

public class RegisterViewModel extends AndroidViewModel {

    private RegisterRepository registerRepository;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        registerRepository = new RegisterRepository(application);
    }


    public LiveData<RegisterApiResponse> getRegisterResponseLiveData(User user) {
        return registerRepository.getRegisterResponseData(user);
    }
}
