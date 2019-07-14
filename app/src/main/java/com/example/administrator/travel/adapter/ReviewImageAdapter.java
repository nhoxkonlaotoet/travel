package com.example.administrator.travel.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.RatingInteractor;
import com.example.administrator.travel.models.impls.RatingInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;

import java.util.HashMap;

public class ReviewImageAdapter extends RecyclerView.Adapter<ReviewImageAdapter.ViewHolder> implements Listener.OnGetReviewImageFinishedListener {
    private ReviewImageAdapter.ItemClickListener mClickListener;
    private RecyclerView parent;

    private HashMap<Integer, Bitmap> imageMap;
    private String tourId, reviewerId;
    private int numberOfImages;
    private RatingInteractor ratingInteractor;
    private boolean[] loadFlags;
    public ReviewImageAdapter(String tourId, String reviewerId, int numberOfImages){
        this.tourId=tourId;
        this.reviewerId=reviewerId;
        this.numberOfImages=numberOfImages;
        this.imageMap=new HashMap<>();
        ratingInteractor=new RatingInteractorImpl();
        loadFlags = new boolean[numberOfImages];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_image, parent, false);
        return new ReviewImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(!loadFlags[position]){
            Log.e( "onBindViewHolder: ",tourId + reviewerId );
            ratingInteractor.getReviewImage(position,tourId,reviewerId,this);
            loadFlags[position]=true;
        }
        else{
            if(imageMap.get(position)!=null){
                holder.imgvReviewImage.setImageBitmap(imageMap.get(position));
            }
        }

    }

    @Override
    public int getItemCount() {
        return numberOfImages;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }

    @Override
    public void onGetReviewImageSuccess(int index, String tourId, Bitmap image) {
        Log.e("onGetReviewImg: ", index+"");
        imageMap.put(index,image);
        parent.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void setClickListener(ReviewImageAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgvReviewImage;

        public ViewHolder(View itemView) {
            super(itemView);
            imgvReviewImage = itemView.findViewById(R.id.imgvReviewImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onReviewImageItemClick(view, tourId,reviewerId,getAdapterPosition());
            }
        }
    }
    public interface ItemClickListener {
        void onReviewImageItemClick(View view, String tourId, String reviewerId, int index);
    }
}
