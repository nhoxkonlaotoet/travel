package com.example.administrator.travel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.TourStartDate;
import com.example.administrator.travel.models.entities.UserInformation;
import com.google.firebase.auth.UserInfo;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 01/01/2019.
 */

public class ActivityAdapter extends BaseAdapter {
    Context context;
    List<com.example.administrator.travel.models.entities.Activity> lstActivity;
    DateFormat dateFormat ;
    Long currentTime;
    List<UserInformation> lstUser;
    int containerHeight,containerWidth;
    public ActivityAdapter(Context context, List<com.example.administrator.travel.models.entities.Activity> lstActivity, Long currentTime)
    {
        this.context=context;
        this.lstActivity=lstActivity;
        dateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        this.currentTime=currentTime;
        lstUser = new ArrayList<>();
        containerHeight = context.getResources().getDimensionPixelSize(R.dimen.container_height);
        containerWidth=getScreenWidth();
    }
    @Override
    public int getCount() {
        return lstActivity.size();
    }

    @Override
    public Object getItem(int position) {
        return lstActivity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.status_item, null);
        convertView.setTag(lstActivity.get(position).id);
        ImageView imgvUserAvatar = convertView.findViewById(R.id.imgvUserAvatar);
        TextView txtUserName = convertView.findViewById(R.id.txtUserName);
        TextView txtActivityTime = convertView.findViewById(R.id.txtActivityTime);
        ImageButton btnOption = convertView.findViewById(R.id.btnOption);
        TextView txtActivityContent = convertView.findViewById(R.id.txtActivityContent);
        GridLayout layoutPicture = convertView.findViewById(R.id.layoutPicture);

        final com.example.administrator.travel.models.entities.Activity activity = lstActivity.get(position);

        Date date = new Date(activity.postTime);

        txtActivityTime.setText(dateFormat.format(date));
       // txtUserName.setText(activity.userId);
        for(int i=0;i<lstUser.size();i++)
            if(lstUser.get(i).id.equals(activity.userId)) {
                txtUserName.setText(lstUser.get(i).name);
                imgvUserAvatar.setImageBitmap(lstUser.get(i).avatar);
                break;
            }

        txtActivityContent.setText(activity.content);
        if(context!=null) {
            int n=activity.numberOfPicture;
            int h,w;
            if(n==1) {
                h = containerHeight;
                w = containerWidth;
                layoutPicture.setColumnCount(1);
                layoutPicture.setRowCount(1);
            }
            else if(n==2)
            {
                h=containerHeight*2/3;
                w=containerWidth/2;
                layoutPicture.setColumnCount(2);
                layoutPicture.setRowCount(1);
            }
            else
            {
                h=containerHeight/2;
                w=containerWidth/3;
                layoutPicture.setColumnCount(3);
                layoutPicture.setRowCount(2);
            }
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w,h) ;
            ImageView imageView;
            for (int i = 0; i < activity.numberOfPicture; i++) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if(activity.lstPicture!=null && activity.lstPicture.size()>i && activity.lstPicture.get(i)!=null) {
                    imageView.setImageBitmap(activity.lstPicture.get(i));
                 //   Log.e( "getView: ",activity.id+"" +activity.lstPicture.get(i));

                }
                layoutPicture.addView(imageView, i);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       // Log.e( "onClick: ", activity.id);
                    }
                });
            }
        }

        return convertView;
    }
    public void updateUserInfo(UserInformation info){
        int n=lstUser.size();
        String id = info.id;
        for(int i=0;i<n;i++)
        {
            if(lstUser.get(i).id.equals(id))
                return;
        }
        lstUser.add(info);
        this.notifyDataSetChanged();


    }
    public void updateImage(Bitmap Bitmap, int index, String activityId){
        for(com.example.administrator.travel.models.entities.Activity activity : lstActivity)
        {
            if(activity.id.equals(activityId)) {
                activity.lstPicture.add( Bitmap);
               // Log.e( "updateImage: ", activity.id+","+ activity.lstPicture.get(activity.lstPicture.size()-1)+"" );
                break;
            }
        }

        this.notifyDataSetChanged();
    }
    int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
