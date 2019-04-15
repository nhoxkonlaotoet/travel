package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.administrator.travel.models.LoadImageTask;
import com.example.administrator.travel.models.bases.ActivityInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.impls.ActivityInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.PostPresenter;
import com.example.administrator.travel.views.PostView;
import com.example.administrator.travel.views.activities.MapsActivity;
import com.example.administrator.travel.views.activities.PostActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/15/2019.
 */

public class PostPresenterImpl implements PostPresenter,
        Listener.OnLoadImageFinishedListener,
        Listener.OnPostActivityFinishedListener {
    PostView view;
    final static int LOCATION_REQUEST = 111;
    ActivityInteractor activityInteractor;
    UserInteractor userInteractor;
    String tourStartId;
    MyLatLng location;
    List<Bitmap> selectedImageList;
    boolean isLayoutPictureShown = false, firstClickPicture = true;

    public PostPresenterImpl(PostView view) {
        this.view = view;
        activityInteractor = new ActivityInteractorImpl();
        userInteractor = new UserInteractorImpl();
        selectedImageList = new ArrayList<>();
        location=new MyLatLng(0,0);
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        tourStartId = bundle.getString("tourStartId");
        boolean isActivity = bundle.getBoolean("isActivity");
        if (isActivity)
            view.viewOnPost();
        else
            view.viewOnReview();
    }

    @Override
    public void onButtonSendClicked(String content) {
        String userId = userInteractor.getUserId(view.getContext());
        if (!userId.equals("none")) {
            Activity activity = new Activity(userId, false, content, location, selectedImageList.size());
            activityInteractor.postActivity(tourStartId, userId, activity, selectedImageList, this);
        }

    }

    @Override
    public void onButtonPictureClicked() {
        if (isLayoutPictureShown) {
            isLayoutPictureShown = false;
            view.hideLayoutPicture();
        } else {
            isLayoutPictureShown = true;
            view.showLayoutPicture();
            if (firstClickPicture) {
                firstClickPicture = false;
                int i = 0;
                try {
                    File storage = new File(Environment.getExternalStorageDirectory() + "/DCIM/100ANDRO/");
                    view.showFramePictures(storage.listFiles().length);
                    for (File f : storage.listFiles()) {
                        new LoadImageTask(this).execute(i + "", f.getAbsolutePath());
                        i++;
                    }
                } catch (Exception ex) {
                }
            }
        }

    }

    @Override
    public void onButtonMarkLocationClicked() {
        Intent intent = new Intent(view.getContext(), MapsActivity.class);
        intent.putExtra("action", "choose");
        view.gotoMapActivity(intent, LOCATION_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOCATION_REQUEST) {
            // Log.e("onActivityResult: ", data.getExtras().getString("chosenLocation") + "");
            String[] arr = data.getExtras().getString("chosenLocation").split(",");
            location = new MyLatLng(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
        }
    }

    @Override
    public void onPictureItemClicked(View view, Bitmap image) {
        if (selectedImageList.contains(image))
            selectedImageList.remove(image);
        else
            selectedImageList.add(image);
        this.view.showFileCount(selectedImageList.size());
    }

    @Override
    public void onEditTextContentClicked() {
        isLayoutPictureShown=false;
        view.hideLayoutPicture();
    }

    @Override
    public void onButtonBackClicked() {
        view.finishView();
    }

    @Override
    public void onLoadImageSuccess(int pos, Bitmap image) {
        view.addPicture(pos, image);
    }

    @Override
    public void onPostActivitySuccess() {
        view.finishView();
    }

    @Override
    public void onPostActivityFail(Exception ex) {
        view.notifyFail(ex.getMessage());
    }
}
