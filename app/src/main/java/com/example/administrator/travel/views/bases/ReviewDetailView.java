package com.example.administrator.travel.views.bases;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.place.detail.Review;

public interface ReviewDetailView {
    void showReviewerName(String name);

    void showReviewerAvatar(Bitmap avatar);

    void showReview(String tourId, Rating review);

    void notify(String message);
}
