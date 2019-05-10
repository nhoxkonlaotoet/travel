package com.example.administrator.travel.presenters.impls;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.bases.CityInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.impls.CityInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.NewsFeedPresenter;
import com.example.administrator.travel.views.NewsFeedView;

import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public class NewsFeedPresenterImpl implements NewsFeedPresenter, Listener.OnGetToursFinishedListener,
        Listener.OnGetFirstImageFinishedListener, Listener.OnGetCitiesFinishedListener {
    NewsFeedView view;

    TourInteractor tourInteractor;
    CityInteractor cityInteractor;
    String origin, destination;
    public final static String NONE="";
    List<City> lstCity;
    boolean firstChangeOrigin = true, firstChangeDestination = true;

    public NewsFeedPresenterImpl(NewsFeedView newsFeedView) {
        this.view = newsFeedView;
        tourInteractor = new TourInteractorImpl();
        cityInteractor = new CityInteractorImpl();
        origin = NONE;
        destination = NONE;

    }
    @Override
    public void onViewCreated() {
        cityInteractor.getCities(this);
        tourInteractor.getTours(this);
    }

    @Override
    public void onViewResumed() {
        view.startShimmerTourAnimation();
    }

    @Override
    public void onViewPaused() {
        view.stopShimmerTourAnimation();
    }

    @Override
    public void onTourItemClicked(String tourId, String owner) {
        view.gotoActivityTour(tourId,owner);
    }
    @Override
    public void onItemSpinnerOriginSelected(int pos) {
        if (firstChangeOrigin) {
            firstChangeOrigin = false;
            return;
        }
        if (pos == 0)
            origin = NONE;
        else
            origin = lstCity.get(pos - 1).id;
        if (origin.equals(NONE) && destination.equals(NONE))
            tourInteractor.getTours(this);
        else
            tourInteractor.getTours(origin, destination, this);

    }
    @Override
    public void onItemSpinnerDestinationSelected(int pos) {
        if (firstChangeDestination) {
            firstChangeDestination = false;
            return;
        }
        if (pos == 0)
            destination = NONE;
        else
            destination = lstCity.get(pos - 1).id;
        if (origin.equals(NONE) && destination.equals(NONE))
            tourInteractor.getTours(this);
        else
            tourInteractor.getTours(origin, destination, this);
    }

    @Override
    public void onGetToursSuccess(List<Tour> tourList) {
        view.stopShimmerTourAnimation();
        view.hideShimmerViewTour();
        view.showRecyclerViewTour();
        view.showTours(tourList);
        int n = tourList.size();
        for (int i = 0; i < n; i++)
            tourInteractor.getFirstImage(i, tourList.get(i).id, this);
    }

    @Override
    public void onGetToursFail(Exception ex) {

    }

    @Override
    public void onGetFirstImageSuccess(int pos, String tourId, Bitmap image) {
        view.updateTourImage(pos, tourId, image);
    }

    @Override
    public void onGetFirstImageFail(Exception ex) {

    }

    @Override
    public void onGetCitiesSuccess(List<City> cities) {
        view.showCities(cities);
        this.lstCity = cities;
    }

    @Override
    public void onGetCitiesFail(Exception ex) {

    }
}
