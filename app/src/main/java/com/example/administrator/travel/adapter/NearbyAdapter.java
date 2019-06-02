package com.example.administrator.travel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.PicassoInteractor;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.place.nearby.Nearby;
import com.example.administrator.travel.models.impls.PicassoInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 24/12/2018.
 */

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder> implements Listener.OnPicassoLoadFinishedListener {

    private LayoutInflater mInflater;
    private NearbyAdapter.NearbyClickListener mClickListener;
    private List<Nearby> nearbyList;
    MyLatLng mylocation;
    String open, close, pricelv0, pricelv1, pricelv2, pricelv3, pricelv4;
    int idstar, idhalfstar, idnostar, idclose, idopen;
    PicassoInteractor picassoInteractor;
    Context context;
    boolean[] loadPhotoFlags = new boolean[60];
    RecyclerView parent;
    public NearbyAdapter(Context context, List<Nearby> lstNearby, MyLatLng mylocation) {
        if (context == null)
            return;
        this.context = context;
        picassoInteractor = new PicassoInteractorImpl();
        this.mInflater = LayoutInflater.from(context);
        this.nearbyList = lstNearby;
        this.mylocation = mylocation;
        open = "Mở cửa";
        close = "Đóng cửa";
        pricelv0 = "Miễn phí";
        pricelv1 = "Rẻ";
        pricelv2 = "Vừa phải";
        pricelv3 = "Đắt";
        pricelv4 = "Rất đắt";
        idstar = R.drawable.ic_star_yellow_24dp;
        idhalfstar = R.drawable.ic_half_star_yellow_24dp;
        idnostar = R.drawable.ic_star_gray_24dp;
        idclose = R.drawable.ic_close_door_24dp;
        idopen = R.drawable.ic_open;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
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
    public void onBindViewHolder(@NonNull NearbyAdapter.ViewHolder holder, final int position) {
        if (context == null || mInflater == null)
            return;
        Log.e("onbind: ", position + "_____");
        Nearby nearby = nearbyList.get(position);
        float[] result = new float[1];
        Location.distanceBetween(mylocation.latitude, mylocation.longitude,
                nearby.geometry.location.lat, nearby.geometry.location.lng, result);
        if (!loadPhotoFlags[position]) {
            loadPhotoFlags[position]=true;
            String url;
            try {
                url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference="
                        + nearbyList.get(position).photos.get(0).photoReference
                        + "&key="
                        + context.getResources().getString(R.string.google_maps_key);
            } catch (Exception e) {
                url = nearbyList.get(position).icon;
            }
            picassoInteractor.load(context, position, url, this);
        }
        if (nearby.photo != null) {
            holder.imgvNearby.setImageBitmap(nearby.photo);
        } else
            holder.imgvNearby.setImageBitmap(null);
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
        if (nearby.rating == null) {
            holder.txtRating.setVisibility(View.INVISIBLE);
            holder.imgvRating.setVisibility(View.INVISIBLE);
        } else {
            holder.txtRating.setVisibility(View.VISIBLE);
            holder.imgvRating.setVisibility(View.VISIBLE);
            holder.txtRating.setText(nearby.rating.toString());
            if (nearby.rating < 4 && nearby.rating > 1) {
                holder.imgvRating.setImageResource(idhalfstar);
            } else if (nearby.rating < 1) {
                holder.imgvRating.setImageResource(idnostar);
            }
        }

        if (nearby.openingHours == null || nearby.openingHours.openNow == null) {
            holder.imgvOpen.setVisibility(View.INVISIBLE);
            holder.txtOpen.setVisibility(View.INVISIBLE);
        } else {
            holder.imgvOpen.setVisibility(View.VISIBLE);
            holder.txtOpen.setVisibility(View.VISIBLE);
            if (nearby.openingHours.openNow) {
                holder.txtOpen.setText(open);
                holder.imgvOpen.setImageResource(idopen);
            } else {
                holder.txtOpen.setText(close);
                holder.imgvOpen.setImageResource(idclose);
            }
        }

        if (nearby.priceLevel == null) {
            holder.txtPriceLevel.setVisibility(View.INVISIBLE);
            holder.imgvPriceLevel.setVisibility(View.INVISIBLE);
        } else {
            holder.txtPriceLevel.setVisibility(View.VISIBLE);
            holder.imgvPriceLevel.setVisibility(View.VISIBLE);
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
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return nearbyList.size();
    }

    @Override
    public void onPicassoLoadSuccess(int pos, Bitmap photo) {
        if (nearbyList != null && nearbyList.size() > pos) {
            nearbyList.get(pos).photo = photo;
            parent.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onPicassoLoadFail(Exception ex) {

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
                mClickListener.onNearbyClick(view, nearbyList.get(getAdapterPosition()));
        }
    }

    // convenience method for getting data at click position
    public Nearby getItem(int pos) {
        return nearbyList.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(NearbyAdapter.NearbyClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface NearbyClickListener {
        void onNearbyClick(View view, Nearby nearby);
    }

    public void appendItems(List<Nearby> nearbyList) {
        for (Nearby nearby : nearbyList) {
            this.nearbyList.add(nearby);
        }
        notifyDataSetChanged();
    }
}
