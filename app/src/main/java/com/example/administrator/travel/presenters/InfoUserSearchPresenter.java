package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;

/**
 * Created by Henry on 12/15/2018.
 */

public interface InfoUserSearchPresenter {
    public void TestFriendedPresenter(SharedPreferences sharedPreferences);
    public void RequestAddFriendPresenter();
    public void CancelAddFriendPresenter();
    public void AcceptAddFriendPresenter();
    public void UnFriendPresenter();
}
