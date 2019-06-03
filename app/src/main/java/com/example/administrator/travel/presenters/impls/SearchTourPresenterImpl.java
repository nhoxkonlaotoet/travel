package com.example.administrator.travel.presenters.impls;

import android.os.Bundle;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.CityInteractor;
import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.CityInteractorImpl;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.SearchTourPresenter;
import com.example.administrator.travel.views.bases.SearchTourView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 6/2/2019.
 */

public class SearchTourPresenterImpl implements SearchTourPresenter, Listener.OnGetCitiesFinishedListener, Listener.OnGetToursFinishedListener, Listener.OnGetAboutToDepartToursFinishedListener {
    SearchTourView view;
    TourInteractor tourInteractor;
    CompanyInteractor companyInteractor;
    CityInteractor cityInteractor;
    boolean inputHasCityId;

    String filter, cityId;

    public SearchTourPresenterImpl(SearchTourView view) {
        this.view = view;
        tourInteractor = new TourInteractorImpl();
        companyInteractor = new CompanyInteractorImpl();
        cityInteractor = new CityInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        filter = bundle.getString("filter");
        switch (filter) {
            case "city":
                cityInteractor.getCities(this);
                cityId = bundle.getString("cityId");
                inputHasCityId = true;
                tourInteractor.getToursByDestination(cityId, this);
                break;
            case "aboutToDepartTour":
                tourInteractor.getAboutToDepartTours(this);

                break;

        }

    }

    @Override
    public void onTextSearchChanged(String keyword) {

    }

    @Override
    public void onButtonSearchClicked() {

    }

    @Override
    public void onItemCityClicked(String cityId) {
        tourInteractor.getToursByDestination(cityId, this);
        view.clearRecyclerViewChild();
        view.showShimmerContainerChild();
        view.startShimmerContainerChildAnimation();
        view.hideTextHaveNoResult();
    }

    @Override
    public void onItemTourClicked(String tourId, String owner) {
        view.gotoActivityTour(tourId, owner);
    }

    @Override
    public void onGetCitiesSuccess(List<City> cityList) {
        if (inputHasCityId) {
            //onItemCityClicked(cityId);
        }
        view.showCities(cityList);
        view.stopShimmerContainerParentAnimation();
        view.hideShimmerContainerParent();
    }

    @Override
    public void onGetCitiesFail(Exception ex) {
        view.notify(ex.getMessage());
        view.stopShimmerContainerParentAnimation();
        view.hideShimmerContainerParent();
    }

    @Override
    public void onGetToursSuccess(List<Tour> tourList, HashMap<String, Double> ratingMap) {
        if (tourList.size() > 0)
            view.showLikedTours(tourList, ratingMap);
        else {
            view.notify("không tìm thấy");
            view.showTextHaveNoResult();
        }
        view.stopShimmerContainerChildAnimation();
        view.hideShimmerContainerChild();
    }

    @Override
    public void onGetToursFail(Exception ex) {
        view.notify(ex.getMessage());
        view.stopShimmerContainerChildAnimation();
        view.hideShimmerContainerChild();
    }

    @Override
    public void onGetAboutToDepartToursSuccess(List<Tour> tourList, HashMap<String, TourStartDate> tourStartMap) {
        if (tourList.size() > 0)
            view.showAboutToDepartTours(tourList, tourStartMap);
        else
            view.showTextHaveNoResult();
        view.stopShimmerContainerChildAnimation();
        view.hideShimmerContainerChild();
    }

    @Override
    public void onGetAboutToDepartToursFail(Exception ex) {
        view.notify(ex.getMessage());
        view.hideShimmerContainerChild();
    }
}
