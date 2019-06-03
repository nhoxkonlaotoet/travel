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
import com.example.administrator.travel.adapter.TourAdapter;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Company;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.bases.SearchTourPresenter;
import com.example.administrator.travel.presenters.impls.SearchTourPresenterImpl;
import com.example.administrator.travel.views.bases.SearchTourView;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;
import java.util.List;

public class SearchTourActivity extends AppCompatActivity implements SearchTourView, CityAdapter.CityClickListener, TourAdapter.ItemClickListener, CompanyAdapter.CompanyClickListener {
    private ShimmerFrameLayout shimmerContainerParent, shimmerContainerChild;
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
        shimmerContainerParent.startShimmerAnimation();
    }

    void mapping() {
        recyclerViewParent = findViewById(R.id.recyclerViewParent);
        recyclerViewChild = findViewById(R.id.recyclerViewChild);
        shimmerContainerParent = findViewById(R.id.shimmerContainerParent);
        shimmerContainerChild = findViewById(R.id.shimmerContainerChild);
        txtHaveNoResult = findViewById(R.id.txtHaveNoResult);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerContainerParent.startShimmerAnimation();
        shimmerContainerChild.startShimmerAnimation();

    }

    @Override
    protected void onPause() {
        shimmerContainerParent.stopShimmerAnimation();
        shimmerContainerChild.stopShimmerAnimation();
        super.onPause();
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
        TourAdapter tourAdapter = new TourAdapter(this, tourStartMap, tourList);
        tourAdapter.setClickListener(this);
        recyclerViewChild.setAdapter(tourAdapter);
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
    public void startShimmerContainerParentAnimation() {
        shimmerContainerParent.startShimmerAnimation();
    }

    @Override
    public void stopShimmerContainerParentAnimation() {
        shimmerContainerParent.stopShimmerAnimation();
    }

    @Override
    public void startShimmerContainerChildAnimation() {
        shimmerContainerChild.startShimmerAnimation();
    }

    @Override
    public void stopShimmerContainerChildAnimation() {
        shimmerContainerChild.stopShimmerAnimation();
    }

    @Override
    public void hideShimmerContainerParent() {
        if(shimmerContainerParent.getVisibility()==View.VISIBLE)
            shimmerContainerParent.setVisibility(View.GONE);
    }

    @Override
    public void showShimmerContainerParent() {
        if(shimmerContainerParent.getVisibility()==View.GONE)
            shimmerContainerParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShimmerContainerChild() {
        if(shimmerContainerChild.getVisibility()==View.VISIBLE)
            shimmerContainerChild.setVisibility(View.GONE);
    }

    @Override
    public void showShimmerContainerChild() {
        if(shimmerContainerChild.getVisibility()==View.GONE)
            shimmerContainerChild.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTextHaveNoResult() {
        if(txtHaveNoResult.getVisibility()==View.GONE)
            txtHaveNoResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTextHaveNoResult() {
        if(txtHaveNoResult.getVisibility()==View.VISIBLE)
            txtHaveNoResult.setVisibility(View.GONE);
    }

    @Override
    public void clearRecyclerViewParent() {
        recyclerViewParent.setAdapter(null);
    }

    @Override
    public void clearRecyclerViewChild() {
        recyclerViewChild.setAdapter(null);
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
