package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Tour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 24/12/2018.
 */
public class NewsFeedAdapter extends BaseAdapter {
    List<Tour> list= new ArrayList();
    Context context;
    public NewsFeedAdapter(Context context, List<Tour> listTour)
    {
        this.list = listTour;
        this.context=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tour tour = list.get(position);
        convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.tour_item, null);
        //mapping
        TextView txtTourName = convertView.findViewById(R.id.txtTourName);
        TextView txtDays = convertView.findViewById(R.id.txtDays);
        RatingBar ratingBar = convertView.findViewById(R.id.ratebarTourRating);
        TextView txtNumberofRating = convertView.findViewById(R.id.txtNumberofRating);
        TextView txtTourPrice = convertView.findViewById(R.id.txtTourPrice);
        TextView txtTourSaleprice = convertView.findViewById(R.id.txtTourSalePrice);
        ImageView imgv = convertView.findViewById(R.id.imgvTour);
        convertView.setTag(list.get(position).id+" "+list.get(position).owner);
        //set values
        imgv.setImageBitmap(tour.image);
        txtTourName.setText(tour.name);
        txtDays.setText(tour.days+" ngày "+tour.nights+" đêm");
        ratingBar.setRating(tour.rating);
        txtNumberofRating.setText(tour.numberofRating+" bình chọn");
        txtTourPrice.setText(tour.adultPrice + "đ");
        txtTourSaleprice.setVisibility(View.INVISIBLE);
        return convertView;
    }
}