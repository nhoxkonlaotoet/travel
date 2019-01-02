package com.example.administrator.travel.views.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.AcceptBookingAdapter;
import com.example.administrator.travel.models.entities.TourBooking;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.presenters.AcceptBookingPresenter;
import com.example.administrator.travel.views.AcceptBookingView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptBookingFragment extends Fragment implements AcceptBookingView {
    AcceptBookingPresenter presenter;
    ListView lstvAcceptBooking;
    AcceptBookingAdapter adapter;
    public AcceptBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accept_booking, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstvAcceptBooking = getActivity().findViewById(R.id.lstvAcceptBooking);

        Bundle bundle = getActivity().getIntent().getExtras();
        String tourStartId= bundle.getString("tourStartId");

        presenter = new AcceptBookingPresenter(this);
        presenter.onViewLoad(tourStartId);

    }

    @Override
    public void showBookings(List<TourBooking> lstBooking) {
        adapter = new AcceptBookingAdapter(lstBooking, getActivity());
        lstvAcceptBooking.setAdapter(adapter);
    }

    @Override
    public void notifyTourFinished() {

    }

    @Override
    public void showUserInfo(UserInformation user) {
        adapter.updateUserInfor(user);
    }
}
