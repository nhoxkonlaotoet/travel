package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.content.Intent;

import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.List;

/**
 * Created by Administrator on 15/11/2018.
 */

public interface TourStartView {
    void showTourStartDate(List<TourStartDate> listTourStartDate, boolean isCompany);
    void notifyFailure(Exception ex);
    void gotoBooktourActivity(String tourStartId, String ownerId);
    Context getContext();
}
