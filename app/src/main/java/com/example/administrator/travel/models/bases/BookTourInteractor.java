package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourBookingDetail;
import com.example.administrator.travel.models.entities.TourBookingRequest;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 5/26/2019.
 */

public interface BookTourInteractor {
    void getBooking(String bookingId, Listener.OnGetBookingFinishedListener listener);

    void bookTour(TourBookingRequest tourBookingRequest, Context context, Listener.OnBookTourFinishedListener listener);

}
