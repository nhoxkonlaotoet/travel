package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 26/12/2018.
 */

public class ReviewAdapter extends BaseAdapter {
    List<Rating> list;
    Context context;
    DateFormat dateFormat;
    public ReviewAdapter(Context context, List<Rating> list)
    {

        this.list=list;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.item_review, null);
        TextView txtReviewerName = convertView.findViewById(R.id.txtReviewerName);
        TextView txtRatingDate = convertView.findViewById(R.id.txtRatingDate);
        RatingBar ratingBarReview = convertView.findViewById(R.id.ratingBarReview);
        TextView txtReviewCOntent = convertView.findViewById(R.id.txtReviewCOntent);

        Date date = new Date(list.get(position).ratingTime);
        Rating rating = list.get(position);
        convertView.setTag(rating.id);

        txtReviewerName.setText(rating.ratingPeopleId);
        txtRatingDate.setText(dateFormat.format(date));
        ratingBarReview.setRating(rating.rating);
        txtReviewCOntent.setText(rating.content);

        return convertView;
    }
}