package com.example.administrator.travel.views;

/**
 * Created by Administrator on 18/11/2018.
 */

public interface BookTourView {

    void disableBtnAccept();
    void enableBtnAccept();
    void updateAdultNumber(int number);
    void updateChildrenNumber(int number);
    void updateBabyNumber(int number);
    void notify(String message);
    void close();
    void showPrice(int adultPrice, int childrenPrice, int babyPrice, int availableNumber);
    void showNumberPeople(int adultNumber, int childrenNumber, int babyNumber);
}

