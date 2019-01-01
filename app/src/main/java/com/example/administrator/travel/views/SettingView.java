package com.example.administrator.travel.views;

/**
 * Created by Administrator on 22/12/2018.
 */

public interface SettingView {
    void hideBtnLogin();
    void showBtnLogin();
    void hideBtnLogout();
    void showBtnLogout();
    void gotoLoginActivity();
    void notifyLogoutFailure(Exception ex);
    void turnOnSwitchShareLocation();
    void turnOffSwitchShareLocation();
    void showLayoutShareLocation();
    void hideLayoutShareLocation();
}
