package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/3/2019.
 */

public interface UserInteractor {
    String getUserId(Context context);

    void getUserName(String userId, Listener.OnGetUserNameFinishedListener listener, int pos);

    void getUserAvatar(String userId, Listener.OnGetUserAvatarFinishedListener listener, int pos);

    boolean isLogged(Context context);

    void login(String email, String password, Context context, Listener.OnLoginFinishedListener listener);

    void signUp(String email, String password, Listener.OnSignUpFinishedListener listener);

    void rememberLogin(String userId, Context context);

    void logout(Context context);
}
