package com.example.administrator.travel.models;

import android.content.SharedPreferences;

/**
 * Created by Henry on 12/14/2018.
 */

public interface ChatSearchFriendInteractor {
    public void getListFriendSearch(ChatSearchFriendListener listener);
    public void setSearchName(String searchName);
    public void getFriendInvitation(ChatSearchFriendListener listener, SharedPreferences sharedPreferences);
}
