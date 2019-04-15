package com.example.administrator.travel.presenters.bases;


import android.graphics.Bitmap;
import android.os.Bundle;

import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public interface TourRatingPresenter {
    void onViewCreated(Bundle bundle);
    void onBtnCollapseClicked();
    void OnRatingBarTouched(float value);
    void onBtnSendReviewClicked(float rate, String content, List<Bitmap> lstImage);
    void onGetImageResult();
    void onBtnCancelClicked();
    void onImageAddClicked();
    void onListviewReviewItemClicked(String reviewId);
}
