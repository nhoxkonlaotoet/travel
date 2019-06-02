package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.travel.adapter.ActivityAdapter;
import com.example.administrator.travel.adapter.NonScrollListView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.presenters.bases.ActivityPresenter;
import com.example.administrator.travel.presenters.impls.ActivityPresenterImpl;
import com.example.administrator.travel.views.ActivityView;
import com.example.administrator.travel.views.activities.MapsActivity;
import com.example.administrator.travel.views.activities.PostActivity;

import java.util.List;


public class ActivityFragment extends Fragment implements ActivityView {
    RecyclerView recyclerViewActivity;
    TextView txtActivityContent;
    ActivityAdapter adapter;
    ActivityPresenter presenter;
    ImageButton btnMap;

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnMap = getActivity().findViewById(R.id.btnMap);
        txtActivityContent = getActivity().findViewById(R.id.txtActivityContent);
        recyclerViewActivity = getActivity().findViewById(R.id.recyclerViewActivity);

        setBtnMapClick();
        setEditTextContentClick();
        Bundle bundle = getActivity().getIntent().getExtras();
        presenter = new ActivityPresenterImpl(this);
        presenter.onViewCreated(bundle);

    }

    void setBtnMapClick() {
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonMapClicked();
            }
        });
    }

    void setEditTextContentClick() {
        txtActivityContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onEditTextContentClicked();
            }
        });
    }

    @Override
    public void gotoMapActivty(String tourStartId) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra("openFrom", "activity");
        intent.putExtra("tourStartId", tourStartId);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragement_activity, container, false);
    }

    @Override
    public void showActivities(List<Activity> lstActivity) {

        adapter = new ActivityAdapter(getActivity(), lstActivity);
       // adapter.setClickListener(this);
        recyclerViewActivity.setAdapter(adapter);

    }

    @Override
    public void notifyGetActivitiesFailure(Exception ex) {

    }

    @Override
    public void updateUserName(String userId, String userName) {
        adapter.updateUserName(userId, userName);
    }

    @Override
    public void updateUserAvatar(String userId, Bitmap avatar) {
        adapter.updateUserAvatar(userId, avatar);
    }

    @Override
    public void updateActivityImage(String activityId, Bitmap activityPhoto) {
        adapter.updateImage(activityId, activityPhoto);
    }

    @Override
    public void gotoPostActivity(String tourStartId, boolean isTourGuide) {
        Intent intent = new Intent(getActivity(), PostActivity.class);
        intent.putExtra("tourStartId", tourStartId);
        intent.putExtra("isActivity", true);
        intent.putExtra("isTourGuide", isTourGuide);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
