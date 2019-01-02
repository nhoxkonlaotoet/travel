package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.adapter.NonScrollListView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.ScheduleAdapter;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.presenters.TourDetailPresenter;
import com.example.administrator.travel.views.TourDetailView;
import com.example.administrator.travel.views.activities.MapsActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class TourDetailFragment extends Fragment implements TourDetailView {
    NonScrollListView lstvSchedule;
    TourDetailPresenter presenter;
    public String dayId, tourId;
    TextView txtAdultPrice, txtChildrenPrice, txtBabyPrice, txtVehicle;
    Spinner spinnerDays;
    List<Day> lstDay = new ArrayList<>();
    Context context;
    public TourDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();

        return inflater.inflate(R.layout.fragment_tour_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lstvSchedule = getActivity().findViewById(R.id.lstvSchedule);
        txtAdultPrice=getActivity().findViewById(R.id.txtAdultPrice);
        txtChildrenPrice=getActivity().findViewById(R.id.txtChildrenPrice);
        txtBabyPrice=getActivity().findViewById(R.id.txtBabyPrice);
        spinnerDays=getActivity().findViewById(R.id.spinnerDays);
        txtVehicle=getActivity().findViewById(R.id.txtVehicle);;
        Bundle bundle = getActivity().getIntent().getExtras();
        tourId = bundle.getString("tourId");
        presenter = new TourDetailPresenter(this);
        presenter.onViewLoad();
        setOnSelectItemSpinner();
        setOnScheduleItemClick();
    }

    void setOnScheduleItemClick()
    {
        lstvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String scheduleId = view.getTag().toString();
               presenter.onScheduleItemClicked(scheduleId);

            }
        });
    }

    void setOnSelectItemSpinner()
    {
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
    @Override
    public void showInfor(Tour tour)
    {
        txtAdultPrice.setText(String.valueOf(tour.adultPrice));
        txtChildrenPrice.setText(String.valueOf(tour.childrenPrice));
        txtBabyPrice.setText(String.valueOf(tour.babyPrice));
        txtVehicle.setText(String.valueOf(tour.vihicle));
    }
    @Override
    public void showDays(List<Day> lstDay) {
        this.lstDay=lstDay;
        List<Integer> lstSpinnerDay = new ArrayList<>();
        for (Day day : lstDay) {
        //    Log.e("showDays: ", day.toString());
            lstSpinnerDay.add(day.day);
        }
        if(!(lstDay.size()==0)) {
            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, lstSpinnerDay);
            spinnerDays.setAdapter(adapter);
          // presenter.getSchedule(tourId, lstDay.get(0).id);
        }
    }

    @Override
    public void showSchedules(List<Schedule> lstSchedule) {
      //  for (Schedule schedule : lstSchedule) {
         //   Log.e("showSchedules: ", schedule.toString());
      //  }
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(getContext(),lstSchedule);
        lstvSchedule.setAdapter(scheduleAdapter);
    }

    @Override
    public void notifyFailure(Exception ex) {
        Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void gotoMapActivity(String tourId, String dayId, String scheduleId) {
        Intent intent = new Intent(getActivity(),MapsActivity.class);
        intent.putExtra("tourId",tourId);
        intent.putExtra("dayId",dayId);
        intent.putExtra("scheduleId",scheduleId);
        intent.putExtra("action","schedule");
        startActivity(intent);
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


}
