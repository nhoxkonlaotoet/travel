package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.travel.adapter.ActivityAdapter;
import com.example.administrator.travel.adapter.NonScrollListView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.presenters.ActivityPresenter;
import com.example.administrator.travel.views.ActivityView;
import com.example.administrator.travel.views.activities.MapsActivity;
import com.example.administrator.travel.views.activities.PostActivity;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityFragment extends Fragment implements ActivityView {
    NonScrollListView lstvActivities;
    TextView txtActivityContent;
    ActivityAdapter adapter;
    ActivityPresenter presenter;
    Context context;
    ImageButton btnMap;
    public ActivityFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnMap=getActivity().findViewById(R.id.btnMap);
        Bundle bundle = getActivity().getIntent().getExtras();
        final String tourStartId= bundle.getString("tourStartId");

        txtActivityContent=getActivity().findViewById(R.id.txtActivityContent);
        lstvActivities = getActivity().findViewById(R.id.lstvStatus);
        //getAllStorageLocations();
        txtActivityContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               presenter.onTxtContentClicked();
            }
        });
        setBtnMapClick();
        presenter=new ActivityPresenter(this);
        presenter.onViewLoad(tourStartId);


    }
    void setBtnMapClick(){
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            presenter.onBtnMapClicked();
            }
        });
    }
    @Override
    public void gotoMapActivty(String tourStartId){
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra("openFrom","activity");
        intent.putExtra("tourStartId",tourStartId);
        startActivity(intent);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_communication, container, false);
    }

    @Override
    public void showActivities(List<Activity> lstActivity, Long currentTime) {
        if(context!=null) {
            adapter = new ActivityAdapter(context, lstActivity, currentTime);
            lstvActivities.setAdapter(adapter);
        }
    }

    @Override
    public void notifyGetActivitiesFailure(Exception ex) {

    }

    @Override
    public void addUserInfor(UserInformation info) {
        adapter.updateUserInfo(info);
    }

    @Override
    public void addImage(Bitmap Bitmap, int index, String activityId) {
        adapter.updateImage(Bitmap, index, activityId);
    }

    @Override
    public void gotoPostActiivty(String tourStartId, boolean isTourGuide) {



        Intent intent = new Intent(getActivity(), PostActivity.class);
        intent.putExtra("tourStartId",tourStartId);
        intent.putExtra("isActivity",true);
        intent.putExtra("isTourGuide", isTourGuide);
        startActivity(intent);
    }

}
