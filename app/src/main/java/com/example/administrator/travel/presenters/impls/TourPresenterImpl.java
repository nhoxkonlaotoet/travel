package com.example.administrator.travel.presenters.impls;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.TourPresenter;
import com.example.administrator.travel.views.TourView;

/**
 * Created by Admin on 4/15/2019.
 */

public class TourPresenterImpl implements TourPresenter, Listener.OnGetTourImagesFinishedListener, Listener.OnFinishTourFinishedListener {
    boolean isMytour, isCompany;
    String tourId;
    TourInteractor tourInteractor;
    TourView view;
    CompanyInteractor companyInteractor;
    ParticipantInteractor participantInteractor;
    UserInteractor userInteractor;
    int tabCount;

    public TourPresenterImpl(TourView view) {
        this.view = view;
        tourInteractor = new TourInteractorImpl();
        companyInteractor = new CompanyInteractorImpl();
        participantInteractor = new ParticipantInteractorImpl();
        userInteractor = new UserInteractorImpl();

    }

    @Override
    public void onViewCreated(Bundle bundle) {
        isMytour = bundle.getBoolean("mytour");
        tourId = bundle.getString("tourId");
        isCompany = companyInteractor.isCompany(view.getContext());


        if (view.getContext() == null)
            return;
        if (isMytour && !isCompany) {
            String tourStartId = bundle.getString("tourStartId");
            String userId = userInteractor.getUserId(view.getContext());
            participantInteractor.setTourFinishStream(tourStartId, userId, view.getContext(), this);
        }

        if (!isMytour && isCompany)
            tabCount = 3;
        else tabCount = 4;

        view.initVpContainer(tabCount, isMytour, isCompany);

        if (isMytour) {
            view.hideImagePanel();

            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_detail));
            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_activity));
            if (isCompany)
                view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_activity));
            else
                view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_nearby));
            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_rating));
        } else {
            view.setActionbarTransparent();

            tourInteractor.getTourImage(tourId, this);

            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_detail));
            if (!isCompany)
                view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_booking));
            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_contact));
            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_rating));
        }
    }

    @Override
    public void onGetTourImagesSuccess(Bitmap[] images) {
        view.showTourImages(images);
    }

    @Override
    public void onGetTourImagesFail(Exception ex) {

    }

    @Override
    public void onTourFinished() {
        view.notifyTourFinished();
        view.closebyTourFinished();
    }
}
