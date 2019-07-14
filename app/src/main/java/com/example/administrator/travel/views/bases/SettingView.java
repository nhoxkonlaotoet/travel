package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Admin on 4/26/2019.
 */

public interface SettingView {
    void hideBtnLogin();
    void showBtnLogin();
    void hideBtnLogout();
    void showBtnLogout();
    void gotoLoginActivity();
    void gotoProfileActivity(String myId);
    void turnOnSwitchShareLocation();
    void turnOffSwitchShareLocation();
    void disableSwitchShareLocation();
    void enableSwitchShareLocation();
    void showLayoutShareLocation();
    void hideLayoutShareLocation();
    void setSwitchShareLocationState(boolean value);
    void notifySetShareLocationFail(String message);
    Context getContext();
}
