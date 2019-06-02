package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.views.bases.SearchTour;

import java.util.HashMap;
import java.util.List;

public class SearchTourActivity extends AppCompatActivity implements SearchTour {

    RecyclerView recyclerViewParent,recyclerViewChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tour);
    }

    void mapping(){
        recyclerViewParent = findViewById(R.id.recyclerViewParent);
        recyclerViewChild = finishFromChild(R.id.recyclerViewChild);
    }

    @Override
    public void showCities(List<City> cityList) {

    }

    @Override
    public void showCompanies(List<Company> companyList) {

    }

    @Override
    public void showAboutToDepartTours(List<Tour> tourList, HashMap<String, TourStartDate> tourStartMap) {

    }

    @Override
    public void showLikedTours(List<Tour> tourList, HashMap<String, Double> ratingMap) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void close() {
        finish();
    }
}
