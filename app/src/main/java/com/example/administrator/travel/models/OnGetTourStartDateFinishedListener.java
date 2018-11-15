package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.List;

/**
 * Created by Administrator on 15/11/2018.
 */

public interface OnGetTourStartDateFinishedListener {
    public void onSuccess(List<TourStartDate> listTourStartDate);
}
