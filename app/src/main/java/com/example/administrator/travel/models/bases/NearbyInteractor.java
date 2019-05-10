package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Admin on 4/16/2019.
 */

public interface NearbyInteractor {
    void getNearby(String type, LatLng location, String pageToken, String apiKey, Listener.OnGetNearbyFinishedListener listener);
    void getPlaceType(Listener.OnGetPlaceTypeFinishedListener listener);
}
