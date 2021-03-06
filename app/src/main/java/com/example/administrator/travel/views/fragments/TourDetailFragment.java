package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.FormatMoney;
import com.example.administrator.travel.adapter.NonScrollListView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.ScheduleAdapter;
import com.example.administrator.travel.adapter.TourStartAdapter;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.presenters.bases.TourDetailPresenter;
import com.example.administrator.travel.presenters.impls.TourDetailPresenterImpl;
import com.example.administrator.travel.views.activities.AddTourStartDateActivity;
import com.example.administrator.travel.views.bases.TourDetailView;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//com.example.administrator.travel.friendContactAdapter.NonScrollListView
public class TourDetailFragment
        extends Fragment implements TourDetailView, ScheduleAdapter.ItemClickListener, TourStartAdapter.ItemClickListener {
    RecyclerView recyclerViewSchedule, recyclerviewTourStart;
    RelativeLayout btnAddTourStart;
    TourDetailPresenter presenter;
    TextView txtAdultPrice, txtChildrenPrice, txtBabyPrice, txtVehicle;
    Spinner spinnerDays;
    List<Day> lstDay = new ArrayList<>();
    TextView txtNotifyHaveNoTourStart;
    public TourDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tour_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapping();
        setOnButtonAddTourStartClick();
        setOnSelectItemSpinner();

        Bundle bundle = getActivity().getIntent().getExtras();
        presenter = new TourDetailPresenterImpl(this);
        presenter.onViewCreated(bundle);
    }

    void mapping() {
        recyclerViewSchedule = getActivity().findViewById(R.id.recyclerViewSchedule);
        recyclerviewTourStart = getActivity().findViewById(R.id.recyclerviewTourStart);
        txtAdultPrice = getActivity().findViewById(R.id.txtAdultPrice);
        txtChildrenPrice = getActivity().findViewById(R.id.txtChildrenPrice);
        txtBabyPrice = getActivity().findViewById(R.id.txtBabyPrice);
        spinnerDays = getActivity().findViewById(R.id.spinnerDays);
        txtVehicle = getActivity().findViewById(R.id.txtVehicle);
        btnAddTourStart = getActivity().findViewById(R.id.btnAddTourStart);
        txtNotifyHaveNoTourStart=getActivity().findViewById(R.id.txtNotifyHaveNoTourStart);
    }


    void setOnSelectItemSpinner() {
        spinnerDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                presenter.onSelectItemSpinnerDays(lstDay.get(position).id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    void setOnButtonAddTourStartClick() {
        btnAddTourStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onButtonAddTourStartClick();
            }
        });
    }

    @Override
    public void showInfor(Tour tour) {
        txtAdultPrice.setText(FormatMoney.formatToString(tour.adultPrice));
        txtChildrenPrice.setText(FormatMoney.formatToString(tour.childrenPrice));
        txtBabyPrice.setText(FormatMoney.formatToString(tour.babyPrice));
        txtVehicle.setText(String.valueOf(tour.vihicle));
    }

    @Override
    public void showDays(List<Day> lstDay) {
        this.lstDay = lstDay;
        List<Integer> lstSpinnerDay = new ArrayList<>();
        for (Day day : lstDay) {
            //    Log.e("showDays: ", day.toString());
            lstSpinnerDay.add(day.day);
        }
        if (!(lstSpinnerDay.size() == 0) && getContext() != null) {
            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, lstSpinnerDay);
            spinnerDays.setAdapter(adapter);
        }
    }

    @Override
    public void showSchedules(List<Schedule> lstSchedule) {
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(), lstSchedule);
        scheduleAdapter.setClickListener(this);
        recyclerViewSchedule.setAdapter(scheduleAdapter);

    }

    @Override
    public void notifyFailure(Exception ex) {
    }

    @Override
    public void gotoMapActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void gotoAddTourStartDateActivity(String tourId, int requestCode) {
        Intent intent = new Intent(getActivity(), AddTourStartDateActivity.class);
        intent.putExtra("tourId", tourId);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void showTourStartDate(List<TourStartDate> tourStartDateList) {
        if(tourStartDateList==null || tourStartDateList.size()==0)
        {
            txtNotifyHaveNoTourStart.setVisibility(View.VISIBLE);
        }
        else {
            txtNotifyHaveNoTourStart.setVisibility(View.GONE);
            TourStartAdapter tourStartAdapter = new TourStartAdapter(getContext(), false, tourStartDateList);
            tourStartAdapter.setClickListener(this);
            recyclerviewTourStart.setAdapter(tourStartAdapter);
        }
    }



    @Override
    public Context getContext() {
        return getActivity();
    }

    public String getAddress(LatLng latLng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // sá»‘ nhÃ 
            //    Toast.makeText(getActivity(), address+", "+city+", "+state+", "+country+", "+postalCode+", "+knownName, Toast.LENGTH_SHORT).show();
            String result = address + ", " + city + ", " + state + ", " + country;
            return result;

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }


    @Override
    public void onScheduleItemClick(View view, String scheduleId) {
        presenter.onScheduleItemClicked(scheduleId);
    }

    @Override
    public void onTourStartItemClick(View view, String tourStartId) {

    }
}
