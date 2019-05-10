package com.example.administrator.travel.views.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.bases.PreLoadingPresenter;
import com.example.administrator.travel.presenters.impls.PreLoadingPresenterImpl;
import com.example.administrator.travel.views.PreLoadingView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
