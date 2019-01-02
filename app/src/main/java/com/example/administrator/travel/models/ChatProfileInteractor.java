package com.example.administrator.travel.models;

import android.content.SharedPreferences;

/**
 * Created by Henry
 */

public interface ChatProfileInteractor {
    public void getUserProfileInteractor(ChatProfileListener listener, SharedPreferences sharedPreferences);
}
