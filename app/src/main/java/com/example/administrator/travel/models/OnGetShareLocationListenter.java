package com.example.administrator.travel.models;

/**
 * Created by Administrator on 29/12/2018.
 */

public interface OnGetShareLocationListenter {
    void onGetShareLocationSuccess(boolean isShared);
    void onGetShareLocationFailure(Exception ex);
}
