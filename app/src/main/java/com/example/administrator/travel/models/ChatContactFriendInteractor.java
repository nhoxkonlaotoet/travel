package com.example.administrator.travel.models;

import android.content.SharedPreferences;

/**
 * Created by Henry on 12/17/2018.
 */

public interface ChatContactFriendInteractor {
    public void getFriendsInteractor(ChatContactFriendListener listener, SharedPreferences sharedPreferences);
}
