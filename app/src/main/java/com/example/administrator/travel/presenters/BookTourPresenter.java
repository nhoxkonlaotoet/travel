package com.example.administrator.travel.presenters;

import android.view.View;

import com.example.administrator.travel.models.BookTourInteractor;
import com.example.administrator.travel.models.OnBookTourFinishedListener;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.views.BookTourView;

/**
 * Created by Administrator on 18/11/2018.
 */

public class BookTourPresenter implements OnBookTourFinishedListener{
    BookTourView view;
    BookTourInteractor interactor;
    public BookTourPresenter(BookTourView view){
        this.view = view;
        interactor = new BookTourInteractor();
    }
    public void onBtnAcceptClicked(){
        view.sendBookingTour();
    }
    public void onBtnCancelClicked(){
        view.close();
    }
    public void onBtnDecreaseAdultClicked() {
        view.changeNumberOfPeople(view.TYPE_ADULT,-1);
    }
    public void onBtnIncreaseAdultClick(){
        view.changeNumberOfPeople(view.TYPE_ADULT,1);
    }
    public void onBtnDecreaseChildrenClicked(){
        view.changeNumberOfPeople(view.TYPE_CHILDREN,-1);
    }
    public void onBtnIncreaseChildrenClicked(){
        view.changeNumberOfPeople(view.TYPE_CHILDREN,1);
    }
    public void onBtnDecreaseBabyClicked(){
        view.changeNumberOfPeople(view.TYPE_BABY,-1);
    }
    public void onBtnIncreaseBabyClicked(){
        view.changeNumberOfPeople(view.TYPE_BABY,1);
    }
    public void bookTour(String tourStartId, TourBooking tourBooking){
        interactor.bookTour(tourStartId,tourBooking,this);
    }
    @Override
    public void onSuccess() {
        view.notifyBookingSuccess();
        view.close();
    }

    @Override
    public void onFailure(Exception ex) {
        view.notifyBookingFailure();
    }
}
