package com.example.administrator.travel.models.bases;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/1/2019.
 */

public interface CityInteractor {
    void getCities(Listener.OnGetCitiesFinishedListener listener);
}
