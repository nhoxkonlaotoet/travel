package com.example.administrator.travel.presenters;

import com.example.administrator.travel.models.AcceptBookTourInteractor;
import com.example.administrator.travel.models.OnAcceptBookingFinishListener;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.views.AcceptBookingView;
import com.example.administrator.travel.views.fragments.AcceptBookingFragment;

import java.util.List;

/**
 * Created by Administrator on 02/01/2019.
 */

public class AcceptBookingPresenter implements OnAcceptBookingFinishListener{
    AcceptBookingView view;
    AcceptBookTourInteractor acceptBookTourInteractor;

    String tourStartId;

    public AcceptBookingPresenter(AcceptBookingView view)
    {
        this.view=view;
        acceptBookTourInteractor=new AcceptBookTourInteractor();
    }
    public void onViewLoad(String tourStartId){
        this.tourStartId=tourStartId;
        acceptBookTourInteractor.getBookings(tourStartId,this);
    }

    @Override
    public void onGetBookingsSuccess(List<TourBooking> lstBooking) {
        view.showBookings(lstBooking);
        int n= lstBooking.size();
        for(int i=0;i<n;i++){
            acceptBookTourInteractor.getUserInfor(lstBooking.get(i).userId,this);
        }
    }

    @Override
    public void onGetBookingsFinishedTour() {
        view.notifyTourFinished();
    }

    @Override
    public void onGetBookingsFailure(Exception ex) {

    }

    @Override
    public void onGetUserInforSuccess(UserInformation user) {
        view.showUserInfo(user);
    }
}
