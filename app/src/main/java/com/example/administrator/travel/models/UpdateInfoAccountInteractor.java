package com.example.administrator.travel.models;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.UserInformation;
/**
 * Created by Henry
 */

public interface UpdateInfoAccountInteractor {
    public void updateInfoAccountInteractor(UpdateInfoAccountListener listener,SharedPreferences sharedPreferences, Bitmap bitmap, UserInformation userInformation);
}
