package com.example.administrator.travel.models.bases;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/5/2019.
 */

public interface ScheduleInteractor {
    void getSchedules(String tourId, String dayId, Listener.OnGetSchedulesFinishedListener listener);

}
