package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.NearbyAdapter;
import com.example.administrator.travel.adapter.NearbyTypeAdapter;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.NearbyType;
import com.example.administrator.travel.models.entities.Nearby;
import com.example.administrator.travel.presenters.bases.NearbyPresenter;
import com.example.administrator.travel.presenters.impls.NearbyPresenterImpl;
import com.example.administrator.travel.views.bases.NearbyView;
import com.example.administrator.travel.views.activities.MapsActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements NearbyView, NearbyAdapter.NearbyClickListener, NearbyTypeAdapter.NearbyTypeClickListener {

    RecyclerView recyclerViewNearby, recyclerViewNearbyType;
    NearbyPresenter presenter;

    NearbyAdapter nearbyAdapter;
    NearbyTypeAdapter nearbyTypeAdapter;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    void setOnRecyclerViewNearbyScroll() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewNearby = getActivity().findViewById(R.id.recyclerViewNearby);
        recyclerViewNearbyType = getActivity().findViewById(R.id.recyclerViewNearbyType);
        setrecyclerViewNearbyScroll();
        setOnRecyclerViewNearbyScroll();

        presenter = new NearbyPresenterImpl(this);
        presenter.onViewCreated();
    }


    void setrecyclerViewNearbyScroll() {
        recyclerViewNearby.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    presenter.onRecyclerViewNearbyScrollBottom();
                }
            }
        });
    }

    @Override
    public void showNearbyTypes(List<NearbyType> nearbyTypeList) {
        nearbyTypeAdapter = new NearbyTypeAdapter(getActivity(), nearbyTypeList);
        nearbyTypeAdapter.setClickListener(this);
        recyclerViewNearbyType.setAdapter(nearbyTypeAdapter);
    }

    @Override
    public void showNearbys(List<Nearby> nearbyList, MyLatLng mylocation) {
        nearbyAdapter = new NearbyAdapter(getContext(), nearbyList, mylocation, true);
        nearbyAdapter.setClickListener(this);
        recyclerViewNearby.setAdapter(nearbyAdapter);

    }

    @Override
    public void appendNearbys(List<Nearby> nearbyList) {
        nearbyAdapter.appendItems(nearbyList);

    }

    @Override
    public void notify(String message) {
        if (getActivity() != null)
            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void gotoMapActivity(String origin, String destination, String openFrom) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra("openFrom", openFrom);
        intent.putExtra("origin", origin);
        intent.putExtra("destination", destination);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onNearbyClick(View view, Nearby nearby) {
        presenter.onNearbyItemClicked(nearby);
    }

    @Override
    public void onItemNearbyTypeClick(View view, String nearbyTypeId, String nearbyTypeValue) {
        presenter.onNearbyTypeItemClicked(nearbyTypeValue);
    }
}
