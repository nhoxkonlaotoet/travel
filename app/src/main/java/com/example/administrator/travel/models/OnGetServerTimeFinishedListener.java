package com.example.administrator.travel.models;

/**
 * Created by Administrator on 28/11/2018.
 */

public interface OnGetServerTimeFinishedListener {
    public void onGetTimeSuccess(Long time);
    public void onGetTimeFailure(Exception ex);
}
