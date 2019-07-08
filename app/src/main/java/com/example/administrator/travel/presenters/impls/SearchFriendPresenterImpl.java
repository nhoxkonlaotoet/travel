package com.example.administrator.travel.presenters.impls;

import android.os.Bundle;
import android.util.Log;

import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.SearchFriendPresenter;
import com.example.administrator.travel.views.bases.SearchFriendView;

import java.util.List;

public class SearchFriendPresenterImpl implements SearchFriendPresenter, Listener.OnSearchUsersFinishedListener {
    SearchFriendView view;
    UserInteractor userInteractor;

    public SearchFriendPresenterImpl(SearchFriendView view) {
        this.view = view;
        userInteractor = new UserInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {

        view.hideHaveNoSearchResult();
    }

    @Override
    public void onEditTextSearchFriendTypingStoped(String keyword) {
        userInteractor.searchUsers(keyword, this);
        view.hideHaveNoSearchResult();
    }

    @Override
    public void onItemFriendClicked(String friendId) {
        view.gotoProfile(friendId);
    }

    @Override
    public void onBackButtonPressed() {

    }

    @Override
    public void onButtonClearSearchFriendResultClicked() {
        view.clearRecyclerViewSearchFriend();
        view.clearEditTextSearchFriend();
    }

    @Override
    public void onSearchUsersSuccess(List<UserInformation> userInformationList) {
        if (userInformationList.size() == 0) {
            view.showHaveNoSearchResult();
            view.clearRecyclerViewSearchFriend();
        }
        else
            view.showListUserSearch(userInformationList);
    }

    @Override
    public void onSearchUsersFail(Exception ex) {

    }
}
