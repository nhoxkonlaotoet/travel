package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 24/12/2018.
 */

public class SelectMyTourAdapter extends BaseAdapter {
    Context context;
    List<Tour> lstTour;
    List<TourStartDate> lstTourStart;
    int n;
    DateFormat dateFormat;

    public SelectMyTourAdapter(Context context, int n) {
        this.context = context;
        this.n = n;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        lstTour = new ArrayList<>();
        lstTourStart = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return n;
    }

    @Override
    public Object getItem(int position) {
        return lstTourStart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (context == null)
            return null;
        convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.item_select_my_tour, null);
        TextView txtTourName = convertView.findViewById(R.id.txtMyTourName);
        TextView txtStartDate = convertView.findViewById(R.id.txtStartDate);
        ProgressBar progressBarWaitTourName = convertView.findViewById(R.id.progressbarWaitTourName);
        ProgressBar progressBarWaitTourStartDate = convertView.findViewById(R.id.progressbarWaitTourStartDate);
        if (lstTourStart.size() > 0 && lstTourStart.get(position) != null) {
            Date date = new Date(lstTourStart.get(position).startDate);
            txtStartDate.setText(dateFormat.format(date));
            progressBarWaitTourStartDate.setVisibility(View.GONE);

            if (lstTourStart.size() > 0 && lstTour.get(position) != null) {
                String tourname = lstTour.get(position).name;
                txtTourName.setText(tourname);
                progressBarWaitTourName.setVisibility(View.GONE);
                convertView.setTag(lstTourStart.get(position).tourId+" "+lstTourStart.get(position).id);
            }
        }
        return convertView;
    }

    public void updateTourInfo(int pos, Tour tour, TourStartDate tourStartDate) {
        if (!lstTour.contains(tour))
            lstTour.add(pos, tour);
        if (!lstTourStart.contains(tourStartDate))
            lstTourStart.add(pos, tourStartDate);
        notifyDataSetChanged();
    }
}