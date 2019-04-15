package com.example.administrator.travel.presenters.bases;

import android.view.View;

/**
 * Created by Admin on 4/4/2019.
 */

public interface SelectMyTourPresenter {
    void onViewCreated();
    void onBtnScanQRClick();
    void onItemTourClicked(View item);
    void onBtnLoginClicked();
    void onViewResult(String resultString);
}
