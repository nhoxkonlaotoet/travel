package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/3/2019.
 */

public class CompanyInteractorImpl implements CompanyInteractor {

    @Override
    public void checkIsCompanyOnline(String userId, Listener.OnCheckIsCompanyFinishedListener listener) {

    }

    @Override
    public boolean isCompany(Context context) {
        return false;
    }
}
