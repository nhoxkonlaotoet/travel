package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.os.Bundle;

import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.TourStartDateInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.TourStartInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
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
    CompanyInteractor companyInteractor;
    UserInteractor userInteractor;
    boolean isCompany;
    public TourStartPresenterImpl(TourStartView view){
        this.view = view;
        tourStartDateInteractor=new TourStartInteractorImpl();
        companyInteractor = new CompanyInteractorImpl();
        userInteractor = new UserInteractorImpl();
    }
    @Override
    public void onViewCreated(Bundle bundle) {
        String tourId = bundle.getString("tourId");
        if(userInteractor.isLogged(view.getContext())) {
            String userId=userInteractor.getUserId(view.getContext());
            isCompany = companyInteractor.isCompany(userId,view.getContext());
        }
        tourStartDateInteractor.getTourStarts(tourId,this);
    }

    @Override
    public void onTourStartItemClick(String tourStartId) {

        view.gotoBooktourActivity(tourStartId);
    }

    @Override
    public void onGetTourStartSuccess(List<TourStartDate> tourStartDateList) {
        view.showTourStartDate(tourStartDateList, isCompany);
    }

    @Override
    public void onGetTourStartFail(Exception ex) {

    }
}
