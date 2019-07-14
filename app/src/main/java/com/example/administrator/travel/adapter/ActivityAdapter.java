package com.example.administrator.travel.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 01/01/2019.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private ActivityAdapter.ItemClickListener mClickListener;
    private List<com.example.administrator.travel.models.entities.Activity> activityList;
    private DateFormat dateFormat;
    private HashMap<String, String> userNameMap;
    private HashMap<String, Bitmap> userAvatarMap;

    public ActivityAdapter(Context context, List<com.example.administrator.travel.models.entities.Activity> activityList) {
        if (context == null)
            return;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.activityList = activityList;
        dateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        userNameMap = new HashMap<>();
        userAvatarMap = new HashMap<>();

    }

    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null)
            return null;
        View view = mInflater.inflate(R.layout.item_activity, parent, false);
        return new ActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final com.example.administrator.travel.models.entities.Activity activity = activityList.get(position);
        Date date = new Date(activity.postTime);
        if (userNameMap.get(activity.userId) != null) {

        }
        if (userAvatarMap.get(activity.userId) != null) {
            holder.imgvUserAvatar.setImageBitmap(userAvatarMap.get(activity.userId));
        }
        holder.txtActivityContent.setText(activity.content);
        holder.txtActivityTime.setText(dateFormat.format(date));

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtActivityTime, txtActivityContent;
        ImageView imgvUserAvatar;

        ViewHolder(View itemView) {
            super(itemView);
            txtActivityTime = itemView.findViewById(R.id.txtActivityTime);
            txtActivityContent = itemView.findViewById(R.id.txtActivityContent);
            imgvUserAvatar = itemView.findViewById(R.id.imgvUserAvatar);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onActivityItemClick(view, "");
        }
    }

    public void setClickListener(ActivityAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onActivityItemClick(View view, String activityId);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }


    public void updateUserName(String userId, String userName) {
        if (userNameMap != null) {
            userNameMap.put(userId, userName);
            notifyDataSetChanged();
        }
    }

    public void updateUserAvatar(String userId, Bitmap avatarPhoto) {
        if (userAvatarMap != null) {
            userAvatarMap.put(userId, avatarPhoto);
            notifyDataSetChanged();
        }
    }
}
