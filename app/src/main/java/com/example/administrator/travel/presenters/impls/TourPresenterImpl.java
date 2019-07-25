package com.example.administrator.travel.presenters.impls;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.travel.LocationService;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.ExternalStorageInteractor;
import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.ExternalStorageInteractorImpl;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.TourPresenter;
import com.example.administrator.travel.views.bases.TourView;

/**
 * Created by Admin on 4/15/2019.
 */

public class TourPresenterImpl implements TourPresenter, Listener.OnFinishTourFinishedListener,
        Listener.OnGetTourFinishedListener, Listener.OnGetTourImageFinishedListener {
    boolean onMyTour, isCompany, isOwned;
    private TourView view;
    private String tourId;
    private TourInteractor tourInteractor;
    private CompanyInteractor companyInteractor;
    private ParticipantInteractor participantInteractor;
    private UserInteractor userInteractor;
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
        onMyTour = bundle.getBoolean("mytour");
        String owner = bundle.getString("owner");
        tourId = bundle.getString("tourId");
        if (userInteractor.isLogged()) {
            String userId = userInteractor.getUserId();
            isCompany = companyInteractor.isCompany(userId, view.getContext());
            if (userId.equals(owner))
                isOwned = true;
        }
        Toast.makeText(view.getContext(), isCompany + "", Toast.LENGTH_SHORT).show();
        if (view.getContext() == null)
            return;
        if (onMyTour && !isCompany) {
            String tourStartId = bundle.getString("tourStartId");
            participantInteractor.setTourFinishStream(tourStartId, view.getContext(), this);
        }
        tabCount = 4;
        view.initVpContainer(tabCount, onMyTour, isCompany);

        if (onMyTour) {
            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_detail));
            if (isCompany) {
                view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_start_date));
                view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_contact));
                view.setActionbarTransparent();
                //     tourInteractor.getTourImages(tourId, this);
            } else {
                view.collapseToolbarLayout();
                view.hideImagePanel();
                view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_activity));
                view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_nearby));
            }
            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_rating));
        } else {
            view.setActionbarTransparent();
            tourInteractor.getTour(tourId, this);
            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_detail));
            if (!isCompany || (isCompany && isOwned))
                view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_start_date));
            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_contact));
            view.addTabLayoutTab(view.getContext().getResources().getString(R.string.title_tour_rating));
        }
    }


    @Override
    public void onTourFinished() {
        if (userInteractor.isLogged()) {
            String userId = userInteractor.getUserId();
            boolean isJoining = participantInteractor.isJoiningTour(userId, view.getContext());
            if (isJoining) {
                participantInteractor.removeparticipatingTour(userId, view.getContext());
                view.notifyTourFinished();
                view.getContext().stopService(new Intent(view.getContext(), LocationService.class));
                view.closebyTourFinished();
            }
        }
    }

    @Override
    public void onGetTourSuccess(Tour tour) {
        view.showTourImages(tour.id, tour.numberofImages);
        for (int i = 0; i < tour.numberofImages; i++) {
            tourInteractor.getTourImage(i, tourId, this);
        }
    }

    @Override
    public void onGetTourFail(Exception ex) {

    }

    @Override
    public void onGetTourImageSuccess(int pos, String tourId, Bitmap tourImage) {
        view.addTourImage(tourId + pos, tourImage);
    }

}
