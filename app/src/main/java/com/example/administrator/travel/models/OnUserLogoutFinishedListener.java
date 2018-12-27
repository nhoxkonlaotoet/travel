package com.example.administrator.travel.models;

/**
 * Created by Administrator on 22/12/2018.
 */

public interface OnUserLogoutFinishedListener {
    void onLogoutSuccess();
    void onLogoutFailure(Exception ex);
}
