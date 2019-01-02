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
    List<Rating> list;
    Context context;
    DateFormat dateFormat;
    List<UserInformation> lstUser;
    public ReviewAdapter(Context context, List<Rating> list)
    {

        this.list=list;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.context=context;
        lstUser = new ArrayList<>();

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
        ImageView imgvReviewer = convertView.findViewById(R.id.imgvReviewer);



        Date date = new Date(list.get(position).ratingTime);
        Rating rating = list.get(position);
        convertView.setTag(rating.id);

        for(int i=0;i<lstUser.size();i++){
            if(rating.ratingPeopleId.equals(lstUser.get(i).id)) {
                txtReviewerName.setText(lstUser.get(i).getName());
                imgvReviewer.setImageBitmap(lstUser.get(i).avatar);
            }
        }

        txtRatingDate.setText(dateFormat.format(date));
        ratingBarReview.setRating(rating.rating);
        txtReviewCOntent.setText(rating.content);

        return convertView;
    }
    public void updateReviewerInfo(UserInformation user) {
      for(int i=0;i<lstUser.size();i++)
      {
          if(lstUser.get(i).id.equals(user.id))
              return;
      }
      lstUser.add(user);
      notifyDataSetChanged();
    }
}