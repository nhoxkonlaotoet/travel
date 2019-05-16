package com.example.administrator.travel.models.bases;

import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Admin on 5/15/2019.
 */

public interface MapInteractor {
    void getDirection(String origin, String destination, String apiKey, Listener.OnGetDirectionFinishedListener listener);
}
