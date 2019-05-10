package com.example.administrator.travel.views;

import android.content.Context;
import android.graphics.Bitmap;

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
    void gotoTourActivity(String tourId, String tourStartId, String owner, boolean isCompany);
    void showBtnScan();
    void hideBtnScan();
    void showLayoutLogin();
    void hideLayoutLogin();
    void hideLayoutMyTours();
    void showLayoutMyTours();
    void gotoLoginActivity();
    void notifyInvalidScanString();
    void notifyJoinTourFailure(String message);
    void showWaitDialog();
    void dismissWaitDialog();
    void showMyTours(List<Tour> tourList);
    void updateTourImage(int pos, String tourId, Bitmap image);
    void notifyGetMyTourFail(String message);
    Context getContext();
}
