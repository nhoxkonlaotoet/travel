package com.example.administrator.travel.presenters;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.administrator.travel.models.bases.CityInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.impls.CityInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.views.NewFeedView;

import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public class NewFeedPresenter implements Listener.OnGetToursFinishedListener,
        Listener.OnGetFirstImageFinishedListener, Listener.OnGetCitiesFinishedListener {
    NewFeedView view;

    TourInteractor tourInteractor;
    CityInteractor cityInteractor;
    String origin, destination;
    String none;
    List<City> lstCity;
    boolean firstChangeOrigin=true,firstChangeDestination=true;
    public NewFeedPresenter(NewFeedView newFeedView)
    {
        this.view=newFeedView;
        tourInteractor = new TourInteractorImpl();
        cityInteractor = new CityInteractorImpl();
        none="";
        origin=none;
        destination=none;

    }
    public void onViewLoad() {
        cityInteractor.getCities(this);
        tourInteractor.getTours(this);
    }

    public void onItemListViewTourClicked(String tourId, String owner){
        view.gogotActivityTour(tourId,owner);
    }
    public void onItemSpinnerOriginSelected(int pos){
        if(firstChangeOrigin)
        {
            firstChangeOrigin=false;
            return;
        }
        if(pos==0)
            origin=none;
        else
            origin = lstCity.get(pos - 1).id;
        if(origin.equals(none)&&destination.equals(none))
            tourInteractor.getTours(this);
        else
            tourInteractor.getTours(origin, destination, this);

    }
    public void onItemSpinnerDestinationSelected(int pos){
        if(firstChangeDestination) {
            firstChangeDestination = false;
            return;
        }
        if(pos==0)
            destination=none;
        else
            destination = lstCity.get(pos - 1).id;
        if(origin.equals(none)&&destination.equals(none))
            tourInteractor.getTours(this);
        else
            tourInteractor.getTours(origin, destination, this);
    }

    @Override
    public void onGetToursSuccess(List<Tour> tours) {
        view.showTours(tours);
        for(Tour tour : tours)
            tourInteractor.updateFirstImage(tour.id,this);
    }

    @Override
    public void onGetToursFail(Exception ex) {

    }

    @Override
    public void onGetFirstImageSuccess(String tourId, Bitmap image) {
        view.updateTourImage(tourId,image);
    }

    @Override
    public void onGetFirstImageFail(Exception ex) {

    }

    @Override
    public void onGetCitiesSuccess(List<City> cities) {
        view.showCities(cities);
        this.lstCity=cities;
    }

    @Override
    public void onGetCitiesFail(Exception ex) {

    }
}
