package com.example.administrator.travel.views;

import com.example.administrator.travel.models.entities.Nearby;

import java.util.List;

/**
 * Created by Administrator on 29/12/2018.
 */

public interface MapView {
    void connectGoogleApiClient();
    void stopGoogleApiClient();
    void startLocationServices();
    void showNearbys(List<Nearby> lstNearby);
    void showActivities();

}
