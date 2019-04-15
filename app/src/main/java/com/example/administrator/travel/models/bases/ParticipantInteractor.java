package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/14/2019.
 */

public interface ParticipantInteractor {
    void joinTour(String userId, boolean isShareLocation, String tourStartId,
                  Listener.OnJoinTourFinishedListener listener);
    void rememberTour(String tourStartId, String tourId, Context context, Listener.OnRememberTourFinishedListener listener);
    boolean isJoiningTour(Context context);
    String getJoiningTourId(Context context);
    String getJoiningTourStartId(Context context);
    void updateLocation(String tourStartId, String userId,MyLatLng location);
    void setTourFinishStream(String tourStartId, String userId, Context context, Listener.OnFinishTourFinishedListener listener);
    void removeTour(String userId, Context context);
}
