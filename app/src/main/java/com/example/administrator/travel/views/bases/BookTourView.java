package com.example.administrator.travel.views.bases;

import android.content.Context;

import com.example.administrator.travel.adapter.TourBookingAdapter;
import com.example.administrator.travel.models.entities.TourBookingDetail;

import java.util.ArrayList;

/**
 * Created by Administrator on 18/11/2018.
 */

public interface BookTourView {

    void showPrice(int adultPrice, int childrenPrice, int babyPrice);

    void showNumberAvailableSlot(int availableNumber);

    void addTourists(int numberOfTourist, int touristType, int price);

    void removeTourists(int numberOfTourist, int touristType);

    void gotoLoginActivity();

    void gotoCardAuthorizationActivity(int authorizationCode, ArrayList<TourBookingDetail> tourBookingDetailList,
                                       String tourStartDateId, Integer numberOfAdult, Integer numberOfChildren,
                                       Integer numberOfBaby, Integer money, String ownerId);
    void showButtonNext();

    void hideButtonNext();

    void notify(String message);

    void close();

    Context getContext();

    TourBookingAdapter getBookingAdapter();
}


