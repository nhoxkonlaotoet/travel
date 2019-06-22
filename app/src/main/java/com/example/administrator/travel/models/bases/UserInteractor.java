package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/3/2019.
 */

public interface UserInteractor {
    String getUserId();

    void getUserInfor(String userId, Listener.OnGetUserInforFinishedListener listener);

    void getUserAvatar(String userId, Listener.OnGetUserAvatarFinishedListener listener);

    boolean isLogged();

    void login(String email, String password, Context context, Listener.OnLoginFinishedListener listener);

    void signUp(String email, String password, Listener.OnSignUpFinishedListener listener);

    void logout();
}
