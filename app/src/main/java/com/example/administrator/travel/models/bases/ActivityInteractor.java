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
    void postActivity(String tourStartId, String userId, Activity activity, List<Bitmap> listImage, Listener.OnPostActivityFinishedListener listener);
    void getActivitiyPhoto(int pos, String tourStartId, String activityId, Listener.OnGetActivityPhotosFinishedListener listener);
    byte[] bitmapToBytes(Bitmap image);
}
