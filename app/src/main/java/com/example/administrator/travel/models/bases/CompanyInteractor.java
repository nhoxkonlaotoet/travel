package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/3/2019.
 */

public interface CompanyInteractor {
    void checkIsCompanyOnline(String userId, Listener.OnCheckIsCompanyFinishedListener listener);
    boolean isCompany(Context context);
}
