package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.travel.models.bases.TourStartDateInteractor;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.TourStartInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.TourStartPresenter;
import com.example.administrator.travel.views.TourStartView;
import com.example.administrator.travel.views.activities.BookTourActivity;

import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public class TourStartPresenterImpl implements TourStartPresenter, Listener.OnGetTourStartFinishedListener {
    TourStartView view;
    TourStartDateInteractor tourStartDateInteractor;
    public TourStartPresenterImpl(TourStartView view){
        this.view = view;
        tourStartDateInteractor=new TourStartInteractorImpl();
    }
    @Override
    public void onViewCreated(Bundle bundle) {
        String tourId = bundle.getString("tourId");
        tourStartDateInteractor.getTourStarts(tourId,this);
    }

    @Override
    public void onTourStartItemClick(String tourStartId) {
        Intent intent = new Intent(view.getContext(),BookTourActivity.class);
        intent.putExtra("tourStartId",tourStartId);
        view.gotoBooktourActivity(intent);
    }

    @Override
    public void onGetTourStartSuccess(List<TourStartDate> tourStartDateList) {
        view.showTourStartDate(tourStartDateList);
    }

    @Override
    public void onGetTourStartFail(Exception ex) {

    }
}
