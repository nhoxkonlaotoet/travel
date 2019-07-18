package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.administrator.travel.models.bases.FriendInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.impls.FriendInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.UserProfilePresenter;
import com.example.administrator.travel.views.bases.UserProfileView;

import static android.app.Activity.RESULT_OK;

public class UserProfilePresenterImpl implements UserProfilePresenter, Listener.OnGetUserInforFinishedListener, Listener.OnGetUserAvatarFinishedListener, Listener.OnSendAddFriendRequestFinishedListener {
    UserProfileView view;
    UserInteractor userInteractor;
    FriendInteractor friendInteractor;
    String userId;
    public UserProfilePresenterImpl(UserProfileView view) {
        this.view = view;
        userInteractor = new UserInteractorImpl();
        friendInteractor = new FriendInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        userId = bundle.getString("userId");
        userInteractor.getUserInfor(userId, this);
        if (userId.equals(userInteractor.getUserId())) {
            view.showButtonEditInfor();
            view.enableButtonEditInfor();
            view.hideButtonAddFriend();
            view.hideButtonCancelAddFriend();
            view.hideButtonUnfriend();
        } else {
            view.hideButtonEditInfor();
            view.disableButtonEditInfor();
            view.showButtonAddFriend();
            view.hideButtonCancelAddFriend();
            view.hideButtonUnfriend();
        }
    }

    @Override
    public void onButtonEditInforClicked() {
        view.gotoUpdateUserProfileActivity();
    }

    @Override
    public void onViewResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            view.detroy();
            view.create();
        }
    }

    @Override
    public void onButtonAddFriendClicked() {
        if(userInteractor.isLogged()) {
                // myId, friendId
            friendInteractor.sendAddFriendRequest(userInteractor.getUserId(),userId,this);
        }
    }

    @Override
    public void onGetUserInforSuccess(UserInformation user) {
        userInteractor.getUserAvatar(user.id, user.urlAvatar, this);
        view.showUserInfor(user);
    }

    @Override
    public void onGetUserAvatarSuccess(String userId, Bitmap avatar) {
        view.showUserAvatar(avatar);
    }

    @Override
    public void onSendAddFriendRequestSuccess() {

    }

    @Override
    public void onSendAddFriendRequestFail(Exception ex) {

    }
}
