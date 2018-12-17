package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;

/**
 * Created by Henry on 12/14/2018.
 */

public interface ChatSearchFriendPresenter {
    public void getSearchFriendPresenter(String searchName);
    public void getFriendInvitationPresenter(SharedPreferences sharedPreferences);
}
