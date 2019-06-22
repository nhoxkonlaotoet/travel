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
    private HashMap<String, List<Bitmap>> activityPhotosMap;
    int containerHeight, containerWidth;

    public ActivityAdapter(Context context, List<com.example.administrator.travel.models.entities.Activity> activityList) {
        if (context == null)
            return;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.activityList = activityList;
        dateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        userNameMap = new HashMap<>();
        userAvatarMap = new HashMap<>();
        activityPhotosMap = new HashMap<>();
        containerHeight = context.getResources().getDimensionPixelSize(R.dimen.container_height);
        containerWidth = getScreenWidth();
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
        holder.txtActivityTime.setText(dateFormat.format(date));
        if (userNameMap.get(activity.userId) != null) {
            holder.txtUserName.setText(userNameMap.get(activity.userId));
        }
        if (userAvatarMap.get(activity.userId) != null) {
            holder.imgvUserAvatar.setImageBitmap(userAvatarMap.get(activity.userId));
        }
        holder.txtActivityContent.setText(activity.content);
//        if (context != null) {
//            int n = activity.numberOfPicture;
//            int h, w;
//            if (n == 1) {
//                h = containerHeight;
//                w = containerWidth;
//                holder.layoutPicture.setColumnCount(1);
//                holder.layoutPicture.setRowCount(1);
//            } else if (n == 2) {
//                h = containerHeight * 2 / 3;
//                w = containerWidth / 2;
//                holder.layoutPicture.setColumnCount(2);
//                holder.layoutPicture.setRowCount(1);
//            } else {
//                h = containerHeight / 2;
//                w = containerWidth / 3;
//                holder.layoutPicture.setColumnCount(3);
//                holder.layoutPicture.setRowCount(2);
//            }
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w, h);
//            ImageView imageView;
//            for (int i = 0; i < activity.numberOfPicture; i++) {
//                imageView = new ImageView(context);
//                imageView.setLayoutParams(params);
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                if (activityPhotosMap.get(activity.id) != null) {
//                    List<Bitmap> photoList = activityPhotosMap.get(activity.id);
//                    if (photoList.size() > i && photoList.get(i) != null) {
//                        imageView.setImageBitmap(photoList.get(i));
//                        //   Log.e( "getView: ",activity.id+"" +activity.photoList.get(i));
//                    }
//                }
//                holder.layoutPicture.addView(imageView, i);
//            }
//        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtUserName, txtActivityTime, txtActivityContent;
        ImageView imgvUserAvatar;
        ImageButton btnOption;

        ViewHolder(View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtActivityTime = itemView.findViewById(R.id.txtActivityTime);
            txtActivityContent = itemView.findViewById(R.id.txtActivityContent);
            imgvUserAvatar = itemView.findViewById(R.id.imgvUserAvatar);
            btnOption = itemView.findViewById(R.id.btnOption);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onScheduleItemClick(view, "");
        }
    }

    public void setClickListener(ActivityAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onScheduleItemClick(View view, String scheduleId);
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

    public void updateImage(String activityId, Bitmap activityPhoto) {
        if (activityPhotosMap!=null) {
            List<Bitmap> photoList = new ArrayList<>();
            activityPhotosMap.put(activityId, photoList);
            activityPhotosMap.get(activityId).add(activityPhoto);
            notifyDataSetChanged();
        }


    }

    int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
