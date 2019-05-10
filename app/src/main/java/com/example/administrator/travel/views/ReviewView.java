package com.example.administrator.travel.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.UserInformation;

import java.util.List;

/**
 * Created by Administrator on 25/12/2018.
 */

public interface ReviewView {

    void showDialog();
    void closeDialog();
    void showRating(float rating, long numberofRating);
    void notifyGetRatingFailure(Exception ex);
    void showReviews(List<Rating> lstReview);
    void notifyGetReviewsFailure(Exception ex);
    void updateUserName(String username, int pos);
    void updateUserAvatar(Bitmap avatar, int pos);
    void notifyRateSuccess();
    void notifyRateFailure(Exception ex);
    void showRatingBar();
    void hideRatingBar();
    void gotoReviewDetailActivity(Intent intent);
    void enableRatingBar();
    void disableRatingBar();
    void gotoPostActivity(Intent intent, int requestCode);
    Context getContext();
}
