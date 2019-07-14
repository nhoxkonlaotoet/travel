package com.example.administrator.travel.models.bases;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public interface RatingInteractor {
    void checkRated(String tourId, String userId, Listener.OnCheckRatedFinishedListener listener);

    void rateTour(String tourId, Rating rating, List<Bitmap> imageList, Listener.OnRateTourFinishedListener listener);

    void getReview(String tourId, String userId, Listener.OnGetReviewTourFinishedListener listener);

    void getReviews(String tourId, Listener.OnGetReviewsTourFinishedListener listener);
    // review ID = reviewER ID
    void reactReview(String tourId, String reviewId, String userId, boolean like);

    void removeReactReview(String tourId, String reviewId, String userId);

    byte[] bitmapToBytes(Bitmap image);

    void getReviewImage(int index, String tourId, String reviewerId, Listener.OnGetReviewImageFinishedListener listener);
}
