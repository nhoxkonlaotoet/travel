package com.example.administrator.travel.presenters.impls;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.administrator.travel.models.ChatContactFriendInteractor;
import com.example.administrator.travel.models.ChatContactFriendInteractorImpl;
import com.example.administrator.travel.models.bases.FriendInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.impls.FriendInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.FriendContactPresenter;
import com.example.administrator.travel.views.fragments.FriendContactView;

import java.util.List;


public class FriendContactPresenterImpl implements FriendContactPresenter, Listener.OnGetFriendsFinishedListener {

    FriendContactView view;
    FriendInteractor friendInteractor;
    UserInteractor userInteractor;
    public FriendContactPresenterImpl(FriendContactView view){
        this.view = view;
        friendInteractor = new FriendInteractorImpl();
        userInteractor =new UserInteractorImpl();
    }



    @Override
    public void onViewCreated(Bundle bundle) {
        if(userInteractor.isLogged()) {
            String myId = userInteractor.getUserId();
            friendInteractor.getFriends(myId,this);
        }
    }

    @Override
    public void onItemFriendClick(String friendId) {
        view.gotoChatAcitivty(friendId);
    }

    @Override
    public void onGetFriendsSuccess(List<String> friendIdList) {
        view.showFriends(friendIdList);
    }

    @Override
    public void onGetFriendsFail(Exception ex) {
        view.notify(ex.getMessage());
    }
}
