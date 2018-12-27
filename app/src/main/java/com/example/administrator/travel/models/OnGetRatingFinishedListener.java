package com.example.administrator.travel.models;


import com.example.administrator.travel.models.entities.Rating;

import java.util.List;

/**
 * Created by Administrator on 25/12/2018.
 */

public interface OnGetRatingFinishedListener {
    void onGetRatingSuccess(float rating, long numberofRating);
    void onGetRatingFailure(Exception ex);
    void onGetReviewsSuccess(List<Rating> lstReview);
    void onGetReviewsFailure(Exception ex);
    void onRateSuccess();
    void onRateFailure(Exception ex);
}
