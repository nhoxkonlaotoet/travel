package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 24/12/2018.
 */

public class SelectMyTourAdapter extends BaseAdapter {
    Context context;
    List<Tour> lstTour;
    List<TourStartDate> lstTourStart;
    DateFormat dateFormat ;
    public SelectMyTourAdapter(Context context, List<Tour> lstTour, List<TourStartDate> lstTourStart)
    {
        this.context=context;
        this.lstTour=lstTour;
        this.lstTourStart=lstTourStart;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }
    @Override
    public int getCount() {
        return lstTourStart.size();
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

        convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.item_select_my_tour, null);
        convertView.setTag(lstTourStart.get(position).tourId+" "+lstTourStart.get(position).id);
        TextView txtTourName = convertView.findViewById(R.id.txtMyTourName);
        TextView txtStartDate = convertView.findViewById(R.id.txtStartDate);
        Date date = new Date(lstTourStart.get(position).startDate);
        txtStartDate.setText(dateFormat.format(date));
        String tourname="";
        for(Tour tour:lstTour)
            if(tour.id.equals(lstTourStart.get(position).tourId))
            {
                tourname=tour.name;
                break;
            }
        txtTourName.setText(tourname);

        return convertView;
    }
}