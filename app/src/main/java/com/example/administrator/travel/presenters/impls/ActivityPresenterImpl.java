package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.travel.models.bases.ActivityInteractor;
import com.example.administrator.travel.models.bases.TourStartInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.impls.ActivityInteractorImpl;
import com.example.administrator.travel.models.impls.TourStartInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.ActivityPresenter;
import com.example.administrator.travel.views.ActivityView;
import com.google.firebase.database.DatabaseException;

import java.util.List;

/**
 * Created by Admin on 5/26/2019.
 */

public class ActivityPresenterImpl implements ActivityPresenter, Listener.OnGetActivitiesFinishedListener,
        Listener.OnGetUserInforFinishedListener,
        Listener.OnGetActivityPhotosFinishedListener, Listener.OnGetUserAvatarFinishedListener {
    private ActivityView view;
    private ActivityInteractor activityInteractor;
    private TourStartInteractor tourStartInteractor;
    private UserInteractor userInteractor;
    String tourStartId, tourGuideId;
    boolean isTourGuide = false;


    public ActivityPresenterImpl(ActivityView view) {
        this.view = view;
        tourStartInteractor = new TourStartInteractorImpl();
        userInteractor = new UserInteractorImpl();
        activityInteractor = new ActivityInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        tourStartId = bundle.getString("tourStartId");
        tourGuideId = tourStartInteractor.getTourGuideId(tourStartId,view.getContext());
        if (userInteractor.getUserId(view.getContext()).equals(tourGuideId))
            isTourGuide = true;
        activityInteractor.getActivities(tourStartId, this);
    }

    @Override
    public void onButtonMapClicked() {
        view.gotoMapActivty(tourStartId);
    }

    @Override
    public void onEditTextContentClicked() {
        view.gotoPostActivity(tourStartId, isTourGuide);
    }

    @Override
    public void onGetViewResult(Intent intent) {

    }

    @Override
    public void onGetUserInforSuccess(UserInformation user) {
        view.updateUserName(user.id, user.name);
        Log.e("onGetUserSuccess: ", user.id+"_______"+user.name);
    }

    @Override
    public void onGetActivitiesSuccess(List<Activity> activityList) {
        view.showActivities(activityList);
        for (Activity activity : activityList) {
            String userId = activity.userId;
            userInteractor.getUserInfor(userId, this);
            userInteractor.getUserAvatar(userId, this);
            int n = activity.numberOfPicture;
            for (int i = 0; i < n; i++)
                activityInteractor.getActivitiyPhoto(i, tourStartId, activity.id, this);
        }
    }

    @Override
    public void onGetActivitiesFailure(DatabaseException e) {

    }


    @Override
    public void onGetActivityPhotosSuccess(String activityId, Bitmap photo) {
        view.updateActivityImage(activityId, photo);
        Log.e("onGetPhotosSuccess: ","___"+photo.getByteCount() );
    }

    @Override
    public void onGetUserAvatarSuccess(String userId, Bitmap avatar) {
        view.updateUserAvatar(userId, avatar);
        Log.e("onGetAvatarFinished: ","_____"+ avatar.getByteCount());
    }
}
