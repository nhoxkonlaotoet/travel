package com.example.administrator.travel.models;

import android.content.SharedPreferences;

/**
 * Created by Henry on 12/18/2018.
 */

public interface ChatMessagerInteractor {
    public void getMessageIntractor(ChatMessagerListener listener, SharedPreferences sharedPreferences);
    public void SendMessageInteractor(SharedPreferences sharedPreferences,String message);
}
