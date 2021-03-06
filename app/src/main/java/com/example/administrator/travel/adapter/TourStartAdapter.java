package com.example.administrator.travel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.TourStartDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 4/11/2019.
 */

public class TourStartAdapter extends RecyclerView.Adapter<TourStartAdapter.ViewHolder> {

    private List<TourStartDate> tourStartDateList;
    private LayoutInflater mInflater;
    private TourStartAdapter.ItemClickListener mClickListener;
    private boolean isCompany;
    private Context context;

    public TourStartAdapter(Context context, boolean isCompany, List<TourStartDate> tourStartDateList) {
        if (context != null) {
            this.isCompany = isCompany;
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
            this.tourStartDateList = tourStartDateList;
        }
    }

    @Override
    @NonNull
    public TourStartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_tour_start_date, parent, false);
        return new TourStartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourStartAdapter.ViewHolder holder, int position) {
        Date date = new Date(tourStartDateList.get(position).startDate);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.txtStartDate.setText(dateFormat.format(date));
        holder.txtNumberPeople.setText(tourStartDateList.get(position).peopleBooking + "");
    }

    @Override
    public int getItemCount() {
        if (tourStartDateList != null)
            return tourStartDateList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtStartDate, txtNumberPeople;
        Button btnBookTour;

        ViewHolder(View itemView) {
            super(itemView);
            txtStartDate = itemView.findViewById(R.id.txtStart);
            txtNumberPeople = itemView.findViewById(R.id.txtNumberPeople);
            btnBookTour = itemView.findViewById(R.id.btnBookTour);
            if (isCompany)
                btnBookTour.setText(context.getResources().getString(R.string.see_more));
            else
                btnBookTour.setText(context.getResources().getString(R.string.book_tour));
            btnBookTour.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onTourStartItemClick(view, tourStartDateList.get(getAdapterPosition()).id);
        }
    }

    // convenience method for getting data at click position
    public TourStartDate getItem(int pos) {
        return tourStartDateList.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(TourStartAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onTourStartItemClick(View view, String tourStartId);
    }

}