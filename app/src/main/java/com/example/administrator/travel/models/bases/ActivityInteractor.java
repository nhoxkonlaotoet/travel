package com.example.administrator.travel.models.bases;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.List;

/**
 * Created by Admin on 4/15/2019.
 */

public interface ActivityInteractor {
    void getActivities(String tourStartId, Listener.OnGetActivitiesFinishedListener listener);

    void postActivity(Activity activity, Listener.OnPostActivityFinishedListener listener);

    void likeActivity(String activityId, String userId, Boolean like);
}
