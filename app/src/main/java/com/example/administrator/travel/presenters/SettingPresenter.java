package com.example.administrator.travel.presenters;

import android.view.View;

import com.example.administrator.travel.models.OnGetUserIdFinishedListener;
import com.example.administrator.travel.models.OnUserLogoutFinishedListener;
import com.example.administrator.travel.models.SettingInteractor;
import com.example.administrator.travel.views.SettingView;
import com.example.administrator.travel.views.fragments.SettingFragment;

/**
 * Created by Administrator on 22/12/2018.
 */

public class SettingPresenter implements OnGetUserIdFinishedListener,OnUserLogoutFinishedListener {
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

    }
    public void onBtnLoginClicked()
    {
        view.gotoLoginActivity();
    }
    public void onBtnLogoutClick()
    {
        settingInteractor.logout(this,((SettingFragment)view).getActivity());
    }

    @Override
    public void onGetUserIdSuccess(String userId) {
        // id != "none",
            view.showBtnLogout();
            view.hideBtnLogin();
    }

    @Override
    public void onGetUserIdFailure(Exception ex) {
        //id =="none" | error
        view.showBtnLogin();
        view.hideBtnLogout();
    }

    @Override
    public void onLogoutSuccess() {
        ((SettingFragment)view).onStart();
    }

    @Override
    public void onLogoutFailure(Exception ex) {
        view.notifyLogoutFailure(ex);
    }
}
