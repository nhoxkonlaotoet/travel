package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.List;

/**
 * Created by Administrator on 10/11/2018.
 */

public interface TourDetailView {
    void showInfor(Tour tour);
    void showDays(List<Day> lstDay);
    void showSchedules(List<Schedule> lstSchedule);
    void notifyFailure(Exception ex);
    void gotoMapActivity(Intent intent);

    void gotoAddTourStartDateActivity(String tourId, int requestCode);

    void showTourStartDate(List<TourStartDate> tourStartDateList);
    Context getContext();
}
