package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;

import java.util.List;

/**
 * Created by Administrator on 28/11/2018.
 */

public interface OnGetTourScheduleFinishedListener {
    void onGetDaySuccess(List<Day> lstDay);
    void onGetDayFailure();
    void onGetScheduleSuccess(List<Schedule> lstSchedule);
    void onGetScheduleFailure();
}
