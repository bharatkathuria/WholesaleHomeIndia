package com.becxpress.whi.ViewModel;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.arch.lifecycle.LiveData;
import com.becxpress.whi.model.Review;
import com.becxpress.whi.repository.WriteReviewRepository;

import okhttp3.ResponseBody;

public class WriteReviewViewModel extends AndroidViewModel {

    private WriteReviewRepository writeReviewRepository;

    public WriteReviewViewModel(@NonNull Application application) {
        super(application);
        writeReviewRepository = new WriteReviewRepository(application);
    }

    public LiveData<ResponseBody> writeReview(Review review) {
        return writeReviewRepository.writeReview(review);
    }
}
