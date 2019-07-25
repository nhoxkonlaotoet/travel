package com.example.administrator.travel.views.bases;

import android.content.Context;

import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.List;

import sun.bob.mcalendarview.vo.DateData;

public interface AddTourStartDateView {
    List<DateData> getListDate();

    void showPrices(int adultPrice, int childrenPrice, int babyPrice);

    void showExistDate(List<TourStartDate> tourStartDateList);

    String getEditTextNumberOfAdultValue();

    String getEditTextNumberOfChildrenValue();

    String getEditTextNumberOfBabyValue();

    void removeDate(int year, int month, int day);

    void addDate(int year, int month, int day);

    void notify(String message);

    Context getContext();

    void close();
}
