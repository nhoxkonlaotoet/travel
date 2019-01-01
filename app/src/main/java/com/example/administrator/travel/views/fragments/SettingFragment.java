package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.SettingPresenter;
import com.example.administrator.travel.views.SettingView;
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
        btnLogin = getActivity().findViewById(R.id.btnLogin);
        btnLogout=getActivity().findViewById(R.id.btnLogout);
        switchShareLocation = getActivity().findViewById(R.id.switchShareLocation);
        layoutShareLocation = getActivity().findViewById(R.id.layoutShareLocation);

        presenter=new SettingPresenter(this);
        setBtnLoginClick();
        setBtnLogoutClick();
        setSwitchShareLocationCheckChange();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onViewStart();
    }
    public void setSwitchShareLocationCheckChange(){
        switchShareLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                presenter.onSwitchShareLocationCheckedChanged(b);
            }
        });
    }
    public void setBtnLoginClick()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            presenter.onBtnLoginClicked();
            }
        });
    }
    public void setBtnLogoutClick()
    {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity((new Intent(getActivity(), LoginActivity.class)));
            presenter.onBtnLogoutClick();
            }
        });
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
    public void notifyLogoutFailure(Exception ex) {
        Toast.makeText(getActivity(),"Không thể đăng xuất \r\n" + ex.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void showLayoutShareLocation() {
        layoutShareLocation.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayoutShareLocation() {
        layoutShareLocation.setVisibility(View.GONE);
    }

}
