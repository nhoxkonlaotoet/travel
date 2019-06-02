package com.example.administrator.travel.models.bases;


import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.List;

/**
 * Created by Admin on 4/1/2019.
 */

public interface TourInteractor {
    void getTour(String tourId, Listener.OnGetTourFinishedListener listener);

    void getMyOwnedTours(String companyId, Listener.OnGetMyOwnedTourIdsFinishedListener listener);

    void getMyTourInfo(int pos, String tourStartId, Listener.OnGetMyTourInfoFinishedListener listener);

    void getTourImage(final int pos, String tourId, final Listener.OnGetTourImageFinishedListener listener);

    void getAboutToDepartTours(Listener.OnGetAboutToDepartToursFinishedListener listener);

    void getLikedTours(Listener.OnGetLikedToursFinishedListener listener);

    void getToursByDestination(String cityId, Listener.OnGetToursFinishedListener listener);

    void getToursByOwner(String companyId, Listener.OnGetToursFinishedListener listener);
}
