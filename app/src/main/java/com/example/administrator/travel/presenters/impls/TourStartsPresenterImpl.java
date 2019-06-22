package com.example.administrator.travel.presenters.impls;

import android.os.Bundle;

import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.TourStartInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.TourStartInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.TourStartPresenter;
import com.example.administrator.travel.views.bases.TourStartView;

import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public class TourStartsPresenterImpl implements TourStartPresenter, Listener.OnGetTourStartsFinishedListener {
    TourStartView view;
    TourStartInteractor tourStartInteractor;
    CompanyInteractor companyInteractor;
    UserInteractor userInteractor;
    boolean isCompany;
    private String ownerId;
    public TourStartsPresenterImpl(TourStartView view){
        this.view = view;
        tourStartInteractor =new TourStartInteractorImpl();
        companyInteractor = new CompanyInteractorImpl();
        userInteractor = new UserInteractorImpl();
    }
    @Override
    public void onViewCreated(Bundle bundle) {
        String tourId = bundle.getString("tourId");
        ownerId = bundle.getString("owner");
        if(userInteractor.isLogged()) {
            String userId=userInteractor.getUserId();
            isCompany = companyInteractor.isCompany(userId,view.getContext());
        }
        tourStartInteractor.getTourStarts(tourId,this);
    }

    @Override
    public void onTourStartItemClick(String tourStartId) {

        view.gotoBooktourActivity(tourStartId, ownerId);
    }

    @Override
    public void onGetTourStartsSuccess(List<TourStartDate> tourStartDateList) {
        view.showTourStartDate(tourStartDateList, isCompany);
    }

    @Override
    public void onGetTourStartsFail(Exception ex) {

    }
}
