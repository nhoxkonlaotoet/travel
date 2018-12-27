package com.example.administrator.travel.models;

/**
 * Created by Administrator on 22/12/2018.
 */

public interface OnGetUserIdFinishedListener {
    void onGetUserIdSuccess(String userId);
    void onGetUserIdFailure(Exception ex);

}
