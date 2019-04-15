package com.example.administrator.travel.models.bases;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 4/11/2019.
 */

public interface CompanyContactInteractor {
    void getCompanyContact(String companyId, Listener.OnGetCompanyContactFinishedListener listener);
}
