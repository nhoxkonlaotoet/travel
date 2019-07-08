package com.example.administrator.travel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.administrator.travel.models.DownLoadImageTask;
import com.example.administrator.travel.models.bases.PicassoInteractor;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.place.nearby.Nearby;
import com.example.administrator.travel.models.impls.PicassoInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 24/12/2018.
 */

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.ViewHolder>
        implements Listener.OnDownloadImageFinishedListener {

    private LayoutInflater mInflater;
    private NearbyAdapter.NearbyClickListener mClickListener;
    private List<Nearby> nearbyList;
    private HashMap<String, Bitmap> photoMap;
    private boolean isVerticalList;
    private MyLatLng mylocation;
    String open, close, pricelv0, pricelv1, pricelv2, pricelv3, pricelv4;
    int idstar, idhalfstar, idnostar, idclose, idopen;
    PicassoInteractor picassoInteractor;
    Context context;
    boolean[] loadPhotoFlags = new boolean[60];
    private String apiKey;
    RecyclerView parent;

    public NearbyAdapter(Context context, List<Nearby> lstNearby, MyLatLng mylocation, boolean vertical) {
        if (context == null)
            return;
        this.context = context;
        picassoInteractor = new PicassoInteractorImpl();
        this.mInflater = LayoutInflater.from(context);
        this.nearbyList = lstNearby;
        this.photoMap = new HashMap<>();
        apiKey = context.getResources().getString(R.string.google_maps_key);
        isVerticalList = vertical;

        if (isVerticalList) {
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
            this.mylocation = mylocation;
        }
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
        View view;
        if (isVerticalList)
            view = mInflater.inflate(R.layout.item_nearby_vertical, parent, false);
        else
            view = mInflater.inflate(R.layout.item_nearby_horizontal, parent, false);
        return new NearbyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NearbyAdapter.ViewHolder holder, final int position) {
        if (context == null || mInflater == null)
            return;
        Nearby nearby = nearbyList.get(position);

        if (!loadPhotoFlags[position]) {
            loadPhotoFlags[position] = true;
            String url;
            try {
                url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference="
                        + nearbyList.get(position).photos.get(0).photoReference
                        + "&key="
                        + apiKey;
            } catch (Exception e) {
                url = nearbyList.get(position).icon;
            }
            new DownLoadImageTask(this).execute(nearby.id, url);
        }
        if (photoMap.get(nearby.id) != null) {
            holder.imgvNearby.setImageBitmap(photoMap.get(nearby.id));
        } else
            holder.imgvNearby.setImageBitmap(null);
        holder.txtNearbyName.setText(nearby.name);

        if (isVerticalList) {
            float[] result = new float[1];
            Location.distanceBetween(mylocation.latitude, mylocation.longitude,
                    nearby.geometry.location.lat, nearby.geometry.location.lng, result);
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
    }

    @Override
    public int getItemCount() {
        return nearbyList.size();
    }

    @Override
    public void onDownloadImageSuccess(String id, Bitmap image) {
        if (photoMap != null) {
            photoMap.put(id, image);
            parent.post(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onDownloadImageFail(Exception ex) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtNearbyName, txtNearbyDistance, txtRating, txtOpen, txtPriceLevel;
        ImageView imgvRating, imgvPriceLevel, imgvOpen, imgvNearby;

        ViewHolder(View itemView) {
            super(itemView);

            imgvNearby = itemView.findViewById(R.id.imgvNearby);
            txtNearbyName = itemView.findViewById(R.id.txtNearbyName);
            if (isVerticalList) {
                txtNearbyDistance = itemView.findViewById(R.id.txtNearbyDistance);
                txtRating = itemView.findViewById(R.id.txtRating);
                imgvRating = itemView.findViewById(R.id.imgvRating);
                txtOpen = itemView.findViewById(R.id.txtOpen);
                txtPriceLevel = itemView.findViewById(R.id.txtPriceLevel);
                imgvPriceLevel = itemView.findViewById(R.id.imgvPriceLevel);
                imgvOpen = itemView.findViewById(R.id.imgvOpen);
            }
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
