package com.example.administrator.travel.models;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;

import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.impls.TourStartInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.CompanyContactPresenter;
import com.example.administrator.travel.views.CompanyContactView;

/**
 * Created by Henry
 */

public interface UpdateInfoAccountInteractor {
    public void updateInfoAccountInteractor(UpdateInfoAccountListener listener,SharedPreferences sharedPreferences, Bitmap bitmap, UserInformation userInformation);
    /**
     * Created by Admin on 4/11/2019.
     */
}
