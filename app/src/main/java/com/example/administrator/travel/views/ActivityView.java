package com.example.administrator.travel.views;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.UserInformation;

import java.util.List;

/**
 * Created by Administrator on 01/01/2019.
 */

public interface ActivityView {
    void gotoMapActivty(String tourStartId);

    void showActivities(List<Activity> lstActivity);

    void notifyGetActivitiesFailure(Exception ex);

    void updateActivityImage(String activityId, Bitmap activityPhoto);

    void updateUserName(String userId, String userName);

    void updateUserAvatar(String userId, Bitmap avatar);

    void gotoPostActivity(String tourStartId, boolean isTourGuide);

    Context getContext();
}
