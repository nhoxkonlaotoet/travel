package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/3/2019.
 */

public interface UserInteractor {
    String getUserId(Context context);
    boolean isCompany(Context context);
    void getUserName(String userId, Listener.OnGetUserNameFinishedListener listener, int pos);
    void getUserAvatar(String userId, Listener.OnGetUserAvatarFinishedListener listener, int pos);
}
