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

    void getFirstImage(int pos, String tourId, Listener.OnGetFirstImageFinishedListener listener);

    void getMyOwnedTours(String companyId, Listener.OnGetMyOwnedTourIdsFinishedListener listener);

    void getMyTourInfo(int pos, String tourStartId, Listener.OnGetMyTourInfoFinishedListener listener);

    void getTourImages(String tourId, Listener.OnGetTourImagesFinishedListener listener);

    void getTourImage(int pos, String tourId, Listener.OnGetTourImageFinishedListener listener);

    void getTourImageTitle(int pos, String tourId, Listener.OnGetTourImageTitleFinishedListener listener);
}
