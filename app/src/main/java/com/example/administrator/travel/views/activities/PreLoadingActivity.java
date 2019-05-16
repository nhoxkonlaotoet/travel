package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.bases.PreLoadingPresenter;
import com.example.administrator.travel.presenters.impls.PreLoadingPresenterImpl;
import com.example.administrator.travel.views.bases.PreLoadingView;

public class PreLoadingActivity extends AppCompatActivity implements PreLoadingView {
    PreLoadingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_loading);
        presenter=new PreLoadingPresenterImpl(this);
        presenter.onViewCreated();
    }

    @Override
    public void gotoHomeActivity(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public Context getContext(){
        return this;
    }
}
