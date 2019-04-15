package com.example.administrator.travel.views;

import android.graphics.Bitmap;
import android.view.View;

import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;

import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public interface NewFeedView {
    void showCities(List<City> lstCity);
    void showTours(List<Tour> listTour);
    void updateTourImage(String tourId, Bitmap img);
    void gogotActivityTour(String tourId, String ownerId);
}
