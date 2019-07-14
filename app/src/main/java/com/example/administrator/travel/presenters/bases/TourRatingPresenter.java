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

    void OnRatingBarChanged(float value);

    void onReviewItemClicked(String tourId, String reviewId);

    void onButtonShareFacebookClicked();

    void onViewResult(int requestCode, int resultCode, Intent data);

    void onButtonLikeReviewItemClicked(String reviewId, boolean pressed);

    void onButtonDislikeReviewItemClicked(String reviewId, boolean pressed);

}
