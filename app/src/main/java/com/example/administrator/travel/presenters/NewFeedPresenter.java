package com.example.administrator.travel.presenters;

import android.util.Log;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.NewFeedInteractor;
import com.example.administrator.travel.models.OnGetNewFeedItemFinishedListener;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.views.NewFeedView;
import com.example.administrator.travel.views.fragments.NewsFeedFragment;

import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public class NewFeedPresenter implements OnGetNewFeedItemFinishedListener {
    NewFeedView newFeedView;
    NewFeedInteractor newFeedInteractor;
    String origin, destination;
    String none;
    List<City> lstCity;
    public NewFeedPresenter(NewFeedView newFeedView)
    {
        this.newFeedView=newFeedView;
        newFeedInteractor=new NewFeedInteractor();
        none="";
        origin=none;
        destination=none;

    }
    public void onViewLoad() {
        newFeedInteractor.getCities(this);
        newFeedInteractor.getTours(this);

    }

    public void onItemListViewTourClicked(String tourId, String owner){
        newFeedView.gogotActivityTour(tourId,owner);
    }
    public void onItemSpinnerOriginSelected(int pos){
        if(pos==0)
            origin=none;
        else
            origin = lstCity.get(pos - 1).id;
        if(origin.equals(none)&&destination.equals(none))
            newFeedInteractor.getTours(this);
        else
            newFeedInteractor.getTours(origin, destination, this);
    }
    public void onItemSpinnerDestinationSelected(int pos){
        if(pos==0)
            destination=none;
        else
            destination = lstCity.get(pos - 1).id;
        if(origin.equals(none)&&destination.equals(none))
            newFeedInteractor.getTours(this);
        else
            newFeedInteractor.getTours(origin, destination, this);
    }
    @Override
    public void onGetItemsSuccess(List<Tour> listTour) {
        Log.e( "onGetItemsSuccess: ", "   ");
        newFeedView.showTours(listTour);

    }

    @Override
    public void onGetItemsFailure(Exception ex) {

    }

    @Override
    public void onGetCitiesSuccess(List<City> lstCity) {
        this.lstCity=lstCity;
        newFeedView.showCities(lstCity);
    }

    @Override
    public void onGetCitesFailure(Exception ex) {

    }
}
