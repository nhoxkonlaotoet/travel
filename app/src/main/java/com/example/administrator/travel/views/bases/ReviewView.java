package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.UserInformation;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 25/12/2018.
 */

public interface ReviewView {

    void showTourRating(HashMap<Integer,Integer> startCountMap);
    void showReviews(String tourId, List<Rating> reviewList);
    void showMyReview(Rating myRating);
    void showLayoutMyReview();
    void hideLayoutMyReview();
    void updateMyName(String name);
    void updateMyAvatar(Bitmap avatar);
    void showLayoutRateTour();
    void hideLayoutRateTour();
    void gotoReviewDetailActivity(String tourId, String reviewId);
    void enableRatingBar();
    void disableRatingBar();
    void gotoPostActivity(String tourId, float rating, int requestCode);
    void notify(String message);
    Context getContext();

    Context getAppContext();
}
