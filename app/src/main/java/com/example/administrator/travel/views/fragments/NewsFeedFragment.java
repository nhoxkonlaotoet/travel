package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.LinearLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.CityAdapter;
import com.example.administrator.travel.adapter.CompanyAdapter;
import com.example.administrator.travel.adapter.TourAboutToDepartAdapter;
import com.example.administrator.travel.adapter.TourAdapter;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.bases.NewsFeedPresenter;
import com.example.administrator.travel.presenters.impls.NewsFeedPresenterImpl;
import com.example.administrator.travel.views.activities.SearchTourActivity;
import com.example.administrator.travel.views.bases.NewsFeedView;
import com.example.administrator.travel.views.activities.TourActivity;


import java.util.HashMap;
import java.util.List;

public class NewsFeedFragment extends Fragment implements NewsFeedView, TourAboutToDepartAdapter.ItemClickListener,
        CityAdapter.CityClickListener, TourAdapter.ItemClickListener, CompanyAdapter.CompanyClickListener {
    private RecyclerView recyclerViewTour, recyclerViewCity, recyclerLikedTour, recyclerCompany;
    private NewsFeedPresenter presenter;
    private CardView layoutCities, layoutAboutToDepartTours, layoutLikedTours,layoutCompany;

    public NewsFeedFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_feed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapping();
        presenter = new NewsFeedPresenterImpl(this);
        presenter.onViewCreated();

    }

    void mapping() {
        recyclerViewTour = getActivity().findViewById(R.id.recyclerViewTour);
        recyclerViewCity = getActivity().findViewById(R.id.recyclerViewCity);
        recyclerLikedTour = getActivity().findViewById(R.id.recyclerLikedTour);
        recyclerCompany = getActivity().findViewById(R.id.recyclerCompany);

        layoutCities = getActivity().findViewById(R.id.layoutCities);
        layoutAboutToDepartTours = getActivity().findViewById(R.id.layoutAboutToDepartTours);
        layoutLikedTours = getActivity().findViewById(R.id.layoutLikedTours);
        layoutCompany = getActivity().findViewById(R.id.layoutCompany);
    }

    @Override
    public void showCities(List<City> cityList) {
        CityAdapter cityAdapter = new CityAdapter(getActivity(), cityList);
        cityAdapter.setClickListener(this);
        recyclerViewCity.setAdapter(cityAdapter);
    }


    @Override
    public void showAboutToDepartTours(List<Tour> listTour, HashMap<String, TourStartDate> tourStartMap) {
        TourAboutToDepartAdapter tourAboutToDepartAdapter = new TourAboutToDepartAdapter(getActivity(), listTour, tourStartMap);
        tourAboutToDepartAdapter.setClickListener(this);
        recyclerViewTour.setAdapter(tourAboutToDepartAdapter);
    }

    @Override
    public void showLikedTours(List<Tour> listTour, HashMap<String, Double> ratingMap) {
        TourAdapter tourAdapter = new TourAdapter(getActivity(), listTour, ratingMap);
        tourAdapter.setClickListener(this);
        recyclerLikedTour.setAdapter(tourAdapter);
    }

    @Override
    public void showCompanies(List<Company> companyList) {
        CompanyAdapter companyAdapter = new CompanyAdapter(getActivity(), companyList);
        companyAdapter.setClickListener(this);
        recyclerCompany.setAdapter(companyAdapter);
    }


    @Override
    public void gotoActivityTour(String tourId, String owner) {
        Intent intent = new Intent(getActivity(), TourActivity.class);
        intent.putExtra("tourId", tourId);
        intent.putExtra("mytour", false);
        intent.putExtra("owner", owner);
        startActivity(intent);
    }

    @Override
    public void gotoActivitySearchTour(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void notify(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLayoutCities() {
        layoutCities.setVisibility(View.GONE);
    }

    @Override
    public void hideLayoutAboutToDepartTours() {
        layoutAboutToDepartTours.setVisibility(View.GONE);
    }

    @Override
    public void hideLayoutLikedTours() {
        layoutLikedTours.setVisibility(View.GONE);
    }

    @Override
    public void onTourItemClick(View view, String tourId, String ownerId) {
        presenter.onItemTourClicked(tourId, ownerId);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onItemCityClick(View view, String cityId) {
        presenter.onItemCityClicked(cityId);
    }

    @Override
    public void onItemCompanyClick(View view, String companyId) {
        presenter.onItemCompanyClick(companyId);
    }

}
