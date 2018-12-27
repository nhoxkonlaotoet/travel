package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;

import java.util.List;

/**
 * Created by Administrator on 28/11/2018.
 */

public interface OnGetTourDetailFinishedListener {
    void onGetDaySuccess(List<Day> lstDay);
    void onGetDayFailure(Exception ex);
    void onGetScheduleSuccess(List<Schedule> lstSchedule);
    void onGetScheduleFailure(Exception ex);
    void onGetInforSuccess(Tour tour);
    void onGetInforFailure(Exception ex);
}
