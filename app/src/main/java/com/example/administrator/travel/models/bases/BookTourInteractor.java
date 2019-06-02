package com.example.administrator.travel.models.bases;

import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 5/26/2019.
 */

public interface BookTourInteractor {
    void getBooking(String bookingId, Listener.OnGetBookingFinishedListener listener);

    void bookTour(TourBooking tourBooking, Listener.OnBookTourFinishedListener listener);
}
