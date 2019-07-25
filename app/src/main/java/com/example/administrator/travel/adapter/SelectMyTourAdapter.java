package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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

public class SelectMyTourAdapter extends RecyclerView.Adapter<SelectMyTourAdapter.ViewHolder> {
    List<Tour> tourList;
    List<TourStartDate> tourStartDateList;
    DateFormat dateFormat;
    int n;
    private LayoutInflater mInflater;
    private SelectMyTourAdapter.ItemClickListener mClickListener;

    public SelectMyTourAdapter(Context context, int n) {
        if (context == null)
            return;
        mInflater = LayoutInflater.from(context);
        this.n = n;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tourList = new ArrayList<>();
        tourStartDateList = new ArrayList<>();
    }

    @Override
    @NonNull
    public SelectMyTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_select_my_tour, parent, false);
        return new SelectMyTourAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectMyTourAdapter.ViewHolder holder, int position) {
        if (tourStartDateList.size() > 0 && tourStartDateList.get(position) != null) {
            Date date = new Date(tourStartDateList.get(position).startDate);
            holder.txtStartDate.setText(dateFormat.format(date));
            holder.progressBarWaitTourStartDate.setVisibility(View.GONE);
            if (tourList.size() > 0 && tourList.get(position) != null) {
                String tourname = tourList.get(position).name;
                holder.txtTourName.setText(tourname);
                holder.progressBarWaitTourName.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return n;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTourName, txtStartDate;
        ProgressBar progressBarWaitTourName, progressBarWaitTourStartDate;

        ViewHolder(View itemView) {
            super(itemView);
            txtTourName = itemView.findViewById(R.id.txtMyTourName);
            txtStartDate = itemView.findViewById(R.id.txtStartDate);
            progressBarWaitTourName = itemView.findViewById(R.id.progressbarWaitTourName);
            progressBarWaitTourStartDate = itemView.findViewById(R.id.progressbarWaitTourStartDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null && tourStartDateList.size()>0)
                mClickListener.onMyTourItemClick(view, tourStartDateList.get(getAdapterPosition()).tourId, tourStartDateList.get(getAdapterPosition()).id);
        }
    }


    // allows clicks events to be caught
    public void setClickListener(SelectMyTourAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onMyTourItemClick(View view, String tourId, String tourStartId);
    }


    public void updateTourInfo(int pos, Tour tour, TourStartDate tourStartDate) {
        if (tour != null && tourList != null && !tourList.contains(tour))
            tourList.add(pos, tour);
        if (tourStartDate != null && tourStartDateList != null && !tourStartDateList.contains(tourStartDate))
            tourStartDateList.add(pos, tourStartDate);
        notifyDataSetChanged();
    }
}