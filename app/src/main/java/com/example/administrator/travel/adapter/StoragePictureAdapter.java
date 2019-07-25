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

import com.example.administrator.travel.R;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Admin on 4/15/2019.
 */

public class StoragePictureAdapter extends RecyclerView.Adapter<StoragePictureAdapter.ViewHolder> {

    private HashMap<String, Bitmap> pictureMap;
    private boolean[] flags;
    private StoragePictureAdapter.PictureClickListener mClickListener;
    private int count;
    File[] filenameList;
    RecyclerView parent;
    public StoragePictureAdapter(int count, File[] filenameList) {
        pictureMap = new HashMap<>();
        this.count = count;
        flags = new boolean[count];
        this.filenameList = filenameList;
    }

    @Override
    @NonNull
    public StoragePictureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new StoragePictureAdapter.ViewHolder(view);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }

    @Override
    public void onBindViewHolder(@NonNull StoragePictureAdapter.ViewHolder holder, int position) {

        String name = filenameList[position].getName();
        if (pictureMap.get(name) != null) {
            Bitmap picture = pictureMap.get(name);
            holder.imgvPicture.setImageBitmap(picture);
        }
        holder.checkBoxPicture.setChecked(flags[position]);
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

            int pos=getAdapterPosition();
            Log.e("position",getAdapterPosition()+"___________"+filenameList[pos].getName());
            if (mClickListener != null) {
                if (flags[pos])
                    flags[pos] = false;
                else
                    flags[pos] = true;
                notifyDataSetChanged();

                String name = filenameList[pos].getName();
                mClickListener.onPictureClick(view, pictureMap.get(name));
            }
        }
    }

    public void setClickListener(StoragePictureAdapter.PictureClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface PictureClickListener {
        void onPictureClick(View view, Bitmap image);
    }

    public void updateImage(String name, Bitmap picture) {
        pictureMap.put(name, picture);
        parent.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }
}