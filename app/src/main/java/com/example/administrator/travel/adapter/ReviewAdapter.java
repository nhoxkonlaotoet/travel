package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.entities.UserInformation;

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
    List<Rating> ratingList;
    Context context;
    DateFormat dateFormat;
    List<String> userNameList;
    List<Bitmap> userAvatarList;

    public ReviewAdapter(Context context, List<Rating> ratingList) {

        this.ratingList = ratingList;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.context = context;
        userNameList = new ArrayList<>();
        userAvatarList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return ratingList.size();
    }

    @Override
    public Object getItem(int position) {
        return ratingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (context == null)
            return null;
        convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.item_review, null);
        TextView txtReviewerName = convertView.findViewById(R.id.txtReviewerName);
        TextView txtRatingDate = convertView.findViewById(R.id.txtRatingDate);
        RatingBar ratingBarReview = convertView.findViewById(R.id.ratingBarReview);
        TextView txtReviewCOntent = convertView.findViewById(R.id.txtReviewCOntent);
        ImageView imgvReviewer = convertView.findViewById(R.id.imgvReviewer);


        Date date = new Date(ratingList.get(position).ratingTime);
        Rating rating = ratingList.get(position);
        convertView.setTag(rating.id);
        if (userNameList.size() > 0 && userNameList.get(position) != null) {
            txtReviewerName.setText(userNameList.get(position));
        }
        if (userAvatarList.size() > 0 && userAvatarList.get(position) != null) {
            imgvReviewer.setImageBitmap(userAvatarList.get(position));
        }

        txtRatingDate.setText(dateFormat.format(date));
        ratingBarReview.setRating(rating.rating);
        txtReviewCOntent.setText(rating.content);

        return convertView;
    }

    public void updateUserName(String username, int pos) {
        userNameList.add(pos, username);
        notifyDataSetChanged();
    }
    public void updateUserAvatar(Bitmap userAvatar, int pos) {
        userAvatarList.add(pos, userAvatar);
        notifyDataSetChanged();
    }
}