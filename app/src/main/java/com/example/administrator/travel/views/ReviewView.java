package com.example.administrator.travel.views;

import android.net.Uri;

import com.example.administrator.travel.models.entities.Rating;

import java.util.List;

/**
 * Created by Administrator on 25/12/2018.
 */

public interface ReviewView {

    void showDialog();
    void addImage();
    void gotoGallary();
    void closeDialog();
    void showRating(float rating, long numberofRating);
    void notifyGetRatingFailure(Exception ex);
    void showReviews(List<Rating> lstReview);
    void notifyGetReviewsFailure(Exception ex);
    void notifyRateSuccess();
    void notifyRateFailure(Exception ex);
    void showRatingBar();
    void hideRatingBar();
    void gotoReviewActivity(String reviewId);
    void enableRatingBar();
    void disableRatingBar();
}
