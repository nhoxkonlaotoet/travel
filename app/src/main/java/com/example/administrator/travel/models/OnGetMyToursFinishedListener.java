package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.List;

/**
 * Created by Administrator on 21/12/2018.
 */

public interface OnGetMyToursFinishedListener {
     void onJoinTourSuccess();
     void onJoinTourFailure(Exception ex);
     void isJoiningTourTrue(String tourId, String tourStartId);
     void isJoiningTourFalse();
     void onGetMyToursSuccess(List<Tour> lstTour, List<TourStartDate> lstTourStart);
     void onGetMyToursFailure(Exception ex);
     void onRememberTourSuccess(String tourId, String tourStartId);
     void onRememberTourFailure(Exception ex);
}
