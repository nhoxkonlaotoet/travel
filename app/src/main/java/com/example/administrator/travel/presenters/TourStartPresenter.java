package com.example.administrator.travel.presenters;

import com.example.administrator.travel.models.OnGetTourStartDateFinishedListener;
import com.example.administrator.travel.models.TourStartInteractor;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.views.TourStartView;

import java.util.List;

/**
 * Created by Administrator on 15/11/2018.
 */

public class TourStartPresenter implements OnGetTourStartDateFinishedListener {
    TourStartView view;
    TourStartInteractor interactor;
    public TourStartPresenter(TourStartView view)
    {
        this.view=view;
        interactor=new TourStartInteractor();

    }
    public void getTourStartDate(String tourId)
    {
        interactor.getStartDate(tourId,this);
    }
    @Override
    public void onSuccess(List<TourStartDate> listTourStartDate) {
        view.showTourStartDate(listTourStartDate,false);
    }
}
