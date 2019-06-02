package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.UserInformation;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Administrator on 22/12/2018.
 */

public interface CompanyContactView {
    void showContact(Company company);
    void showContact(UserInformation user);
    void notifyGetContactFailure(Exception ex);
    void hideBtnWebsite();
    void hideBtnAddress();
    void hideBtnPhoneNumber();
    void gotoWebsite(Intent intent);
    void gotoCall(Intent intent);
    void gotoMap(Intent intent);
    Context getContext();
}
