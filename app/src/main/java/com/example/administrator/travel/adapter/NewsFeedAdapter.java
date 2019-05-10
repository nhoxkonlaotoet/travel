package com.example.administrator.travel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Tour;

import java.util.List;

/**
 * Created by Administrator on 24/12/2018.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {

    private List<Tour> tourList;

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public NewsFeedAdapter(Context context, List<Tour> tourList) {
        if (context == null)
            return;
        mInflater = LayoutInflater.from(context);
        this.tourList = tourList;
        Log.e("NewsFeedAdapter: ", tourList.size() + "");
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_tour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tour tour = tourList.get(position);
        holder.itemView.setTag(tourList.get(position).id + " " + tourList.get(position).owner);
        if (tour.image != null)
            holder.imgv.setImageBitmap(tour.image);
        holder.txtTourName.setText(tour.name);
        holder.txtDays.setText(tour.days + " ngày " + tour.nights + " đêm");
        holder.ratingBar.setRating(tour.rating);
        holder.txtNumberofRating.setText(tour.numberofRating + " bình chọn");
        holder.txtTourPrice.setText(tour.adultPrice + "đ");
        holder.txtTourSaleprice.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return tourList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTourName, txtDays, txtNumberofRating, txtTourPrice, txtTourSaleprice;
        ImageView imgv;
        RatingBar ratingBar;

        ViewHolder(View itemView) {
            super(itemView);
            txtTourName = itemView.findViewById(R.id.txtTourName);
            txtDays = itemView.findViewById(R.id.txtDays);
            ratingBar = itemView.findViewById(R.id.ratebarTourRating);
            txtNumberofRating = itemView.findViewById(R.id.txtNumberofRating);
            txtTourPrice = itemView.findViewById(R.id.txtTourPrice);
            txtTourSaleprice = itemView.findViewById(R.id.txtTourSalePrice);
            imgv = itemView.findViewById(R.id.imgvTour);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onTourItemClick(view, tourList.get(getAdapterPosition()).id, tourList.get(getAdapterPosition()).owner);
        }
    }

    // convenience method for getting data at click position
    public Tour getItem(int pos) {
        return tourList.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onTourItemClick(View view, String tourId, String ownerId);
    }

    public void updateImage(int pos, String tourId, Bitmap img) {
        if (tourList!=null && tourList.get(pos).id.equals(tourId)) {
            tourList.get(pos).image = img;
            notifyDataSetChanged();
        }
    }
}
