package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.DayInteractor;
import com.example.administrator.travel.models.bases.ScheduleInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.bases.TourStartInteractor;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.DayInteractorImpl;
import com.example.administrator.travel.models.impls.ScheduleInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.impls.TourStartInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.TourDetailPresenter;
import com.example.administrator.travel.views.bases.TourDetailView;
import com.example.administrator.travel.views.activities.MapsActivity;

import java.util.List;

/**
 * Created by Administrator on 10/11/2018.
 */

public class TourDetailPresenterImpl implements TourDetailPresenter,
        Listener.OnGetDaysFinishedListener, Listener.OnGetSchedulesFinishedListener, Listener.OnGetTourFinishedListener, Listener.OnGetTourStartsFinishedListener {
    final static int ADD_TOUR_START_CODE = 28;

    TourDetailView view;
    TourInteractor tourInteractor;
    DayInteractor dayInteractor;
    ScheduleInteractor scheduleInteractor;
    TourStartInteractor tourStartInteractor;
    String dayId, tourId;
    boolean isMyTour;

    public TourDetailPresenterImpl(TourDetailView view) {
        this.view = view;
        tourInteractor = new TourInteractorImpl();
        dayInteractor = new DayInteractorImpl();
        scheduleInteractor = new ScheduleInteractorImpl();
        tourStartInteractor = new TourStartInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        tourId = bundle.getString("tourId");
        isMyTour = bundle.getBoolean("mytour");
        dayInteractor.getDays(tourId, this);
        tourInteractor.getTour(tourId, this);
        tourStartInteractor.getTourStarts(tourId, this);


    }

    @Override
    public void onSelectItemSpinnerDays(String day) {
        dayId = day;
        scheduleInteractor.getSchedules(tourId, day, this);
    }

    @Override
    public void onScheduleItemClicked(String scheduleId) {
        Intent intent = new Intent(view.getContext(), MapsActivity.class);
        intent.putExtra("tourId", tourId);
        intent.putExtra("dayId", dayId);
        intent.putExtra("scheduleId", scheduleId);
        intent.putExtra("openFrom", view.getContext().getResources().getString(R.string.open_from_schedule));
        intent.putExtra("mytour", isMyTour);
        view.gotoMapActivity(intent);
    }

    @Override
    public void onButtonAddTourStartClick() {
        view.gotoAddTourStartDateActivity(tourId, ADD_TOUR_START_CODE);
    }

    @Override
    public void onGetSchedulesSuccess(List<Schedule> lstSchedule) {
        view.showSchedules(lstSchedule);
    }

    @Override
    public void onGetSchedulesFail(Exception ex) {

    }

    @Override
    public void onGetDaysSuccess(List<Day> dayList) {
        view.showDays(dayList);
    }

    @Override
    public void onGetDaysFail(Exception ex) {

    }

    @Override
    public void onGetTourSuccess(Tour tour) {
        view.showInfor(tour);
    }

    @Override
    public void onGetTourFail(Exception ex) {

    }

    @Override
    public void onGetTourStartsSuccess(List<TourStartDate> tourStartDateList) {
        view.showTourStartDate(tourStartDateList);
    }

    @Override
    public void onGetTourStartsFail(Exception ex) {

    }
}
