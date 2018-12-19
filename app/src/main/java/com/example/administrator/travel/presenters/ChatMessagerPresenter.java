package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;

/**
 * Created by Henry on 12/18/2018.
 */

public interface ChatMessagerPresenter {
    public void getMessagePresenter(SharedPreferences sharedPreferences);
    public void SendMessagePresenter(SharedPreferences sharedPreferences,String message);
}
