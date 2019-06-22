package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.entities.place.detail.Review;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 26/12/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> implements Listener.OnGetUserInforFinishedListener, Listener.OnGetUserAvatarFinishedListener {
    private Context context;
    private LayoutInflater mInflater;
    private ReviewAdapter.ItemClickListener mClickListener;
    private RecyclerView parent;

    private UserInteractor userInteractor;

    private List<Rating> ratingList;
    private HashMap<String, String> userNameMap;
    private HashMap<String, Bitmap> userAvatarMap;

    private boolean[] loadUserInfoFlags;
    private DateFormat dateFormat;
    private String myId;

    public ReviewAdapter(Context context, List<Rating> ratingList) {
        if (context == null)
            return;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.ratingList = ratingList;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        userNameMap = new HashMap<>();
        userAvatarMap = new HashMap<>();
        loadUserInfoFlags = new boolean[ratingList.size()];
        userInteractor = new UserInteractorImpl();
        myId = userInteractor.getUserId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_review, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parent = recyclerView;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mInflater == null)
            return;
        Rating rating = ratingList.get(position);
        Date date = new Date(rating.ratingTime);
        if (!loadUserInfoFlags[position]) {
            loadUserInfoFlags[position] = true;
            userInteractor.getUserInfor(rating.ratingPeopleId, this);
            userInteractor.getUserAvatar(rating.ratingPeopleId, this);
        }
        if (userNameMap.get(rating.ratingPeopleId) != null) {
            holder.txtReviewerName.setText(userNameMap.get(rating.ratingPeopleId));
        }
        if (userAvatarMap.get(rating.ratingPeopleId) != null) {
            holder.imgvReviewer.setImageBitmap(userAvatarMap.get(rating.ratingPeopleId));
        }
        if (rating.likes != null && rating.likes.get(myId) != null)
            if (rating.likes.get(myId)) {
                holder.checkboxLike.setTag(true);
                holder.checkboxLike.setChecked(true);
            } else {
                holder.checkboxDislike.setTag(true);
                holder.checkboxDislike.setChecked(true);
            }
        holder.txtRatingDate.setText(dateFormat.format(date));
        holder.ratingBarReview.setRating(rating.rating);
        holder.txtReviewContent.setText(rating.content);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtReviewerName, txtRatingDate, txtReviewContent;
        RatingBar ratingBarReview;
        ImageView imgvReviewer;
        CheckBox checkboxLike, checkboxDislike;

        ViewHolder(View itemView) {
            super(itemView);
            txtReviewerName = itemView.findViewById(R.id.txtReviewerName);
            txtRatingDate = itemView.findViewById(R.id.txtRatingDate);
            ratingBarReview = itemView.findViewById(R.id.ratingBarReview);
            txtReviewContent = itemView.findViewById(R.id.txtReviewCOntent);
            imgvReviewer = itemView.findViewById(R.id.imgvReviewer);
            checkboxLike = itemView.findViewById(R.id.checkboxLike);
            checkboxDislike = itemView.findViewById(R.id.checkboxDislike);
            txtReviewContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null)
                        mClickListener.onReviewItemClick(view, ratingList.get(getAdapterPosition()).id);
                }
            });

            checkboxLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        if(checkboxDislike.isChecked()) {
                            checkboxDislike.setTag(true);
                            checkboxDislike.setChecked(!b);
                        }
                    }
                    if (checkboxLike.getTag() != null)
                        checkboxLike.setTag(null);
                    else if (mClickListener != null) {
                        mClickListener.onButtonLikeReviewItemClick(checkboxLike, ratingList.get(getAdapterPosition()).id, b);
                    }
                }
            });
            checkboxDislike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        if(checkboxLike.isChecked()) {
                            checkboxLike.setTag(true);
                            checkboxLike.setChecked(!b);
                        }
                    }
                    if (checkboxDislike.getTag() != null)
                        checkboxDislike.setTag(null);
                    else if (mClickListener != null) {
                        mClickListener.onButtonDislikeReviewItemClick(checkboxDislike, ratingList.get(getAdapterPosition()).id, b);
                    }
                }
            });
        }


    }

    public void setClickListener(ReviewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onReviewItemClick(View view, String reviewId);

        void onButtonLikeReviewItemClick(View view, String reviewId, boolean pressed);

        void onButtonDislikeReviewItemClick(View view, String reviewId, boolean pressed);
    }

    public void updateUserName(String userId, String username) {
        userNameMap.put(userId, username);
        parent.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    public void updateUserAvatar(String userId, Bitmap userAvatar) {
        userAvatarMap.put(userId, userAvatar);
        parent.post(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onGetUserInforSuccess(UserInformation user) {
        updateUserName(user.id, user.name);
    }

    @Override
    public void onGetUserAvatarSuccess(String userId, Bitmap avatar) {
        updateUserAvatar(userId, avatar);
    }
}