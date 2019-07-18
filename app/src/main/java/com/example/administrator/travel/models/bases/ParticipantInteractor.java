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

    void rememberTour(String userId, String tourStartId, String tourId, String tourGuideId, Context context);

    String getTourGuide(String tourStartId, Context context);

    boolean isJoiningTour(String userId, Context context);

    void checkJoiningTour(String userId, Listener.OnCheckJoiningTourFinishedListener listener);

    String getJoiningTourId(String UserId, Context context);

    String getJoiningTourStartId(String userId, Context context);

    void updateLocation(String tourStartId, String userId, MyLatLng location);

    void setTourFinishStream(String tourStartId, Context context, Listener.OnFinishTourFinishedListener listener);

    void removeparticipatingTour(String userId, Context context);

    boolean isShareLocation(String userId, Context context);

    void checkShareLoction(String userId, String tourStartId, Listener.OnCheckShareLocationFinishedListener listener);

    void setShareLocation(String userId, String tourStartId, boolean shareLocation, Context context, Listener.OnSetShareLocationFinishedListener listener);

    void getMyToursId(String userId, Listener.OnGetMyTourIdsFinishedListener listener);

    void setStreamPeopleLocationChange(String tourStartId, Listener.OnGetPeopleLocationFinishedListener listener);

    void checkJoinedTour(String userId, String tourId, Listener.OnCheckJoinedTourFinishedListener listener);

    void finishTour(String tourStartId);
}
