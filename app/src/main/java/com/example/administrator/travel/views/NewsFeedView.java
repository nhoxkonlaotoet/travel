package com.example.administrator.travel.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;

import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public interface NewsFeedView {
    void showCities(List<City> lstCity);

    void showTours(List<Tour> listTour);

    void updateTourImage(int pos, String tourId, Bitmap img);

    void gotoActivityTour(String tourId, String ownerId);

    void startShimmerTourAnimation();

    void stopShimmerTourAnimation();

    void showShimmerViewTour();

    void hideShimmerViewTour();

    void showRecyclerViewTour();

    void hideRecyclerViewTour();

    Context getContext();
}
