package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.presenters.bases.UserProfilePresenter;
import com.example.administrator.travel.presenters.impls.UserProfilePresenterImpl;
import com.example.administrator.travel.views.bases.UserProfileView;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity implements UserProfileView {

    private Toolbar toolbar;
    private CircleImageView imgvUserAvatar;
    private TextView txtUserName, txtUserNameTop, txtUserGender, txtUserEmail, txtUserDOB, txtUserPhoneNumber;
    private Button btnEditInfor;

    private UserProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mapping();
        setOnButtonEditInforClick();
        Bundle bundle = getIntent().getExtras();
        presenter = new UserProfilePresenterImpl(this);
        presenter.onViewCreated(bundle);

    }

    private void mapping() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
        imgvUserAvatar = findViewById(R.id.imgvUserAvatar);
        txtUserName = findViewById(R.id.txtUserName);
        txtUserNameTop = findViewById(R.id.txtUserNameTop);
        txtUserGender = findViewById(R.id.txtUserGender);
        txtUserEmail = findViewById(R.id.txtUserEmail);
        txtUserDOB = findViewById(R.id.txtUserDOB);
        txtUserPhoneNumber = findViewById(R.id.txtUserPhoneNumber);
        btnEditInfor = findViewById(R.id.btnEditInfor);
    }

    private void setOnButtonEditInforClick() {
        btnEditInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonEditInforClicked();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void showUserAvatar(Bitmap avatar) {
        imgvUserAvatar.setImageBitmap(avatar);
    }

    @Override
    public void showUserInfor(UserInformation user) {
        txtUserNameTop.setText(user.name);
        txtUserName.setText(user.name);
        txtUserGender.setText(user.isMale ? "Nam" : "Nữ");
        txtUserEmail.setText(user.mail);
        txtUserDOB.setText(user.dateOfBirth + "/" + user.monthOfBirth + "/" + user.yearOfBirth);
        txtUserPhoneNumber.setText(user.sdt);
    }

    @Override
    public void gotoUpdateUserProfileActivity() {
        startActivityForResult(new Intent(UserProfileActivity.this, UpdateUserProfileActivity.class), 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        presenter.onViewResult(requestCode,resultCode,data);
       // onDestroy();
       // onCreate(null);
    }

    @Override
    public void enableButtonEditInfor() {
        btnEditInfor.setEnabled(true);
    }

    @Override
    public void disableButtonEditInfor() {
        btnEditInfor.setEnabled(false);
    }

    @Override
    public void showButtonEditInfor() {
        btnEditInfor.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButtonEditInfor() {
        btnEditInfor.setVisibility(View.GONE);
    }


    @Override
    public void notify(String message) {
        Snackbar.make(null, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void detroy() {
        onDestroy();
    }

    @Override
    public void create() {
        onCreate(null);
    }
}
