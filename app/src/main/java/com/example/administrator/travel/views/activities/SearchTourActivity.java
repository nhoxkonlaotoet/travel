package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.CityAdapter;
import com.example.administrator.travel.adapter.CompanyAdapter;
import com.example.administrator.travel.adapter.TourAboutToDepartAdapter;
import com.example.administrator.travel.adapter.TourAdapter;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.bases.SearchTourPresenter;
import com.example.administrator.travel.presenters.impls.SearchTourPresenterImpl;
import com.example.administrator.travel.views.bases.SearchTourView;

import java.util.HashMap;
import java.util.List;

public class SearchTourActivity extends AppCompatActivity implements SearchTourView, CityAdapter.CityClickListener,
        TourAdapter.ItemClickListener, CompanyAdapter.CompanyClickListener, TourAboutToDepartAdapter.ItemClickListener {
    private RecyclerView recyclerViewParent, recyclerViewChild;
    private TextView txtHaveNoResult;
    SearchTourPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tour);
        mapping();
        Bundle bundle = getIntent().getExtras();
        presenter = new SearchTourPresenterImpl(this);
        presenter.onViewCreated(bundle);
    }

    void mapping() {
        recyclerViewParent = findViewById(R.id.recyclerViewParent);
        recyclerViewChild = findViewById(R.id.recyclerViewChild);
        txtHaveNoResult = findViewById(R.id.txtHaveNoResult);
    }


    @Override
    public void showCities(List<City> cityList) {
        CityAdapter cityAdapter = new CityAdapter(this, cityList);
        cityAdapter.setClickListener(this);
        recyclerViewParent.setAdapter(cityAdapter);
    }

    @Override
    public void showCompanies(List<Company> companyList) {
        CompanyAdapter companyAdapter = new CompanyAdapter(this,companyList);
        companyAdapter.setClickListener(this);
        recyclerViewParent.setAdapter(companyAdapter);

    }

    @Override
    public void showAboutToDepartTours(List<Tour> tourList, HashMap<String, TourStartDate> tourStartMap) {
        TourAboutToDepartAdapter tourAboutToDepartAdapter = new TourAboutToDepartAdapter(this, tourList,tourStartMap);
        tourAboutToDepartAdapter.setClickListener(this);
        recyclerViewChild.setAdapter(tourAboutToDepartAdapter);
    }

    @Override
    public void showLikedTours(List<Tour> tourList, HashMap<String, Double> ratingMap) {
        TourAdapter tourAdapter = new TourAdapter(this, tourList, ratingMap);
        tourAdapter.setClickListener(this);
        recyclerViewChild.setAdapter(tourAdapter);
    }


    @Override
    public void close() {
        finish();
    }

    @Override
    public void gotoActivityTour(String tourId, String owner) {
        Intent intent = new Intent(SearchTourActivity.this, TourActivity.class);
        intent.putExtra("tourId", tourId);
        intent.putExtra("owner", owner);
        startActivity(intent);
    }

    @Override
    public void showTextHaveNoResult() {
            txtHaveNoResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTextHaveNoResult() {
        txtHaveNoResult.setVisibility(View.GONE);
    }


    @Override
    public void notify(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemCityClick(View view, String cityId) {
        presenter.onItemCityClicked(cityId);
    }

    @Override
    public void onTourItemClick(View view, String tourId, String ownerId) {
        presenter.onItemTourClicked(tourId, ownerId);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onItemCompanyClick(View view, String companyId) {
        presenter.onItemCompanyClick(companyId);
    }
}
