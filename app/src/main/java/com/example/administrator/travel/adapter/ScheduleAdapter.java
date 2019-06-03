package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<Schedule> lstSchedule;
    private LayoutInflater mInflater;
    private ScheduleAdapter.ItemClickListener mClickListener;
    private Context context;
    private Geocoder geocoder;

    public ScheduleAdapter(Context context, List<Schedule> lstSchedule) {
        this.lstSchedule = lstSchedule;
        if (context != null) {
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
            geocoder = new Geocoder(context, Locale.getDefault());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtScheduleTitle, txtScheduleAddress, txtScheduleDescription, txtScheduleTime;
        View vSpacing, vAboveLine, vBelowLine;
        LinearLayout layoutTodo;
        ViewHolder(View itemView) {
            super(itemView);
            txtScheduleTitle = itemView.findViewById(R.id.txtScheduleTitle);
            txtScheduleAddress = itemView.findViewById(R.id.txtScheduleAddress);
            txtScheduleDescription = itemView.findViewById(R.id.txtScheduleDescription);
            txtScheduleTime = itemView.findViewById(R.id.txtScheduleTime);
            vSpacing = itemView.findViewById(R.id.vSpacing);
            vAboveLine = itemView.findViewById(R.id.vAboveLine);
            vBelowLine = itemView.findViewById(R.id.vBelowLine);
            layoutTodo = itemView.findViewById(R.id.layoutTodo);
            layoutTodo.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onScheduleItemClick(view, lstSchedule.get(getAdapterPosition()).id);
        }
    }

    public void setClickListener(ScheduleAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onScheduleItemClick(View view, String scheduleId);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_schedule_tour, parent, false);
        return new ScheduleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Schedule schedule = lstSchedule.get(position);
        if (position > 0 && schedule.title.equals(lstSchedule.get(position - 1).title))
            holder.txtScheduleTitle.setVisibility(View.GONE);
        else {
            holder.txtScheduleTitle.setText(schedule.title);
        }
        if (position == lstSchedule.size() ||
                ((position < lstSchedule.size() - 1) && !schedule.title.equals(lstSchedule.get(position + 1).title))) {
            holder.vSpacing.setVisibility(View.VISIBLE);

        } else {
            holder.vSpacing.setVisibility(View.GONE);
        }
        String address = getAddress(schedule.latLng.getLatLng());
        if (address == null)
            address = "";
        holder.txtScheduleAddress.setText(address);
        holder.txtScheduleDescription.setText(schedule.content);
        holder.txtScheduleTime.setText(schedule.hour);
        if (position == 0) {
            holder.vAboveLine.setVisibility(View.INVISIBLE);
        }
        if (position == getItemCount() - 1) {
            holder.vBelowLine.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return lstSchedule.size();
    }

    private String getAddress(LatLng latLng) {
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() == 0)
                return null;
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