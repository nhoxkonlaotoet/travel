package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourStartDate;

/**
 * Created by Administrator on 18/11/2018.
 */

public interface OnBookTourFinishedListener {
    void onGetMyBookTourSuccess(TourBooking tourBooking);
    void onGetMyBookTourFailure(Exception ex);
    void onGetPricesSuccess(TourStartDate tourStartDate);
    void onGetPricesFailure(Exception ex);
    public void onBookTourSuccess();
    public void onBookTourFailure(Exception ex);
}
