package com.becxpress.whi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.becxpress.whi.model.Cart;
import com.becxpress.whi.repository.ToCartRepository;
import com.becxpress.whi.utils.RequestCallback;

import okhttp3.ResponseBody;

public class ToCartViewModel extends AndroidViewModel {

    private ToCartRepository toCartRepository;

    public ToCartViewModel(@NonNull Application application) {
        super(application);
        toCartRepository = new ToCartRepository(application);
    }

    public LiveData<ResponseBody> addToCart(Cart cart, RequestCallback callback) {
        return toCartRepository.addToCart(cart, callback);
    }
}
