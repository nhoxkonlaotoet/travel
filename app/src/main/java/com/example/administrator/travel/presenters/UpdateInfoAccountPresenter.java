package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.UserInformation;

/**
 * Created by Henry
 */

public interface UpdateInfoAccountPresenter {
    public void updateInfoAccountPresenter(SharedPreferences sharedPreferences, Bitmap bitmap, UserInformation userInformation);
}
