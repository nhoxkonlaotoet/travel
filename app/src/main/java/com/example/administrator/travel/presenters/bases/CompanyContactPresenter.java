package com.example.administrator.travel.presenters.bases;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Admin on 4/11/2019.
 */

public interface CompanyContactPresenter {
    void onViewCreated(Bundle bundle);
    void onButtonWebsiteClicked();
    void onButtonPhoneClicked();
    void onButtonAddressClicked();
}
