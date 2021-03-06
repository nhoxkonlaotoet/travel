package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Administrator on 10/11/2018.
 */

public interface TourView {

    void addTabLayoutTab(String tabText);
    void showTourImages(String tourId, int numberOfImages);
    void closebyTourFinished();
    void hideImagePanel();
    void setActionbarTransparent();
    void initVpContainer(int tabCount, boolean isMyTour, boolean isCompany);
    Context getContext();
    void notifyTourFinished();
    void addTourImage(String name, Bitmap image);
    void collapseToolbarLayout();
}
