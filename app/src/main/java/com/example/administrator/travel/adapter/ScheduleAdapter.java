package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Schedule;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 24/12/2018.
 */

public class ScheduleAdapter extends BaseAdapter {
    List<Schedule> lstSchedule;
    Context context;
    Geocoder geocoder;

    public ScheduleAdapter(Context context, List<Schedule> lstSchedule) {
        this.lstSchedule = lstSchedule;
        if(context!=null) {
            this.context = context;
            geocoder = new Geocoder(context, Locale.getDefault());
        }
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

        convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.schedule_tour_item, null);
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
    public String getAddress(LatLng latLng) {
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // sá»‘ nhÃ 
            String result = address + ", " + city + ", " + state + ", " + country;
            addresses.clear();
            return result;

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}