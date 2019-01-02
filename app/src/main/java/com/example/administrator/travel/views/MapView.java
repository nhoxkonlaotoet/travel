package com.example.administrator.travel.views;

import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.models.entities.Route;
import com.example.administrator.travel.models.entities.Schedule;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.util.List;

/**
 * Created by Administrator on 29/12/2018.
 */

public interface MapView {
    void connectGoogleApiClient();
    void stopGoogleApiClient();
    void startLocationServices();
    void showNearbys(List<Nearby> lstNearby);
    void addActivities();
    void addSchedule(List<Schedule> lstSchedule);
    void addDirection(List<Route> lstRoute);
    void addDestination(LatLng des);
    void mapRefesh();
    void showDialog();
    void closeDialog();
}
