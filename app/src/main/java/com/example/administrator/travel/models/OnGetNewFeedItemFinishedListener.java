package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;

import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public interface OnGetNewFeedItemFinishedListener {
    void onGetItemsSuccess(List<Tour> listTour);
    void onGetItemsFailure(Exception ex);
    void onGetCitiesSuccess(List<City> lstCity);
    void onGetCitesFailure(Exception ex);
}
