package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.util.Log;

import com.example.administrator.travel.models.bases.CityInteractor;
import com.example.administrator.travel.models.bases.CompanyInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.impls.CityInteractorImpl;
import com.example.administrator.travel.models.impls.CompanyInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.NewsFeedPresenter;
import com.example.administrator.travel.views.activities.SearchTourActivity;
import com.example.administrator.travel.views.bases.NewsFeedView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public class NewsFeedPresenterImpl implements NewsFeedPresenter, Listener.OnGetAboutToDepartToursFinishedListener,
        Listener.OnGetCitiesFinishedListener, Listener.OnGetLikedToursFinishedListener, Listener.OnGetCompaniesFinishedListener {
    NewsFeedView view;
    Long start;
    TourInteractor tourInteractor;
    CityInteractor cityInteractor;
    CompanyInteractor companyInteractor;
    public NewsFeedPresenterImpl(NewsFeedView newsFeedView) {
        this.view = newsFeedView;
        tourInteractor = new TourInteractorImpl();
        cityInteractor = new CityInteractorImpl();
        companyInteractor = new CompanyInteractorImpl();
    }

    @Override
    public void onViewCreated() {
        start = System.nanoTime();
        cityInteractor.getCities(this);
        tourInteractor.getAboutToDepartTours(this);
        tourInteractor.getLikedTours(this);
        companyInteractor.getCompanies(this);
    }

    @Override
    public void onItemCityClicked(String cityId) {
        Intent intent = new Intent(view.getContext(), SearchTourActivity.class);
        intent.putExtra("filter", "city");
        intent.putExtra("cityId", cityId);
        view.gotoActivitySearchTour(intent);
    }


    @Override
    public void onItemTourClicked(String tourId, String owner) {
        view.gotoActivityTour(tourId, owner);
    }

    @Override
    public void onItemCompanyClick(String companyId) {
        Intent intent = new Intent(view.getContext(), SearchTourActivity.class);
        intent.putExtra("filter", "company");
        intent.putExtra("companyId", companyId);
        view.gotoActivitySearchTour(intent);
    }

    @Override
    public void onGetAboutToDepartToursSuccess(List<Tour> tourList, HashMap<String, TourStartDate> tourStartMap) {
        Log.e("get tours time: ", System.nanoTime() - start + "");
        view.showAboutToDepartTours(tourList, tourStartMap);
    }

    @Override
    public void onGetAboutToDepartToursFail(Exception ex) {
        view.hideLayoutAboutToDepartTours();
    }


    @Override
    public void onGetCitiesSuccess(List<City> cities) {
        view.showCities(cities);
    }

    @Override
    public void onGetCitiesFail(Exception ex) {
        view.hideLayoutCities();
    }

    @Override
    public void onGetLikedToursSuccess(List<Tour> tourList, HashMap<String, Double> ratingMap) {
        view.showLikedTours(tourList,ratingMap);
    }

    @Override
    public void onGetLikedToursFail(Exception ex) {
        view.hideLayoutLikedTours();
    }

    @Override
    public void onGetCompaniesSuccess(List<Company> companyList) {
        view.showCompanies(companyList);
    }

    @Override
    public void onGetCompaniesFail(Exception ex) {

    }
}
