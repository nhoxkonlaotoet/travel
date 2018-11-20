package com.example.administrator.travel.models;

/**
 * Created by Administrator on 18/11/2018.
 */

public interface OnBookTourFinishedListener {
    public void onSuccess();
    public void onFailure(Exception ex);
}
