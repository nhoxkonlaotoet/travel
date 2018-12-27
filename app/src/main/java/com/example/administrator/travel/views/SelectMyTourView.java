package com.example.administrator.travel.views;

import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.List;

/**
 * Created by Administrator on 21/12/2018.
 */

public interface SelectMyTourView {

    void showMyTours(List<Tour> lstTour, List<TourStartDate> lstTourStart);
    void gotoCamera();
    void gotoTourActivity(String tourId, String tourStartId);
    void loadMyTours();
    void showLayoutLogin();
    void hideLayoutLogin();
    void hideLayoutMyTours();
    void showLayoutMyTours();
    void gotoLoginActivity();
}
