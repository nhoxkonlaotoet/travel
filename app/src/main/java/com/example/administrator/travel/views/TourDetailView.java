package com.example.administrator.travel.views;

import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;

import java.util.List;

/**
 * Created by Administrator on 10/11/2018.
 */

public interface TourDetailView {
    void showDays(List<Day> lstDay);
    void showSchedules(List<Schedule> lstSchedule);
}
