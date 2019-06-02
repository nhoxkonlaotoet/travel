package com.example.administrator.travel.presenters.impls;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.administrator.travel.LocationService;
import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.SelectMyTourPresenter;
import com.example.administrator.travel.views.bases.SelectMyTourView;

import java.util.List;

/**
 * Created by Administrator on 21/12/2018.
 */

public class SelectMyTourPresenterImpl implements SelectMyTourPresenter, Listener.OnGetMyTourIdsFinishedListener,
        Listener.OnGetMyTourInfoFinishedListener, Listener.OnJoinTourFinishedListener,
        Listener.OnGetMyOwnedTourIdsFinishedListener {
    SelectMyTourView view;
    TourInteractor tourInteractor;
    UserInteractor userInteractor;
    ParticipantInteractor participantInteractor;
    CompanyInteractor companyInteractor;
    String userId;
    boolean isCompany;

    public SelectMyTourPresenterImpl(SelectMyTourView view) {
        this.view = view;
        tourInteractor = new TourInteractorImpl();
        userInteractor = new UserInteractorImpl();
        participantInteractor = new ParticipantInteractorImpl();
        companyInteractor = new CompanyInteractorImpl();
    }

    @Override
    public void onViewCreated() {
        if (userInteractor.isLogged(view.getContext())) {
            userId = userInteractor.getUserId(view.getContext());
            isCompany = companyInteractor.isCompany(userId, view.getContext());
            view.showLayoutMyTours();
            view.hideLayoutLogin();
            if (isCompany) {
                view.hideBtnScan();
                tourInteractor.getMyOwnedTours(userId, this);
            } else {
                participantInteractor.getMyToursId(userId, this);
                if (participantInteractor.isJoiningTour(userId, view.getContext())) {
                    view.hideBtnScan();
                    boolean isRunningService = false;
                    ActivityManager manager = (ActivityManager) view.getContext().getSystemService(Context.ACTIVITY_SERVICE);
                    String locationServiceName = LocationService.className();
                    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
                        if (locationServiceName.equals(service.service.getClassName())) {
                            isRunningService = true;
                            break;
                        }
                    if (!isRunningService)
                        view.getContext().startService(new Intent(view.getContext(), LocationService.class));
                    String tourId = participantInteractor.getJoiningTourId(userId, view.getContext());
                    String tourStartId = participantInteractor.getJoiningTourStartId(userId, view.getContext());
                    view.gotoTourActivity(tourId, tourStartId, null, isCompany);
                } else
                    view.showBtnScan();
            }
        } else {
            view.hideBtnScan();
            view.hideLayoutMyTours();
            view.showLayoutLogin();
        }

    }

    @Override
    public void onBtnScanQRClick() {
        view.gotoCamera();
    }

    @Override
    public void onMyTourItemClicked(String tourId, String tourStartId) {
        view.gotoTourActivity(tourId, tourStartId, null, isCompany);
    }

    @Override
    public void onMyOwnedTourItemClicked(String tourId, String owner) {
        view.gotoTourActivity(tourId, null, owner, isCompany);
    }

    @Override
    public void onBtnLoginClicked() {
        view.gotoLoginActivity();
    }

    @Override
    public void onViewResult(String tourStartId) {
        if (tourStartId.contains("/")) {
            view.notify("Mã không hợp lệ");
        } else {
            participantInteractor.joinTour(userId, false, tourStartId, this);
            view.showWaitDialog();
        }
    }

    @Override
    public void onJoinTourSuccess(String tourId, String tourStartId, String tourGuideId) {
        view.dismissWaitDialog();
        participantInteractor.getMyToursId(userId, this);
        participantInteractor.rememberTour(userId, tourStartId, tourId, tourGuideId, view.getContext());
        onViewCreated();
    }

    @Override
    public void onJoinTourFail(Exception ex) {
        view.dismissWaitDialog();
        if (ex.getMessage().equals("1"))
            view.notify("Mã không đúng");
        else
            view.notify(ex.getMessage());
    }

    @Override
    public void onGetMyTourIdsSuccess(List<String> listMyTourId) {
        int n = listMyTourId.size();
        for (int i = 0; i < n; i++)
            tourInteractor.getMyTourInfo(i, listMyTourId.get(i), this);
        view.showMyTours(n);
    }

    @Override
    public void onGetMyTourIdsFail(Exception ex) {

    }

    @Override
    public void onGetMyTourInfoSuccess(int pos, Tour tour, TourStartDate tourStartDate) {
        Log.e("onGetMyTourInfo:", tour + "");
        view.updateTourInfo(pos, tour, tourStartDate);
    }

    @Override
    public void onGetMyTourInfoFail(Exception ex) {
        view.notify(ex.getMessage());
    }

    @Override
    public void onGetMyOwnedToursSuccess(List<Tour> listMyOwnedTour) {
        view.showMyTours(listMyOwnedTour);
    }

    @Override
    public void onGetMyOwnedToursFail(Exception ex) {
        view.notify(ex.getMessage());
    }






}
