package com.example.administrator.travel.presenters;

import com.example.administrator.travel.models.OnGetTourScheduleFinishedListener;
import com.example.administrator.travel.models.TourDetailInteractor;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.views.TourDetailView;

import java.util.List;

/**
 * Created by Administrator on 10/11/2018.
 */

public class TourDetailPresenter  implements OnGetTourScheduleFinishedListener{
    TourDetailView view;
    TourDetailInteractor interactor;

    public TourDetailPresenter( TourDetailView view){
        this.view=view;
        interactor=new TourDetailInteractor();
    }

    public void getDays(String tourId){
        interactor.getDays(tourId,this);
    }
    public void getSchedule(String tourId, String dayId)
    {
        interactor.getSchedule(tourId,dayId,this);
    }
    @Override
    public void onGetDaySuccess(List<Day> lstDay) {
        view.showDays(lstDay);
    }

    @Override
    public void onGetDayFailure() {

    }

    @Override
    public void onGetScheduleSuccess(List<Schedule> lstSchedule) {
        view.showSchedules(lstSchedule);
    }

    @Override
    public void onGetScheduleFailure() {

    }
}
