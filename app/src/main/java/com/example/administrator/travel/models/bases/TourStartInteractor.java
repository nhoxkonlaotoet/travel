package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.entities.AddTourStartDateRequest;
import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/11/2019.
 */

public interface TourStartInteractor {
    void getTourStarts(String tourId, Listener.OnGetTourStartsFinishedListener listener);

    void getTourStart(String tourStartId, Listener.OnGetTourStartFinishedListener listener);

    String getTourGuideId(String tourStartId, Context context);

    void addTourStartRequest(String tourId, AddTourStartDateRequest addTourStartDateRequest, Listener.OnAddTourStartDateRequest listener);
}
