package com.example.administrator.travel.models.bases;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public interface RatingInteractor {
    void getRating(String tourId, Listener.OnGetRatingTourFinishedListener listener);
    void checkRated(String tourId, String userId, Listener.OnCheckRatedFinishedListener listener);
    void rateTour(String tourId, Rating rating, List<Bitmap> imageList, Listener.OnRateTourFinishedListener listener);
    void getReviews(String tourId, Listener.OnGetReviewsTourFinishedListener listener);
    byte[] bitmapToBytes(Bitmap image);
}
