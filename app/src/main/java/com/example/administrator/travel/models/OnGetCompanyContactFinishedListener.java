package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.UserInformation;

/**
 * Created by Administrator on 22/12/2018.
 */

public interface OnGetCompanyContactFinishedListener {
    void getCompanyContactSuccess(Company company);
    void getOnwerContactSuccess(UserInformation user);
    void getCompanyContactFailure(Exception ex);
}
