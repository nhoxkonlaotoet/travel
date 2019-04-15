package com.example.administrator.travel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Tour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/15/2019.
 */

public class StoragePictureAdapter extends RecyclerView.Adapter<StoragePictureAdapter.ViewHolder> {

    private List<Bitmap> pictureList;
    private boolean[] flags;
    private LayoutInflater mInflater;
    private StoragePictureAdapter.PictureClickListener mClickListener;
    private int count;

    public StoragePictureAdapter(Context context, int count) {
        if(context==null)
            return;
        this.mInflater = LayoutInflater.from(context);
        pictureList = new ArrayList<>();
        this.count = count;
        flags=new boolean[count];
    }

    @Override
    @NonNull
    public StoragePictureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mInflater==null)
            return null;
        View view = mInflater.inflate(R.layout.item_picture, parent, false);
        return new StoragePictureAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoragePictureAdapter.ViewHolder holder, int position) {
        try {
            Bitmap picture = pictureList.get(position);
            holder.imgvPicture.setImageBitmap(picture);
            holder.checkBoxPicture.setChecked(flags[position]);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgvPicture;
        CheckBox checkBoxPicture;
        ViewHolder(View itemView) {
            super(itemView);
            imgvPicture = itemView.findViewById(R.id.imgvPicture);
            checkBoxPicture = itemView.findViewById(R.id.checkboxPicture);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(flags[getAdapterPosition()])
                flags[getAdapterPosition()]=false;
            else
                flags[getAdapterPosition()]=true;
            if (mClickListener != null)
                mClickListener.onPictureClick(view, pictureList.get(getAdapterPosition()));
            notifyDataSetChanged();
        }
    }

    // convenience method for getting data at click position
    public Bitmap getItem(int pos) {
        return pictureList.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(StoragePictureAdapter.PictureClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface PictureClickListener {
        void onPictureClick(View view, Bitmap image);
    }

    public void updateImage(int pos, Bitmap picture) {
        pictureList.add(pos, picture);
        notifyDataSetChanged();
    }
}