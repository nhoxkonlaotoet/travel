package com.example.administrator.travel.views;

import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.List;

/**
 * Created by Administrator on 15/11/2018.
 */

public interface TourStartView {
    public void loadTourStartDate();
    public void showTourStartDate(List<TourStartDate> listTourStartDate);

}
