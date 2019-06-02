package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public interface NewsFeedView {
    void showCities(List<City> lstCity);

    void showAboutToDepartTours(List<Tour> listTour, HashMap<String,TourStartDate> tourStartMap);

    void showLikedTours(List<Tour> listTour, HashMap<String,Double> ratingMap);

    void gotoActivityTour(String tourId, String ownerId);

    void notify(String message);

    void hideLayoutCities();

    void hideLayoutAboutToDepartTours();

    void hideLayoutLikedTours();

    Context getContext();
}
