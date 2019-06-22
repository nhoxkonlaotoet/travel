package com.example.administrator.travel.views.bases;

import android.content.Context;

import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.util.HashMap;
import java.util.List;


public interface SearchTourView {
    void showCities(List<City> cityList);

    void showCompanies(List<Company> companyList);

    void showAboutToDepartTours(List<Tour> tourList, HashMap<String, TourStartDate> tourStartMap);

    void showLikedTours(List<Tour> tourList, HashMap<String, Double> ratingMap);

    void close();

    void gotoActivityTour(String tourId, String owner);

    void showTextHaveNoResult();

    void hideTextHaveNoResult();

    void notify(String message);

    Context getContext();

}
