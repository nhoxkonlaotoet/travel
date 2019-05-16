package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.TourStartAdapter;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.bases.TourStartPresenter;
import com.example.administrator.travel.presenters.impls.TourStartPresenterImpl;
import com.example.administrator.travel.views.bases.TourStartView;
import com.example.administrator.travel.views.activities.BookTourActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TourStartFragment extends Fragment implements TourStartView, TourStartAdapter.ItemClickListener {
    RecyclerView recyclerviewTourStart;
    TourStartPresenter presenter;
    TourStartAdapter tourStartAdapter;

    public TourStartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tour_start, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerviewTourStart = getActivity().findViewById(R.id.recyclerviewTourStart);
        presenter = new TourStartPresenterImpl(this);
        Bundle bundle = getActivity().getIntent().getExtras();
        presenter.onViewCreated(bundle);
    }

    @Override
    public void showTourStartDate(List<TourStartDate> listTourStartDate, boolean isCompany) {

        tourStartAdapter = new TourStartAdapter(getContext(), isCompany, listTourStartDate);
        tourStartAdapter.setClickListener(this);
        recyclerviewTourStart.setAdapter(tourStartAdapter);
        Log.e("SelectTourFragment: ", "showTourStartDate");
    }

    @Override
    public void notifyFailure(Exception ex) {

    }

    @Override
    public void gotoBooktourActivity(String tourStartId) {
        Intent intent = new Intent(getActivity(),BookTourActivity.class);
        intent.putExtra("tourStartId",tourStartId);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void onTourStartItemClick(View view, String tourStartId) {
        presenter.onTourStartItemClick(tourStartId);
    }

}
