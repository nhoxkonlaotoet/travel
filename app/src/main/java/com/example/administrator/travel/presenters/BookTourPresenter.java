package com.example.administrator.travel.presenters;

import com.example.administrator.travel.models.BookTourInteractor;
import com.example.administrator.travel.models.OnBookTourFinishedListener;
import com.example.administrator.travel.models.OnGetServerTimeFinishedListener;
import com.example.administrator.travel.models.GetServerTimeInteractor;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.views.BookTourView;

/**
 * Created by Administrator on 18/11/2018.
 */

public class BookTourPresenter implements OnBookTourFinishedListener, OnGetServerTimeFinishedListener{
    BookTourView view;
    BookTourInteractor bookTourInteractor;
    GetServerTimeInteractor serverTimeInteractor;
    public BookTourPresenter(BookTourView view){
        this.view = view;
        bookTourInteractor = new BookTourInteractor();
        serverTimeInteractor=new GetServerTimeInteractor();
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
    public void getCurrentTime(){
        serverTimeInteractor.getCurrentTime(this);
    }
    public void bookTour(String tourId, TourBooking tourBooking){
        bookTourInteractor.bookTour(tourId,tourBooking,this);
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
        view.receivedCurrentTime(time);
    }
    @Override
    public void onGetTimeFailure(Exception ex)
    {
        view.receivedCurrentTime(ex);
    }
}
