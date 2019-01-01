package com.example.administrator.travel.models;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.UserInformation;

/**
 * Created by Administrator on 01/01/2019.
 */

public interface OnGetUserInforFinishedListener {
    void onGetUserInforSuccess(UserInformation info);
    void onGetUserInforFailure(Exception ex);
    void onCheckTourGuideTrue();
    void onCheckTourGuideFalse(String tourGuideId);
    void onCheckTourGuideFailure(Exception ex);
}
