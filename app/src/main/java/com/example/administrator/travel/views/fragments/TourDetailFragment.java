package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.NonScrollListView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Day;
import com.example.administrator.travel.models.entities.Schedule;
import com.example.administrator.travel.presenters.TourDetailPresenter;
import com.example.administrator.travel.views.TourDetailView;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class TourDetailFragment extends Fragment implements TourDetailView {
    NonScrollListView lstvSchedule;
    TourDetailPresenter presenter;
    String dayId, tourId;

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
        lstvSchedule = getActivity().findViewById(R.id.lstvSchedule);

        Bundle bundle = getActivity().getIntent().getExtras();
        tourId = bundle.getString("tourId");
        presenter = new TourDetailPresenter(this);
        presenter.getDays(tourId);

//        final List<String[]> values = new LinkedList<String[]>();
//        values.add(new String[]{"Title 1", "Subtitle 1"});
//        values.add(new String[]{"Title 2", "Subtitle 2"});
//        values.add(new String[]{"Title 3", "Subtitle 3"});
//        values.add(new String[]{"Title 4", "Subtitle 4"});
//        values.add(new String[]{"Title 5", "Subtitle 5"});
//        values.add(new String[]{"Title 6", "Subtitle 6"});
//        values.add(new String[]{"Title 7", "Subtitle 7"});
//        values.add(new String[]{"Title 8", "Subtitle 8"});
//
//        lstvSchedule.setAdapter(new ArrayAdapter<String[]>(getActivity(), android.R.layout.simple_expandable_list_item_2, android.R.id.text1, values) {
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//
//                return view;
//            }
//        });
    }

    @Override
    public void showDays(List<Day> lstDay) {
        for (Day day : lstDay) {
            Log.e("showDays: ", day.toString());
        }
        presenter.getSchedule(tourId, lstDay.get(0).id);
    }

    @Override
    public void showSchedules(List<Schedule> lstSchedule) {
        for (Schedule schedule : lstSchedule) {
            Log.e("showSchedules: ", schedule.toString());
        }
        ScheduleAdapter scheduleAdapter = new ScheduleAdapter(lstSchedule);
        lstvSchedule.setAdapter(scheduleAdapter);
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

    public class ScheduleAdapter extends BaseAdapter {
        List<Schedule> lstSchedule;

        public ScheduleAdapter(List<Schedule> lstSchedule) {
            this.lstSchedule = lstSchedule;
        }

        @Override
        public int getCount() {
            return lstSchedule.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getActivity().getLayoutInflater().inflate(R.layout.schedule_tour_item, null);
            Schedule schedule = lstSchedule.get(position);
            TextView txtScheduleTitle = convertView.findViewById(R.id.txtScheduleTitle);
            TextView txtScheduleAddress = convertView.findViewById(R.id.txtScheduleAddress);
            TextView txtScheduleDescription = convertView.findViewById(R.id.txtScheduleDescription);
            TextView txtScheduleTime = convertView.findViewById(R.id.txtScheduleTime);
            View vSpacing = convertView.findViewById(R.id.vSpacing);

            if (position > 0 && schedule.title.equals(lstSchedule.get(position - 1).title))
                txtScheduleTitle.setVisibility(View.GONE);
            else {
                txtScheduleTitle.setText(schedule.title);
            }
            if (position == lstSchedule.size() ||
                    ((position < lstSchedule.size() - 1) && !schedule.title.equals(lstSchedule.get(position + 1).title)))
                vSpacing.setVisibility(View.VISIBLE);
            {
                txtScheduleAddress.setText(getAddress(schedule.latLng.getLatLng()));
                txtScheduleDescription.setText(schedule.content);
                txtScheduleTime.setText(schedule.hour);
                if (position == 0) {
                    View vAboveLine = convertView.findViewById(R.id.vAboveLine);
                    vAboveLine.setVisibility(View.INVISIBLE);
                }
                if (position == getCount() - 1) {
                    View vBelowLine = convertView.findViewById(R.id.vBelowLine);
                    vBelowLine.setVisibility(View.INVISIBLE);
                }
                return convertView;
            }
        }
    }
}
