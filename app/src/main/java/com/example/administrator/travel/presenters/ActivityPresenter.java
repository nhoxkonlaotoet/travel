package com.example.administrator.travel.presenters;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.administrator.travel.models.ActivityInteractor;
import com.example.administrator.travel.models.OnGetActivityFinishedListener;
import com.example.administrator.travel.models.OnGetUserInforFinishedListener;
import com.example.administrator.travel.models.UserInformationInteractor;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.views.ActivityView;
import com.example.administrator.travel.views.fragments.ActivityFragment;

import java.util.List;

/**
 * Created by Administrator on 01/01/2019.
 */

public class ActivityPresenter implements OnGetActivityFinishedListener, OnGetUserInforFinishedListener{
    ActivityView view;
    ActivityInteractor activityInteractor;
    UserInformationInteractor userInformationInteractor;
    String tourStartId, tourGuideId;
    boolean isTourGuide=false;
    public ActivityPresenter(ActivityView view){
        this.view=view;
        activityInteractor=new ActivityInteractor();
        userInformationInteractor=new UserInformationInteractor();
    }

    public void onViewLoad(String tourStartId){
        this.tourStartId=tourStartId;
        activityInteractor.getActivities(tourStartId,this);
        userInformationInteractor.isTourGuide(tourStartId,((ActivityFragment)view).getActivity().getApplicationContext(),this);
    }
    public void onTxtContentClicked(){
        view.gotoPostActiivty(tourStartId,isTourGuide);
    }
    @Override
    public void onGetActivitiesSuccess(List<Activity> lstActivity, Long currentTime) {
        view.showActivities(lstActivity, currentTime);
        for(Activity activity : lstActivity){
            String userId = activity.userId;
            userInformationInteractor.getUserInfor(userId,this);
            int n=activity.numberOfPicture;
            for(int i=0;i<n;i++)
                activityInteractor.getImage(tourStartId,activity.id,i,this);
        }
    }

    @Override
    public void onGetActivitiesFailure(Exception ex) {
        view.notifyGetActivitiesFailure(ex);
    }

    @Override
    public void onGetActivityImagesSuccess(Bitmap Bitmap, int index, String activityId) {
      //  Log.e("get image: ", activityId+"___"+index+"____"+Bitmap);
        view.addImage(Bitmap,index,activityId);
    }

    @Override
    public void onGetActivityImageFailure(Exception ex) {

    }

    @Override
    public void onGetUserInforSuccess(UserInformation info) {
    //    Log.e( "onGetUserInforSuccess: ", info.name + " "+ info.avatar);
        view.addUserInfor(info);
    }

    @Override
    public void onGetUserInforFailure(Exception ex) {

    }

    @Override
    public void onCheckTourGuideTrue() {
        isTourGuide=true;
    }

    @Override
    public void onCheckTourGuideFalse(String tourGuideId) {
        isTourGuide=false;
        this.tourGuideId=tourGuideId;
    }

    @Override
    public void onCheckTourGuideFailure(Exception ex) {

    }
}
