package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;

import com.example.administrator.travel.models.InfoUserSearchInteractor;
import com.example.administrator.travel.models.InfoUserSearchInteractorImpl;
import com.example.administrator.travel.models.InfoUserSearchListener;
import com.example.administrator.travel.views.InfoUserSearchView;

/**
 * Created by Henry on 12/15/2018.
 */

public class InfoUserSearchPresenterImpl implements InfoUserSearchPresenter, InfoUserSearchListener {
    InfoUserSearchView infoUserSearchView;
    InfoUserSearchInteractor infoUserSearchInteractor;

    public InfoUserSearchPresenterImpl(InfoUserSearchView infoUserSearchView){
        infoUserSearchInteractor = new InfoUserSearchInteractorImpl();
        this.infoUserSearchView = infoUserSearchView;
    }

    @Override
    public void TestFriendedPresenter(SharedPreferences sharedPreferences) {
        infoUserSearchInteractor.TestFriendedInteractor(this,sharedPreferences);
    }

    @Override
    public void RequestAddFriendPresenter() {
        infoUserSearchInteractor.RequestAddFriendInteractor();
    }

    @Override
    public void CancelAddFriendPresenter() {
        infoUserSearchInteractor.CancelAddFriendInteractor();
    }

    @Override
    public void AcceptAddFriendPresenter() {
        infoUserSearchInteractor.AcceptAddFriendInteractor();
    }

    @Override
    public void UnFriendPresenter() {
        infoUserSearchInteractor.UnFriendInteractor();
    }

    @Override
    public void onResultTestFriendedListener(int friend) {
        infoUserSearchView.onResultTestFriendedView(friend);
    }
}
