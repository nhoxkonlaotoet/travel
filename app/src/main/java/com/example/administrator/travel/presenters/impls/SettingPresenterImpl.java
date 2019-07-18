package com.example.administrator.travel.presenters.impls;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.SettingPresenter;
import com.example.administrator.travel.views.bases.SettingView;

/**
 * Created by Admin on 4/26/2019.
 */

public class SettingPresenterImpl implements SettingPresenter, Listener.OnCheckShareLocationFinishedListener, Listener.OnSetShareLocationFinishedListener, Listener.OnGetUserInforFinishedListener, Listener.OnGetUserAvatarFinishedListener {
    SettingView view;
    UserInteractor userInteractor;
    ParticipantInteractor participantInteractor;
    CompanyInteractor companyInteractor;
    boolean shareLocation;
    String myId, tourStartId;

    public SettingPresenterImpl(SettingView view) {
        this.view = view;
        userInteractor = new UserInteractorImpl();
        participantInteractor = new ParticipantInteractorImpl();
        companyInteractor = new CompanyInteractorImpl();
    }

    @Override
    public void onViewStarted() {
        Toast.makeText(view.getContext(), "logged:"+userInteractor.isLogged()+" "+userInteractor.getUserId(), Toast.LENGTH_SHORT).show();

        if (userInteractor.isLogged()) {
            view.hideBtnLogin();
            view.showBtnLogout();
            myId = userInteractor.getUserId();
            userInteractor.getUserInfor(myId,this);
            view.showLayoutUSer();
            if (!companyInteractor.isCompany(myId, view.getContext())) {
                tourStartId = participantInteractor.getJoiningTourStartId(myId, view.getContext());
                participantInteractor.checkShareLoction(myId, tourStartId, this);
                view.showLayoutShareLocation();
                view.disableSwitchShareLocation();
            }
            else view.hideLayoutShareLocation();
        } else {
            view.hideLayoutUser();
            view.hideBtnLogout();
            view.showBtnLogin();
            view.hideLayoutShareLocation();
        }

    }

    @Override
    public void onShareLocationSwitchClicked() {
        Toast.makeText(view.getContext(), myId + " " + tourStartId, Toast.LENGTH_SHORT).show();
        if (!myId.equals("") && !tourStartId.equals("")) {
            // share = false -> click switch -> save !share = true
            // save success share=true
            participantInteractor.setShareLocation(myId, tourStartId, !shareLocation, view.getContext(), this);
            view.disableSwitchShareLocation();
        }
    }

    @Override
    public void onButtonLoginClicked() {
        view.gotoLoginActivity();
    }

    @Override
    public void onButtonLogoutClicked() {
        userInteractor.logout();
        ((Fragment) view).onStart();
    }
    @Override
    public void onMyProfileClicked(){

        view.gotoProfileActivity(myId);
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

    @Override
    public void onGetUserInforSuccess(UserInformation user) {
        view.showUserName(user.name);
        userInteractor.getUserAvatar(user.id,user.urlAvatar,this);
    }

    @Override
    public void onGetUserAvatarSuccess(String userId, Bitmap avatar) {
        view.showUserAvatar(avatar);
    }
}
