package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.OnGetMyToursFinishedListener;
import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/1/2019.
 */

public interface TourInteractor {
    void getTours(Listener.OnGetToursFinishedListener listener);

    void getTours(String origin, Listener.OnGetToursFinishedListener listener);

    void getTours(String origin, String destination, Listener.OnGetToursFinishedListener listener);

    void getTour(String tourId, Listener.OnGetTourFinishedListener listener);

    void updateFirstImage(String tourId, Listener.OnGetFirstImageFinishedListener listener);

    void getMyToursId(String userId, Listener.OnGetMyTourIdsFinishedListener listener);

    void getMyTourInfo(int pos, String tourStartId, final Listener.OnGetMyTourInfoFinishedListener listener);

    void getTourImage(final String tourId, final Listener.OnGetTourImagesFinishedListener listener);
}
