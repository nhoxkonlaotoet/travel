package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.UserInformation;

public interface UserProfileView {

    void showUserAvatar(Bitmap avatar);

    void showUserInfor(UserInformation user);

    void gotoUpdateUserProfileActivity();

    void enableButtonEditInfor();

    void disableButtonEditInfor();

    void showButtonEditInfor();

    void hideButtonEditInfor();

    void notify(String message);

    Context getContext();

    void close();

    void detroy();

    void create();
}
