package com.example.administrator.travel.models;

/**
 * Created by Administrator on 31/12/2018.
 */

public interface OnPostActivityFinishedListener {
    void onPostSuccess();
    void onPostFailure(Exception ex);
}
