package com.example.administrator.travel.views.bases;

import android.content.Context;

import com.example.administrator.travel.models.entities.UserInformation;

import java.util.List;

/**
 * Created by Henry on 12/14/2018.
 */

public interface SearchFriendView {
    void showListUserSearch(List<UserInformation> userInformationList);

    void showFriendInvitation(List<UserInformation> listUserSearch);

    void clearRecyclerViewSearchFriend();

    void clearEditTextSearchFriend();

    void showHaveNoSearchResult();

    void hideHaveNoSearchResult();

    void gotoProfile(String friendId);

    Context getContext();
}
