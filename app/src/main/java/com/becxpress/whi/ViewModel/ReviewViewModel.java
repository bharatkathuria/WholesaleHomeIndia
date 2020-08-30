package com.becxpress.whi.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.becxpress.whi.model.ReviewApiResponse;
import com.becxpress.whi.repository.ReviewRepository;

public class ReviewViewModel extends AndroidViewModel {

    private ReviewRepository reviewRepository;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository(application);
    }

    public LiveData<ReviewApiResponse> getReviews(int productId) {
        return reviewRepository.getReviews(productId);
    }
}
