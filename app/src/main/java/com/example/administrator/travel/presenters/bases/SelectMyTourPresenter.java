package com.example.administrator.travel.presenters.bases;

import android.view.View;

/**
 * Created by Admin on 4/4/2019.
 */

public interface SelectMyTourPresenter {
    void onViewCreated();
    void onBtnScanQRClick();
    void onMyTourItemClicked(String tourId, String tourStartId);
    void onMyOwnedTourItemClicked(String tourId, String owner);
    void onBtnLoginClicked();
    void onViewResult(String tourStartId);
}
