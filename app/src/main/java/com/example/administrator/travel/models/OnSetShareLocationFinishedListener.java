package com.example.administrator.travel.models;

/**
 * Created by Administrator on 29/12/2018.
 */

public interface OnSetShareLocationFinishedListener {
    void onTurnOnShareLocationSuccess();
    void onTurnOffShareLocationSuccess();
    void onTurnOnShareLocationFailrue(Exception ex);
    void onTurnOffShareLocationFailure(Exception ex);
}
