package com.example.administrator.travel.presenters.bases;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public interface TourRatingPresenter {
    void onViewCreated(Bundle bundle);
    void onBtnCollapseClicked();
    void OnRatingBarChanged(float value);
    void onBtnSendReviewClicked();
    void onGetImageResult();
    void onBtnCancelClicked();
    void onImageAddClicked();
    void onListviewReviewItemClicked(String reviewId);
    void onEditTextContentClicked();
    void onViewResult(int requestCode, int resultCode, Intent data);

}
