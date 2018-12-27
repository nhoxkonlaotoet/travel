package com.example.administrator.travel.views;

import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;

import java.util.List;

/**
 * Created by Administrator on 10/11/2018.
 */

public interface TourDetailView {
    void showInfor(Tour tour);
    void showDays(List<Day> lstDay);
    void showSchedules(List<Schedule> lstSchedule);
    void notifyFailure(Exception ex);
}
