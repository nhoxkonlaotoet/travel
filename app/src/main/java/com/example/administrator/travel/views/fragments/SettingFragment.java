package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.bases.SettingPresenter;
import com.example.administrator.travel.presenters.impls.SettingPresenterImpl;
import com.example.administrator.travel.views.bases.SettingView;
import com.example.administrator.travel.views.activities.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements SettingView {
    RelativeLayout btnLogin, btnLogout;
    SettingPresenter presenter;
    SwitchCompat switchShareLocation;
    RelativeLayout layoutShareLocation;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapping();

        presenter = new SettingPresenterImpl(this);
        setBtnLoginClick();
        setBtnLogoutClick();
        setSwitchShareLocationClick();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onViewStarted();
    }

    void mapping() {
        btnLogin = getActivity().findViewById(R.id.btnLogin);
        btnLogout = getActivity().findViewById(R.id.btnLogout);
        switchShareLocation = getActivity().findViewById(R.id.switchShareLocation);
        layoutShareLocation = getActivity().findViewById(R.id.layoutShareLocation);
    }

    public void setSwitchShareLocationClick() {
        switchShareLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onShareLocationSwitchClicked();
            }
        });
    }

    public void setBtnLoginClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonLoginClicked();
            }
        });
    }

    public void setBtnLogoutClick() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity((new Intent(getActivity(), LoginActivity.class)));
                presenter.onButtonLogoutClicked();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onStart();
    }

    @Override
    public void hideBtnLogin() {
        btnLogin.setVisibility(View.GONE);
    }

    @Override
    public void showBtnLogin() {
        btnLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBtnLogout() {
        btnLogout.setVisibility(View.GONE);
    }

    @Override
    public void showBtnLogout() {
        btnLogout.setVisibility(View.VISIBLE);
    }

    @Override
    public void gotoLoginActivity() {
        startActivity((new Intent(getActivity(), LoginActivity.class)));
    }

    @Override
    public void turnOnSwitchShareLocation() {
        switchShareLocation.setChecked(true);
    }

    @Override
    public void turnOffSwitchShareLocation() {
        switchShareLocation.setChecked(false);
    }

    @Override
    public void disableSwitchShareLocation() {
        switchShareLocation.setClickable(false);
    }

    @Override
    public void enableSwitchShareLocation() {
        switchShareLocation.setClickable(true);
    }

    @Override
    public void showLayoutShareLocation() {
        layoutShareLocation.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayoutShareLocation() {
        layoutShareLocation.setVisibility(View.GONE);
    }

    @Override
    public void setSwitchShareLocationState(boolean value) {
        switchShareLocation.setChecked(value);
    }

    @Override
    public void notifySetShareLocationFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
