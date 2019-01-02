package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.Schedule;

import java.util.List;

/**
 * Created by Administrator on 02/01/2019.
 */

public interface OnGetScheduleFinishedListener {
    void onGetScheduleSuccess(List<Schedule> lstSchedule);
    void onGetScheduleFailure(Exception ex);
}
