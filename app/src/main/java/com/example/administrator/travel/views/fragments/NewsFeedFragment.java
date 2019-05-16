package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.NewsFeedAdapter;
import com.example.administrator.travel.models.entities.City;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.presenters.bases.NewsFeedPresenter;
import com.example.administrator.travel.presenters.impls.NewsFeedPresenterImpl;
import com.example.administrator.travel.views.bases.NewsFeedView;
import com.example.administrator.travel.views.activities.TourActivity;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.List;

public class NewsFeedFragment extends Fragment implements NewsFeedView, NewsFeedAdapter.ItemClickListener {
    private String array_spinner[];
    private Spinner spinnerOrigin, spinnerDestination;
    private ShimmerFrameLayout shimmerViewTour;
    private RecyclerView recyclerViewTour;
    private NewsFeedPresenter presenter;
    private NewsFeedAdapter adapter;

    public NewsFeedFragment() {
        array_spinner = new String[4];
        array_spinner[0] = "Mọi thứ";
        array_spinner[1] = "Tour";
        array_spinner[2] = "Địa điểm";
        array_spinner[3] = "Đánh giá";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_feed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new NewsFeedPresenterImpl(this);
        presenter.onViewCreated();
        shimmerViewTour = getActivity().findViewById(R.id.shimmerViewTour);
        recyclerViewTour = getActivity().findViewById(R.id.recyclerViewTour);

        //lstv.setSelector(R.color.transparent);

        spinnerOrigin = getActivity().findViewById(R.id.spinnerOrigin);
        spinnerDestination = getActivity().findViewById(R.id.spinnerDestination);


        setItemSpinnerOriginSelect();
        setItemSpinnerDestinationSelect();
    }


//    }
    void setItemSpinnerOriginSelect() {
        spinnerOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.onItemSpinnerOriginSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void setItemSpinnerDestinationSelect() {
        spinnerDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.onItemSpinnerDestinationSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void showCities(List<City> lstCity) {
        String arr[] = new String[lstCity.size() + 1];
        int n = lstCity.size();
        arr[0] = "Không";
        for (int i = 1; i <= n; i++)
            arr[i] = lstCity.get(i - 1).name;

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.news_feed_spinner_item, R.id.txtSpinnerOption, arr);
        spinnerOrigin.setAdapter(adapter);
        spinnerDestination.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewResumed();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onViewPaused();
    }

    @Override
    public void showTours(List<Tour> listTour) {
        adapter = new NewsFeedAdapter(getActivity(), listTour);
        adapter.setClickListener(this);
        recyclerViewTour.setAdapter(adapter);
    }

    @Override
    public void updateTourImage(int pos, String tourId, Bitmap img) {
        adapter.updateImage(pos, tourId, img);
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
    public void startShimmerTourAnimation() {
        shimmerViewTour.startShimmerAnimation();
    }

    @Override
    public void stopShimmerTourAnimation() {
        shimmerViewTour.stopShimmerAnimation();
    }

    @Override
    public void showShimmerViewTour() {
        shimmerViewTour.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShimmerViewTour() {
        shimmerViewTour.setVisibility(View.GONE);

    }

    @Override
    public void showRecyclerViewTour() {
        recyclerViewTour.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerViewTour() {
        recyclerViewTour.setVisibility(View.GONE);
    }

    @Override
    public void onTourItemClick(View view, String tourId, String ownerId) {
        presenter.onTourItemClicked(tourId, ownerId);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
