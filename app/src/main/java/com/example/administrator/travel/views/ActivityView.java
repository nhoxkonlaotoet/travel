package com.example.administrator.travel.views;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.UserInformation;

import java.util.List;

/**
 * Created by Administrator on 01/01/2019.
 */

public interface ActivityView {
    void showActivities(List<Activity> lstActivity, Long currentTime);
    void notifyGetActivitiesFailure(Exception ex);
    void addUserInfor(UserInformation info);
    void addImage(Bitmap Bitmap, int index, String activityId);
    void gotoPostActiivty(String tourStartId, boolean isTourGuide);
}
