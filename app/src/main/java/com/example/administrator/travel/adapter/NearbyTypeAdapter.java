package com.example.administrator.travel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.NearbyType;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 5/27/2019.
 */

public class NearbyTypeAdapter extends RecyclerView.Adapter<NearbyTypeAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private NearbyTypeAdapter.NearbyTypeClickListener mClickListener;
    private List<NearbyType> nearbyTypeList;
    private HashMap<String, Bitmap> nearbyTypeIconMap;

    public NearbyTypeAdapter(Context context, List<NearbyType> nearbyTypeList) {
        if (context == null)
            return;
        this.mInflater = LayoutInflater.from(context);
        this.nearbyTypeList = nearbyTypeList;
        nearbyTypeIconMap = new HashMap<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_nearby_type, parent, false);
        return new NearbyTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NearbyType nearbyType = nearbyTypeList.get(position);
        if (nearbyTypeIconMap.get(nearbyType.id) != null)
            holder.imgvNearbyTypeIcon.setImageBitmap(nearbyTypeIconMap.get(nearbyType.id));
        holder.txtNearbyTypeName.setText(nearbyType.display);
    }


    @Override
    public int getItemCount() {
        return nearbyTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgvNearbyTypeIcon;
        TextView txtNearbyTypeName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgvNearbyTypeIcon = itemView.findViewById(R.id.imgvNearbyTypeIcon);
            txtNearbyTypeName = itemView.findViewById(R.id.txtNearbyTypeName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemNearbyTypeClick(view, nearbyTypeList.get(getAdapterPosition()).id,
                        nearbyTypeList.get(getAdapterPosition()).value);
        }
    }

    public void setClickListener(NearbyTypeAdapter.NearbyTypeClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface NearbyTypeClickListener {
        void onItemNearbyTypeClick(View view, String nearbyTypeId, String nearbyTypeValue);
    }

    public void updateNearbyTypeIcon(String nearbyTypeId, Bitmap icon) {
        nearbyTypeIconMap.put(nearbyTypeId, icon);
        notifyDataSetChanged();
    }
}
