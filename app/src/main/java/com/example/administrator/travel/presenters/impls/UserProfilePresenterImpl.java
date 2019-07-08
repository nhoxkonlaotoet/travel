package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.UserProfilePresenter;
import com.example.administrator.travel.views.bases.UserProfileView;

import static android.app.Activity.RESULT_OK;

public class UserProfilePresenterImpl implements UserProfilePresenter, Listener.OnGetUserInforFinishedListener, Listener.OnGetUserAvatarFinishedListener {
    UserProfileView view;
    UserInteractor userInteractor;

    public UserProfilePresenterImpl(UserProfileView view) {
        this.view = view;
        userInteractor = new UserInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        String userId = bundle.getString("userId");
        userInteractor.getUserInfor(userId, this);
        if (userId.equals(userInteractor.getUserId()))
        {
            view.showButtonEditInfor();
            view.enableButtonEditInfor();
        }
        else
        {
            view.hideButtonEditInfor();
            view.disableButtonEditInfor();
        }
    }

    @Override
    public void onButtonEditInforClicked() {
        view.gotoUpdateUserProfileActivity();
    }

    @Override
    public void onViewResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK)
        {
            view.detroy();
            view.create();
        }
    }

    @Override
    public void onGetUserInforSuccess(UserInformation user) {
        userInteractor.getUserAvatar(user.id,user.urlAvatar,this);
        view.showUserInfor(user);
    }

    @Override
    public void onGetUserAvatarSuccess(String userId, Bitmap avatar) {
        view.showUserAvatar(avatar);
    }
}
