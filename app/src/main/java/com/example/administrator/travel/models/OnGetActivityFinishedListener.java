package com.example.administrator.travel.models;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.Activity;

import java.util.List;

/**
 * Created by Administrator on 01/01/2019.
 */

public interface OnGetActivityFinishedListener {
    void onGetActivitiesSuccess(List<Activity> lstActivity, Long currentTime);
    void onGetActivitiesFailure(Exception ex);
    void onGetActivityImagesSuccess(Bitmap Bitmap, int index, String activityId);
    void onGetActivityImageFailure(Exception ex);
}
