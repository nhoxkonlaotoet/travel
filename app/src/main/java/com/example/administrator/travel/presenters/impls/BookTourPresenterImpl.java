package com.example.administrator.travel.presenters.impls;


import android.os.Bundle;

import com.example.administrator.travel.models.bases.BookTourInteractor;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.BookTourInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.BookTourPresenter;
import com.example.administrator.travel.views.BookTourView;
import com.example.administrator.travel.views.activities.BookTourActivity;
import com.google.firebase.database.DatabaseException;

/**
 * Created by Administrator on 18/11/2018.
 */

public class BookTourPresenterImpl implements BookTourPresenter, Listener.OnBookTourFinishedListener,Listener.OnGetBookingFinishedListener{
    BookTourView view;
    BookTourInteractor bookTourInteractor;
    int adultNumber=0, childrenNumber=0, babyNumber=0;
    int adultPrice, childrenPrice, babyPrice;
    String tourStartId;

    public BookTourPresenterImpl(BookTourView view){

        this.view = view;
        bookTourInteractor = new BookTourInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle){
        tourStartId = bundle.getString("tourStartId");
        bookTourInteractor.getBooking(tourStartId,this);
    }

    @Override
    public void onButtonAcceptClicked(){
        if(adultNumber<0)
            return;
        int money = adultNumber*adultPrice + childrenNumber*childrenPrice + babyNumber*babyPrice;
        TourBooking tourBooking = new TourBooking(null,tourStartId,0L,adultNumber,childrenNumber,babyNumber,money);
        bookTourInteractor.bookTour(tourBooking,this);
    }
    @Override
    public void onButtonCancelClicked(){
        view.close();
    }
    @Override
    public void onButtonDecreaseAdultClicked() {
        if(adultNumber>0) {
            adultNumber--;
            view.updateAdultNumber(adultNumber);
        }
    }
    @Override
    public void onButtonIncreaseAdultClick(){
        if(adultNumber<50) {
            adultNumber++;
            view.updateAdultNumber(adultNumber);
        }
    }
    @Override
    public void onButtonDecreaseChildrenClicked(){
        if(childrenNumber>0) {
            childrenNumber--;
            view.updateChildrenNumber(childrenNumber);
        }
    }
    @Override
    public void onButtonIncreaseChildrenClicked(){
        if(childrenNumber<50) {
            childrenNumber++;
            view.updateChildrenNumber(childrenNumber);
        }
    }
    @Override
    public void onButtonDecreaseBabyClicked(){
        if(babyNumber>0) {
            babyNumber--;
            view.updateBabyNumber(babyNumber);
        }
    }
    @Override
    public void onButtonIncreaseBabyClicked(){
        if(babyNumber<50) {
            babyNumber++;
            view.updateBabyNumber(babyNumber);
        }
    }

    @Override
    public void onGetMyBookTourSuccess(TourBooking tourBooking) {
       if(tourBooking!=null) {
           adultNumber = tourBooking.adult;
           childrenNumber = tourBooking.children;
           babyNumber = tourBooking.baby;
           view.showNumberPeople(tourBooking.adult, tourBooking.children, tourBooking.baby);
           if(tourBooking.accepted)
               view.disableBtnAccept();
       }


    }

    @Override
    public void onGetMyBookTourFailure(DatabaseException ex) {
        view.notify(ex.getMessage());
    }


    @Override
    public void onBookTourSuccess() {
        view.notify("Đặt tour thành công");
    }

    @Override
    public void onBookTourFail(Exception ex) {
        view.notify(ex.getMessage());
    }
}
