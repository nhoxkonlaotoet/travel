package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/3/2019.
 */

public interface UserInteractor {
    String getUserId(Context context);

    void getUserInfor(String userId, Listener.OnGetUserInforFinishedListener listener);

    void getUserAvatar(String userId, Listener.OnGetUserAvatarFinishedListener listener);

    boolean isLogged(Context context);

    void login(String email, String password, Context context, Listener.OnLoginFinishedListener listener);

    void signUp(String email, String password, Listener.OnSignUpFinishedListener listener);

    void rememberLogin(String userId, Context context);

    void logout(Context context);
}
