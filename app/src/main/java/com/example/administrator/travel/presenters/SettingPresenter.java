package com.example.administrator.travel.presenters;

import android.util.Log;
import android.view.View;

import com.example.administrator.travel.models.OnGetShareLocationListenter;
import com.example.administrator.travel.models.OnGetUserIdFinishedListener;
import com.example.administrator.travel.models.OnSetShareLocationFinishedListener;
import com.example.administrator.travel.models.OnUserLogoutFinishedListener;
import com.example.administrator.travel.models.SettingInteractor;
import com.example.administrator.travel.views.bases.SettingView;
import com.example.administrator.travel.views.fragments.SettingFragment;

/**
 * Created by Administrator on 22/12/2018.
 */

public class SettingPresenter implements OnGetUserIdFinishedListener,OnUserLogoutFinishedListener,
        OnSetShareLocationFinishedListener,OnGetShareLocationListenter {
    SettingView view;
    SettingInteractor settingInteractor;
    public SettingPresenter(SettingView view)
    {
        this.view=view;
        settingInteractor=new SettingInteractor();
    }
    public void onViewStart()
    {
        settingInteractor.getUserId(this,((SettingFragment)view).getActivity());
        settingInteractor.getShareLoction(this);

    }
    public void onBtnLoginClicked()
    {
        view.gotoLoginActivity();
    }
    public void onBtnLogoutClick()
    {
        settingInteractor.logout(this,((SettingFragment)view).getActivity());
    }
    public void onSwitchShareLocationCheckedChanged(boolean checked)
    {
        settingInteractor.setShareLocation(checked,this);
    }
    @Override
    public void onGetUserIdSuccess(String userId) {
        // id != "none",
            view.showBtnLogout();
            view.hideBtnLogin();
            view.showLayoutShareLocation();
    }

    @Override
    public void onGetUserIdFailure(Exception ex) {
        //id =="none" | error
        view.showBtnLogin();
        view.hideBtnLogout();
        view.hideLayoutShareLocation();
    }

    @Override
    public void onLogoutSuccess() {
        ((SettingFragment)view).onStart();
    }

    @Override
    public void onLogoutFailure(Exception ex){
    }

    @Override
    public void onTurnOnShareLocationSuccess() {

    }

    @Override
    public void onTurnOffShareLocationSuccess() {

    }

    @Override
    public void onTurnOnShareLocationFailrue(Exception ex) {
        Log.e("TurnOn failure: ",ex+"" );
        view.turnOffSwitchShareLocation();
    }

    @Override
    public void onTurnOffShareLocationFailure(Exception ex) {
        Log.e("TurnOn failure: ",ex+"" );

        view.turnOnSwitchShareLocation();
    }

    @Override
    public void onGetShareLocationSuccess(boolean isShared) {
        if(isShared)
            view.turnOnSwitchShareLocation();
        else
            view.turnOffSwitchShareLocation();
    }

    @Override
    public void onGetShareLocationFailure(Exception ex) {

    }
}
