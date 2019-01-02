package com.example.administrator.travel.presenters;

import com.example.administrator.travel.models.BookTourInteractor;
import com.example.administrator.travel.models.OnBookTourFinishedListener;
import com.example.administrator.travel.models.OnGetServerTimeFinishedListener;
import com.example.administrator.travel.models.GetServerTimeInteractor;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.views.BookTourView;
import com.example.administrator.travel.views.activities.BookTourActivity;

/**
 * Created by Administrator on 18/11/2018.
 */

public class BookTourPresenter implements OnBookTourFinishedListener, OnGetServerTimeFinishedListener{
    BookTourView view;
    BookTourInteractor bookTourInteractor;
    int adultNumber=0, childrenNumber=0, babyNumber=0;
    int adultPrice, childrenPrice, babyPrice;
    String tourStartId;

    public BookTourPresenter(BookTourView view){
        this.view = view;
        bookTourInteractor = new BookTourInteractor();
    }

    public void onViewLoad(String tourStartId){
        this.tourStartId=tourStartId;
        bookTourInteractor.getTourBooking(tourStartId, (BookTourActivity)view,this);
        bookTourInteractor.getPrices(tourStartId,this);
    }

    public void onBtnAcceptClicked(){
        if(adultNumber<0)
            return;
        int money = adultNumber*adultPrice + childrenNumber*childrenPrice + babyNumber*babyPrice;
        TourBooking tourBooking = new TourBooking(null,tourStartId,0L,adultNumber,childrenNumber,babyNumber,money);
        bookTourInteractor.bookTour((BookTourActivity)view,tourBooking,this);
    }
    public void onBtnCancelClicked(){
        view.close();
    }
    public void onBtnDecreaseAdultClicked() {
        if(adultNumber>0) {
            adultNumber--;
            view.updateAdultNumber(adultNumber);
        }
    }
    public void onBtnIncreaseAdultClick(){
        if(adultNumber<50) {
            adultNumber++;
            view.updateAdultNumber(adultNumber);
        }
    }
    public void onBtnDecreaseChildrenClicked(){
        if(childrenNumber>0) {
            childrenNumber--;
            view.updateChildrenNumber(childrenNumber);
        }
    }
    public void onBtnIncreaseChildrenClicked(){
        if(childrenNumber<50) {
            childrenNumber++;
            view.updateChildrenNumber(childrenNumber);
        }
    }
    public void onBtnDecreaseBabyClicked(){
        if(babyNumber>0) {
            babyNumber--;
            view.updateBabyNumber(babyNumber);
        }
    }
    public void onBtnIncreaseBabyClicked(){
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
    public void onGetMyBookTourFailure(Exception ex) {

    }

    @Override
    public void onGetPricesSuccess(TourStartDate tourStartDate) {
        adultPrice=tourStartDate.adultPrice;
        childrenPrice=tourStartDate.childrenPrice;
        babyPrice=tourStartDate.babyPrice;
        view.showPrice(tourStartDate.adultPrice,tourStartDate.childrenPrice,tourStartDate.babyPrice,
                tourStartDate.maxPeople-tourStartDate.peopleBooking);
    }

    @Override
    public void onGetPricesFailure(Exception ex) {

    }

    @Override
    public void onBookTourSuccess() {
        view.notifyBookingSuccess();
        view.close();
    }

    @Override
    public void onBookTourFailure(Exception ex) {
        view.notifyBookingFailure(ex);
    }

    @Override
    public void onGetTimeSuccess(Long time) {

    }
    @Override
    public void onGetTimeFailure(Exception ex)
    {

    }
}
