package com.example.administrator.travel.views;

import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.UserInformation;

import java.util.List;

/**
 * Created by Administrator on 02/01/2019.
 */

public interface AcceptBookingView {
    void showBookings(List<TourBooking> lstBooking);
    void notifyTourFinished();
    void showUserInfo(UserInformation user);
}
