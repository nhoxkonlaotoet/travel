package com.example.administrator.travel.models;

import android.content.SharedPreferences;

/**
 * Created by Henry on 12/19/2018.
 */

public interface ChatInteractor {
    public void getChatInteractor(ChatListener listener, SharedPreferences sharedPreferences);
}
