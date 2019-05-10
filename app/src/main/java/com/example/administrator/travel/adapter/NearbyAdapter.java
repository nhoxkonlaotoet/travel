package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.Nearby;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 24/12/2018.
 */

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> {

    private List<Bitmap> pictureList;
    private LayoutInflater mInflater;
    private NearbyAdapter.NearbyClickListener mClickListener;
    public List<Nearby> lstNearby = new ArrayList<>();
    MyLatLng mylocation;
    String open, close, pricelv0, pricelv1, pricelv2, pricelv3, pricelv4;
    int idhalfstar, idnostar, idclose;

    public NearbyAdapter(Context context, List<Nearby> lstNearby, MyLatLng mylocation) {
        if (context == null)
            return;
        this.mInflater = LayoutInflater.from(context);
        pictureList = new ArrayList<>();
        this.lstNearby = lstNearby;
        this.mylocation = mylocation;
        open = "Mở cửa";
        close = "Đóng cửa";
        pricelv0 = "Miễn phí";
        pricelv1 = "Rẻ";
        pricelv2 = "Vừa phải";
        pricelv3 = "Đắt";
        pricelv4 = "Rất đắt";
        idhalfstar = R.drawable.ic_half_star_yellow_24dp;
        idnostar = R.drawable.ic_star_gray_24dp;
        idclose = R.drawable.ic_close_door_24dp;

    }

    @Override
    @NonNull
    public NearbyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_nearby, parent, false);
        return new NearbyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyAdapter.ViewHolder holder, int position) {
        try {

            Nearby nearby = lstNearby.get(position);

            float[] result = new float[1];
            Location.distanceBetween(mylocation.latitude, mylocation.longitude,
                    nearby.location.latitude, nearby.location.longitude, result);

            if (nearby.photo != null) {
                holder.imgvNearby.setImageBitmap(nearby.photo);
            }
            holder.txtNearbyName.setText(nearby.name);
            float km, m;
            m = result[0];
            if (m >= 1000) {
                km = ((Math.round(m / 100)) * 100);
                km /= 1000;
                holder.txtNearbyDistance.setText(+km + "km");
            } else {
                holder.txtNearbyDistance.setText(+Math.round(m) + "m");
            }

            holder.txtRating.setText(nearby.rating.toString());
            if (nearby.rating < 4 && nearby.rating > 1) {
                holder.imgvRating.setImageResource(idhalfstar);

            } else if (nearby.rating < 1) {
                holder.imgvRating.setImageResource(idnostar);
            }

            if (nearby.openNow == null) {
                holder.txtOpen.setVisibility(View.INVISIBLE);
                holder.imgvOpen.setVisibility(View.INVISIBLE);
            } else if (nearby.openNow) {
                holder.txtOpen.setText(open);
            } else {
                holder.txtOpen.setText(close);
                holder.imgvOpen.setImageResource(idclose);
            }
            switch (nearby.priceLevel) {
                case 0:
                    holder.txtPriceLevel.setText(pricelv0);
                    break;
                case 1:
                    holder.txtPriceLevel.setText(pricelv1);
                    break;
                case 2:
                    holder.txtPriceLevel.setText(pricelv2);
                    break;
                case 3:
                    holder.txtPriceLevel.setText(pricelv3);
                    break;
                case 4:
                    holder.txtPriceLevel.setText(pricelv4);
                    break;
                default:
                    holder.imgvPriceLevel.setVisibility(View.INVISIBLE);
                    holder.txtPriceLevel.setVisibility(View.INVISIBLE);
                    break;
            }

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return lstNearby.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNearbyName, txtNearbyDistance, txtRating, txtOpen, txtPriceLevel;
        ImageView imgvRating, imgvPriceLevel, imgvOpen, imgvNearby;

        ViewHolder(View itemView) {
            super(itemView);
            txtNearbyName = itemView.findViewById(R.id.txtNearbyName);
            txtNearbyDistance = itemView.findViewById(R.id.txtNearbyDistance);
            txtRating = itemView.findViewById(R.id.txtRating);
            imgvRating = itemView.findViewById(R.id.imgvRating);
            txtOpen = itemView.findViewById(R.id.txtOpen);
            txtPriceLevel = itemView.findViewById(R.id.txtPriceLevel);
            imgvPriceLevel = itemView.findViewById(R.id.imgvPriceLevel);
            imgvOpen = itemView.findViewById(R.id.imgvOpen);
            imgvNearby = itemView.findViewById(R.id.imgvNearby);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onNearbyClick(view, lstNearby.get(getAdapterPosition()));
        }
    }

    // convenience method for getting data at click position
    public Nearby getItem(int pos) {
        return lstNearby.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(NearbyAdapter.NearbyClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface NearbyClickListener {
        void onNearbyClick(View view, Nearby nearby);
    }

    public void updateImage(int pos, Bitmap picture) {
        if (pos < lstNearby.size()) {
            lstNearby.get(pos).photo = picture;
            notifyDataSetChanged();
        }
    }

    public void appendItems(List<Nearby> nearbyList) {
        for (Nearby nearby : nearbyList) {
            this.lstNearby.add(nearby);
        }
        notifyDataSetChanged();
    }
}
