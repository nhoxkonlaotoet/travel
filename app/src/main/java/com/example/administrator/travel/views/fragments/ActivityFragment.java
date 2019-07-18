package com.example.administrator.travel.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.travel.adapter.ActivityAdapter;
import com.example.administrator.travel.R;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.Participant;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.presenters.bases.ActivityPresenter;
import com.example.administrator.travel.presenters.impls.ActivityPresenterImpl;
import com.example.administrator.travel.views.bases.ActivityView;
import com.example.administrator.travel.views.activities.CreateActivityActivity;

import java.util.List;


public class ActivityFragment extends Fragment implements ActivityView {
    RecyclerView recyclerViewActivity;
    CardView layoutStatusPost;
    ActivityAdapter adapter;
    ActivityPresenter presenter;
    Button btnFinishTour;
    public ActivityFragment() {
        // Required empty public constructor
    }
    @Override
    public void showButtonFinish(){btnFinishTour.setVisibility(View.VISIBLE);}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutStatusPost = getActivity().findViewById(R.id.layoutStatusPost);
        recyclerViewActivity = getActivity().findViewById(R.id.recyclerViewActivity);
        btnFinishTour =getActivity().findViewById(R.id.btnFinishTour);
        btnFinishTour.setVisibility(View.GONE);
        setTextContentClick();
        Bundle bundle = getActivity().getIntent().getExtras();
        presenter = new ActivityPresenterImpl(this);
        presenter.onViewCreated(bundle);

        btnFinishTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBtnFinishTourClick();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onViewResult(requestCode,resultCode,data);
    }

    void setTextContentClick() {
        layoutStatusPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onTextContentClicked();
            }
        });
    }

    @Override
    public void gotoMapActivty(String tourStartId) {
//        Intent intent = new Intent(getActivity(), MapsActivity.class);
//        intent.putExtra("openFrom", "activity");
//        intent.putExtra("tourStartId", tourStartId);
//        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_activity, container, false);
    }

    @Override
    public void showActivities(List<Activity> activityList) {

        adapter = new ActivityAdapter(getActivity(), activityList);
       // friendContactAdapter.setClickListener(this);
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
    public void gotoPostActivity(String tourStartId, boolean isTourGuide) {
        Intent intent = new Intent(getActivity(), CreateActivityActivity.class);
        intent.putExtra("tourStartId", tourStartId);
        intent.putExtra("isActivity", true);
        intent.putExtra("isTourGuide", isTourGuide);
        startActivityForResult(intent,128);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
