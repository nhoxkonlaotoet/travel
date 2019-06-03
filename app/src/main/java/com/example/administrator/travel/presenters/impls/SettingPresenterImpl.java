package com.example.administrator.travel.presenters.impls;

import android.app.Fragment;
import android.widget.Toast;

import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.SettingPresenter;
import com.example.administrator.travel.views.bases.SettingView;

/**
 * Created by Admin on 4/26/2019.
 */

public class SettingPresenterImpl implements SettingPresenter, Listener.OnCheckShareLocationFinishedListener, Listener.OnSetShareLocationFinishedListener {
    SettingView view;
    UserInteractor userInteractor;
    ParticipantInteractor participantInteractor;
    CompanyInteractor companyInteractor;
    boolean shareLocation;

    String userId, tourStartId;

    public SettingPresenterImpl(SettingView view) {
        this.view = view;
        userInteractor = new UserInteractorImpl();
        participantInteractor = new ParticipantInteractorImpl();
        companyInteractor = new CompanyInteractorImpl();
    }

    @Override
    public void onViewStarted() {
        Toast.makeText(view.getContext(), "logged:"+userInteractor.isLogged(view.getContext())+" "+userInteractor.getUserId(view.getContext()), Toast.LENGTH_SHORT).show();

        if (userInteractor.isLogged(view.getContext())) {
            view.hideBtnLogin();
            view.showBtnLogout();
            userId = userInteractor.getUserId(view.getContext());
            if (!companyInteractor.isCompany(userId, view.getContext())) {
                tourStartId = participantInteractor.getJoiningTourStartId(userId, view.getContext());
                participantInteractor.checkShareLoction(userId, tourStartId, this);
                view.showLayoutShareLocation();
                view.disableSwitchShareLocation();
            }
            else view.hideLayoutShareLocation();
        } else {
            view.hideBtnLogout();
            view.showBtnLogin();
            view.hideLayoutShareLocation();
        }

    }

    @Override
    public void onShareLocationSwitchClicked() {
        Toast.makeText(view.getContext(), userId + " " + tourStartId, Toast.LENGTH_SHORT).show();
        if (!userId.equals("") && !tourStartId.equals("")) {
            // share = false -> click switch -> save !share = true
            // save success share=true
            participantInteractor.setShareLocation(userId, tourStartId, !shareLocation, view.getContext(), this);
            view.disableSwitchShareLocation();
        }
    }

    @Override
    public void onButtonLoginClicked() {
        view.gotoLoginActivity();
    }

    @Override
    public void onButtonLogoutClicked() {
        userInteractor.logout(view.getContext());
        ((Fragment) view).onStart();
    }

    @Override
    public void onCheckLocationSuccess(boolean isShareLocation) {
        shareLocation = isShareLocation;
        view.setSwitchShareLocationState(isShareLocation);
        view.enableSwitchShareLocation();
    }

    @Override
    public void onCheckLocationFail(Exception ex) {
    }

    @Override
    public void onSetShareLocationSuccess(boolean isShareLocation) {
        // update share
        shareLocation = isShareLocation;
        view.setSwitchShareLocationState(isShareLocation);
        view.enableSwitchShareLocation();

    }

    @Override
    public void onSetShareLocationFail(Exception ex) {
        view.notifySetShareLocationFail(ex.getMessage());
        view.setSwitchShareLocationState(shareLocation);
        view.enableSwitchShareLocation();
    }
}