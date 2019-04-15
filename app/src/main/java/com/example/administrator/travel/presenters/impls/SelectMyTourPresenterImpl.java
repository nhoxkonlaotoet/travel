package com.example.administrator.travel.presenters.impls;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;

import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.TimeInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.TimeInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.SelectMyTourPresenter;
import com.example.administrator.travel.views.SelectMyTourView;
import com.example.administrator.travel.views.activities.LoginActivity;

import java.util.List;

/**
 * Created by Administrator on 21/12/2018.
 */

public class SelectMyTourPresenterImpl implements SelectMyTourPresenter, Listener.OnGetMyTourIdsFinishedListener,
        Listener.OnGetMyTourInfoFinishedListener, Listener.OnJoinTourFinishedListener, Listener.OnRememberTourFinishedListener {
    SelectMyTourView view;
    TourInteractor tourInteractor;
    UserInteractor userInteractor;
    ParticipantInteractor participantInteractor;
    String userId;
    boolean isCompany = false;

    public SelectMyTourPresenterImpl(SelectMyTourView view) {
        this.view = view;
        tourInteractor = new TourInteractorImpl();
        userInteractor = new UserInteractorImpl();
        participantInteractor=new ParticipantInteractorImpl();
    }

    @Override
    public void onViewCreated() {
        userId = userInteractor.getUserId(view.getContext());
        if (userId.equals("none")) {
            view.hideBtnScan();
            view.hideLayoutMyTours();
            view.showLayoutLogin();
        } else {
            view.showLayoutMyTours();
            view.hideLayoutLogin();
            isCompany = userInteractor.isCompany(view.getContext());
            tourInteractor.getMyToursId(userId, this);
            if (participantInteractor.isJoiningTour(view.getContext())) {
                view.hideBtnScan();
                String tourId = participantInteractor.getJoiningTourId(view.getContext());
                String tourStartId = participantInteractor.getJoiningTourStartId(view.getContext());
                view.gotoTourActivity(tourId, tourStartId, isCompany);
            } else
                view.showBtnScan();
        }

    }
    @Override
    public void onBtnScanQRClick() {
        view.gotoCamera();
    }
    @Override
    public void onItemTourClicked(View item) {
        //tag= tourId+" "+tourStartId;
        String[] s = item.getTag().toString().split(" ");
        view.gotoTourActivity(s[0], s[1], isCompany);
    }
    @Override
    public void onBtnLoginClicked() {
        view.gotoLoginActivity();
    }
    @Override
    public void onViewResult(String resultString) {
        if (resultString.contains("/") || !resultString.contains("+")) {
            view.notifyInvalidScanString();
        } else {
            String[] s = resultString.split("\\+");
            String tourId = s[0];
            String tourStartId = s[1];
            participantInteractor.joinTour(userId, false, tourStartId, this);
            view.showWaitDialog();
        }
    }

    @Override
    public void onJoinTourSuccess(String tourId, String tourStartId) {
        view.dismissWaitDialog();
        tourInteractor.getMyToursId(userId, this);
        participantInteractor.rememberTour(tourStartId, tourId, view.getContext(), this);
        onViewCreated();
    }

    @Override
    public void onJoinTourFail(Exception ex) {
        view.dismissWaitDialog();
        if (ex.getMessage().equals("1"))
            view.notifyInvalidScanString();
        else
            view.notifyJoinTourFailure(ex);
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

    }

    @Override
    public void onRememberTourSuccess() {

    }

    @Override
    public void onRememberTourFail(Exception ex) {

    }
}
