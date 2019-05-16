package com.example.administrator.travel.presenters.impls;

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
        Listener.OnGetMyOwnedTourIdsFinishedListener, Listener.OnGetFirstImageFinishedListener {
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
            isCompany = companyInteractor.isCompany(userId,view.getContext());
            view.showLayoutMyTours();
            view.hideLayoutLogin();
            if (isCompany) {
                view.hideBtnScan();
                tourInteractor.getMyOwnedTours(userId, this);
            } else {
                participantInteractor.getMyToursId(userId, this);
                if (participantInteractor.isJoiningTour(userId, view.getContext())) {
                    view.hideBtnScan();

                    view.getContext().startService(new Intent(view.getContext(), LocationService.class));

                    String tourId = participantInteractor.getJoiningTourId(userId, view.getContext());
                    String tourStartId = participantInteractor.getJoiningTourStartId(userId, view.getContext());
                    view.gotoTourActivity(tourId, tourStartId,null, isCompany);
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
        view.gotoTourActivity(tourId,tourStartId,null,isCompany);
    }

    @Override
    public void onMyOwnedTourItemClicked(String tourId, String owner) {
        view.gotoTourActivity(tourId,null, owner, isCompany);
    }

    @Override
    public void onBtnLoginClicked() {
        view.gotoLoginActivity();
    }

    @Override
    public void onViewResult(String tourStartId) {
        if (tourStartId.contains("/")) {
            view.notifyInvalidScanString();
        } else {
            participantInteractor.joinTour(userId, false, tourStartId, this);
            view.showWaitDialog();
        }
    }

    @Override
    public void onJoinTourSuccess(String tourId, String tourStartId) {
        view.dismissWaitDialog();
        participantInteractor.getMyToursId(userId, this);
        participantInteractor.rememberTour(userId, tourStartId, tourId, view.getContext());
        onViewCreated();
    }

    @Override
    public void onJoinTourFail(Exception ex) {
        view.dismissWaitDialog();
        if (ex.getMessage().equals("1"))
            view.notifyInvalidScanString();
        else
            view.notifyJoinTourFailure(ex.getMessage());
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
        view.notifyGetMyTourFail(ex.getMessage());
    }

    @Override
    public void onGetMyOwnedToursSuccess(List<Tour> listMyOwnedTour) {
        view.showMyTours(listMyOwnedTour);
        int n = listMyOwnedTour.size();
        for (int i = 0; i < n; i++) {
            tourInteractor.getFirstImage(i, listMyOwnedTour.get(i).id, this);
        }
    }

    @Override
    public void onGetMyOwnedToursFail(Exception ex) {
        view.notifyGetMyTourFail(ex.getMessage());
    }

    @Override
    public void onGetFirstImageSuccess(int pos, String tourId, Bitmap image) {
        view.updateTourImage(pos, tourId, image);
    }

    @Override
    public void onGetFirstImageFail(Exception ex) {

    }
}
