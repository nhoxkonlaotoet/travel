package com.example.administrator.travel.presenters;

import android.util.Log;

import com.example.administrator.travel.models.entities.NewFeedInteractor;
import com.example.administrator.travel.models.entities.NewFeedInteractorImpl;
import com.example.administrator.travel.models.entities.OnGetNewFeedItemFinishedListener;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.views.NewFeedView;

import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public class NewFeedPresenterImpl implements NewFeedPresenter,OnGetNewFeedItemFinishedListener {
    NewFeedView newFeedView;
    NewFeedInteractor newFeedInteractor;


    public NewFeedPresenterImpl(NewFeedView newFeedView)
    {
        this.newFeedView=newFeedView;
        newFeedInteractor=new NewFeedInteractorImpl();

    }
    @Override
    public void getTours() {
       newFeedInteractor.getTour(this);

    }
    @Override
    public void onSuccess(List<Tour> listTour) {
        for(Tour tour : listTour)
        {
            Log.e("onSuccess: ", tour.toString());
        }
        newFeedView.showTours(listTour);
    }


}
