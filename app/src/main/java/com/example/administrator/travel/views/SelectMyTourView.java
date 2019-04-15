package com.example.administrator.travel.views;

import android.content.Context;

import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.List;

/**
 * Created by Administrator on 21/12/2018.
 */

public interface SelectMyTourView {

    void showMyTours(int n);
    void updateTourInfo(int pos, Tour tour, TourStartDate tourStartDate);
    void gotoCamera();
    void gotoTourActivity(String tourId, String tourStartId,boolean isCompany);
    void showBtnScan();
    void hideBtnScan();
    void showLayoutLogin();
    void hideLayoutLogin();
    void hideLayoutMyTours();
    void showLayoutMyTours();
    void gotoLoginActivity();
    void notifyInvalidScanString();
    void notifyJoinTourFailure(Exception ex);
    void showWaitDialog();
    void dismissWaitDialog();
    Context getContext();
}
