package com.example.administrator.travel.presenters;

import com.example.administrator.travel.models.OnGetScheduleFinishedListener;
import com.example.administrator.travel.models.OnGetTourDetailFinishedListener;
import com.example.administrator.travel.models.TourDetailInteractor;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.views.TourDetailView;
import com.example.administrator.travel.views.fragments.TourDetailFragment;

import java.util.List;

/**
 * Created by Administrator on 10/11/2018.
 */

public class TourDetailPresenter  implements OnGetTourDetailFinishedListener,OnGetScheduleFinishedListener{
    TourDetailView view;
    TourDetailInteractor interactor;

    public TourDetailPresenter( TourDetailView view){
        this.view=view;
        interactor=new TourDetailInteractor();
    }
    public void onViewLoad()
    {
        interactor.getDays(((TourDetailFragment)view).tourId,this);
        interactor.getInfor(((TourDetailFragment)view).tourId,this);

    }
    public void onSelectItemSpinnerDays(String day)
    {
        interactor.getSchedule(((TourDetailFragment)view).tourId,day,this);
    }
    @Override
    public void onGetDaySuccess(List<Day> lstDay) {
        view.showDays(lstDay);
    }

    @Override
    public void onGetDayFailure(Exception ex) {
        view.notifyFailure(ex);

    }

    @Override
    public void onGetScheduleSuccess(List<Schedule> lstSchedule) {
        view.showSchedules(lstSchedule);
    }

    @Override
    public void onGetScheduleFailure(Exception ex) {
        view.notifyFailure(ex);

    }

    @Override
    public void onGetInforSuccess(Tour tour) {
        view.showInfor(tour);
    }

    @Override
    public void onGetInforFailure(Exception ex) {
        view.notifyFailure(ex);
    }
}
