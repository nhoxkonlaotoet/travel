package com.example.administrator.travel.models.impls;

import com.example.administrator.travel.models.bases.BookTourInteractor;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 5/26/2019.
 */

public class BookTourInteractorImpl  implements BookTourInteractor{
    @Override
    public void getBooking(String bookingId, Listener.OnGetBookingFinishedListener listener) {

    }

    @Override
    public void bookTour(TourBooking tourBooking, Listener.OnBookTourFinishedListener listener) {

    }
}
