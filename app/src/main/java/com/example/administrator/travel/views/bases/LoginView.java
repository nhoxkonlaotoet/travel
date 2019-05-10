package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Admin on 4/26/2019.
 */

public interface LoginView {
    void gotoResetPassActivity();
    void gotoSignUpActivity();
    void gotoPreLoadingActivity();
    void close();
    void showLoginDialog();
    void closeLoginDialog();
    void notifyLoginFail(String message);
    Context getContext();
}
