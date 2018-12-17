package com.example.administrator.travel.models;

import android.content.SharedPreferences;

/**
 * Created by Henry on 12/15/2018.
 */

public interface InfoUserSearchInteractor {
    public void TestFriendedInteractor(InfoUserSearchListener listener, SharedPreferences sharedPreferences);
    public void RequestAddFriendInteractor();
    public void CancelAddFriendInteractor();
    public void AcceptAddFriendInteractor();
    public void UnFriendInteractor();
}
