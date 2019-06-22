package com.example.administrator.travel.presenters.impls;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.travel.models.bases.TourStartInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.TourBookingDetail;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.TourStartInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.BookTourPresenter;
import com.example.administrator.travel.views.BookTourView;

import java.util.List;

/**
 * Created by Administrator on 18/11/2018.
 */

public class BookTourPresenterImpl implements BookTourPresenter, Listener.OnBookTourFinishedListener,
        Listener.OnGetTourStartFinishedListener {
    private final static int TOURIST_TYPE_ADULT = 0;
    private final static int TOURIST_TYPE_CHILDREN = 1;
    private final static int TOURIST_TYPE_BABY = 2;
    private final static int AUTHORIZATION_CODE = 999;

    private BookTourView view;
    private TourStartInteractor tourStartInteractor;
    private UserInteractor userInteractor;
    private Integer numberOfAdult = 0, numberOfChildren = 0, numberOfBaby = 0, totalAvailableSlot;
    private int adultPrice, childrenPrice, babyPrice;
    private String tourStartId, companyId;

    public BookTourPresenterImpl(BookTourView view) {
        this.view = view;
        tourStartInteractor = new TourStartInteractorImpl();
        userInteractor = new UserInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        if (!userInteractor.isLogged()) {
            view.gotoLoginActivity();
        }
        tourStartId = bundle.getString("tourStartId");
        companyId = bundle.getString("owner");
        tourStartInteractor.getTourStart(tourStartId, this);
        view.hideButtonNext();
    }

    @Override
    public void onEtxtNumberOfAdultTypingStoped(int numberOfAdult) {
        if (this.numberOfAdult == numberOfAdult)
            return;
        int tempNumber = numberOfAdult - this.numberOfAdult;
        this.numberOfAdult = numberOfAdult;
        if (totalAvailableSlot >= this.numberOfAdult + numberOfChildren + numberOfBaby) {
            if (tempNumber > 0) {
                view.addTourists(tempNumber, TOURIST_TYPE_ADULT, adultPrice);
            } else {
                view.removeTourists(-tempNumber, TOURIST_TYPE_ADULT);
            }
        }
        if (this.numberOfAdult + numberOfChildren + numberOfBaby > 0)
            view.showButtonNext();
        else
            view.hideButtonNext();
    }

    @Override
    public void onEtxtNumberOfChildrenTypingStoped(int numberOfChildren) {
        if (this.numberOfChildren == numberOfChildren)
            return;
        int tempNumber = numberOfChildren - this.numberOfChildren;
        this.numberOfChildren = numberOfChildren;
        if (totalAvailableSlot >= numberOfAdult + this.numberOfChildren + numberOfBaby) {
            if (totalAvailableSlot >= this.numberOfAdult + numberOfChildren + numberOfBaby) {
                if (tempNumber > 0) {
                    view.addTourists(tempNumber, TOURIST_TYPE_CHILDREN, childrenPrice);
                } else {
                    view.removeTourists(-tempNumber, TOURIST_TYPE_CHILDREN);
                }
            }
        }
        if (numberOfAdult + this.numberOfChildren + numberOfBaby > 0)
            view.showButtonNext();
        else
            view.hideButtonNext();
    }

    @Override
    public void onEtxtNumberOfBabyTypingStoped(int numberOfBaby) {
        if (this.numberOfBaby == numberOfBaby)
            return;
        int tempNumber = numberOfBaby - this.numberOfBaby;
        this.numberOfBaby = numberOfBaby;
        if (totalAvailableSlot >= numberOfAdult + numberOfChildren + this.numberOfBaby) {
            if (tempNumber > 0) {
                view.addTourists(tempNumber, TOURIST_TYPE_BABY, babyPrice);
            } else {
                view.removeTourists(-tempNumber, TOURIST_TYPE_BABY);
            }
        }
        if (numberOfAdult + numberOfChildren + this.numberOfBaby > 0)
            view.showButtonNext();
        else
            view.hideButtonNext();
    }

    @Override
    public void onButtonNextClicked() {

        if (view.getBookingAdapter().getTourists() != null && view.getBookingAdapter().getTourists().size() > 0
                && numberOfAdult + numberOfChildren + numberOfBaby > 0
                && totalAvailableSlot >= numberOfAdult + numberOfChildren + numberOfBaby) {
            Integer money = numberOfAdult * adultPrice + numberOfChildren * childrenPrice + numberOfBaby * babyPrice;
            view.gotoCardAuthorizationActivity(AUTHORIZATION_CODE, null,
                    tourStartId, numberOfAdult, numberOfChildren, numberOfBaby, money, companyId);
        }
    }

    @Override
    public void onViewResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTHORIZATION_CODE)
            if (resultCode == Activity.RESULT_OK) {
                String senderCardNumber = data.getStringExtra("cardNumber");
                List<TourBookingDetail> tourBookingDetailList = view.getBookingAdapter().getTourists();

            } else if (resultCode == Activity.RESULT_CANCELED)
                Log.e("onActivityResult: ", requestCode + "  " + resultCode);
    }

    @Override
    public void onBookTourSuccess() {
        view.notify("Đặt tour thành công");
    }

    @Override
    public void onBookTourFail(Exception ex) {
        view.notify(ex.getMessage());
    }

    @Override
    public void onGetTourStartSuccess(TourStartDate tourStartDate) {
        Log.e("onGetTourStart: ", tourStartDate.id + " " + tourStartDate.adultPrice + " " + tourStartDate.childrenPrice + " " + tourStartDate.babyPrice);
        adultPrice = tourStartDate.adultPrice;
        childrenPrice = tourStartDate.childrenPrice;
        babyPrice = tourStartDate.babyPrice;
        totalAvailableSlot = tourStartDate.maxPeople - tourStartDate.peopleBooking;
        view.showPrice(tourStartDate.adultPrice, tourStartDate.childrenPrice, tourStartDate.babyPrice);
        view.showNumberAvailableSlot(tourStartDate.maxPeople - tourStartDate.peopleBooking);
    }

    @Override
    public void onGetTourStartFail(Exception ex) {

    }


}
