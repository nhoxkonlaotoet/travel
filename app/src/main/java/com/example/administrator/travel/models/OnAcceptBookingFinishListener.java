package com.example.administrator.travel.models;

import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.UserInformation;

import java.util.List;

/**
 * Created by Administrator on 02/01/2019.
 */

public interface OnAcceptBookingFinishListener {
    void onGetBookingsSuccess(List<TourBooking> lstBooking);
    void onGetBookingsFinishedTour();
    void onGetBookingsFailure(Exception ex);
    void onGetUserInforSuccess(UserInformation user);
}
