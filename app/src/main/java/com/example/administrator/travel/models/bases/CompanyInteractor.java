package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/3/2019.
 */

public interface CompanyInteractor {
    void checkIsCompany(String userId, Listener.OnCheckIsCompanyFinishedListener listener);

    boolean isCompany(String userId, Context context);

    void setIsCompany(String userId, boolean isCompany, Context context);

    void getCompanyContact(String companyId, Listener.OnGetCompanyContactFinishedListener listener);
}
