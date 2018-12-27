package com.example.administrator.travel.views;

import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Administrator on 10/11/2018.
 */

public interface TourView {

    void addTab(Boolean isMyTour);
    void connectGoogleApiClient();
    void transmitLocationToFragment(Location location);
    void startLocationServices();
    void notifyConnectFailed(ConnectionResult connectionResult);
    void stopGoogleApiClient();
    void showImages(Bitmap[] images, Integer numberofImages);
}
