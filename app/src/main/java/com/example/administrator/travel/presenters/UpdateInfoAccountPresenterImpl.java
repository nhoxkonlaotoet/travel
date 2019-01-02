package com.example.administrator.travel.presenters;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.administrator.travel.models.UpdateInfoAccountInteractor;
import com.example.administrator.travel.models.UpdateInfoAccountInteractorImpl;
import com.example.administrator.travel.models.UpdateInfoAccountListener;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.views.UpdateInfoAccountView;

/**
 * Created by Henry
 */

public class UpdateInfoAccountPresenterImpl implements  UpdateInfoAccountPresenter,UpdateInfoAccountListener {

    UpdateInfoAccountInteractor updateInfoAccountInteractor;
    UpdateInfoAccountView updateInfoAccountView;

    public UpdateInfoAccountPresenterImpl(UpdateInfoAccountView view){
        this.updateInfoAccountView = view;
        updateInfoAccountInteractor = new UpdateInfoAccountInteractorImpl();
    }


    @Override
    public void updateInfoAccountPresenter(SharedPreferences sharedPreferences, Bitmap bitmap, UserInformation userInformation) {
        updateInfoAccountInteractor.updateInfoAccountInteractor(this,sharedPreferences,bitmap,userInformation);
    }

    @Override
    public void onResultUpdate(int statusCode) {
        updateInfoAccountView.showResultUpdate(statusCode);
    }
}
