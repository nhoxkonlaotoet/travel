package com.example.administrator.travel.presenters.impls;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.example.administrator.travel.models.LoadImageTask;
import com.example.administrator.travel.models.bases.ActivityInteractor;
import com.example.administrator.travel.models.bases.ExternalStorageInteractor;
import com.example.administrator.travel.models.bases.RatingInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.models.entities.MyLatLng;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.impls.ActivityInteractorImpl;
import com.example.administrator.travel.models.impls.ExternalStorageInteractorImpl;
import com.example.administrator.travel.models.impls.RatingInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.PostPresenter;
import com.example.administrator.travel.views.bases.PostView;
import com.example.administrator.travel.views.activities.MapsActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Admin on 4/15/2019.
 */

public class PostPresenterImpl implements PostPresenter,
        Listener.OnPostActivityFinishedListener, Listener.OnRateTourFinishedListener,
        Listener.OnLoadImageThumpnailFinishedListener {
    PostView view;
    final static int LOCATION_REQUEST = 111;
    ActivityInteractor activityInteractor;
    RatingInteractor ratingInteractor;
    UserInteractor userInteractor;
    String tourStartId, tourId;
    float rating;
    MyLatLng location;
    List<Bitmap> selectedImageList;
    boolean isActivity, isLayoutPictureShown = false, firstClickPicture = true;
    ExternalStorageInteractor externalStorageInteractor;

    public PostPresenterImpl(PostView view) {
        this.view = view;
        activityInteractor = new ActivityInteractorImpl();
        userInteractor = new UserInteractorImpl();
        ratingInteractor = new RatingInteractorImpl();
        selectedImageList = new ArrayList<>();
        location = new MyLatLng(0, 0);
        externalStorageInteractor = new ExternalStorageInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        tourStartId = bundle.getString("tourStartId");
        isActivity = bundle.getBoolean("isActivity");
        if (isActivity)
            view.viewOnPost();
        else {
            view.viewOnReview();
            rating = bundle.getFloat("rating");
            tourId = bundle.getString("tourId");
        }
    }

    @Override
    public void onButtonSendClicked(String content) {
        String userId = userInteractor.getUserId();
        if (!userId.equals("none")) {
            if (isActivity) {
                // Activity activity = new Activity(myId, false, content, location, selectedImageList.size());
                // activityInteractor.postActivity(tourStartId, myId, activity, selectedImageList, this);
            } else {
                view.showWaitDialog();
                Rating rating = new Rating(this.rating, userId, selectedImageList.size(), content);
                ratingInteractor.rateTour(tourId, rating, selectedImageList, this);
            }
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
                    File storage = new File(Environment.getExternalStorageDirectory() + "/DCIM/CAMERA/");
                    view.showFramePictures(storage.listFiles().length, storage.listFiles());
                    for (File f : storage.listFiles()) {
                        int quality = 1;
                        if (f.length() > 1024) {
                            quality += (int) ((float) f.length() / (1024 * 1024 * 1.5));
                        }
                        Log.e("size", quality+"");
                        externalStorageInteractor.getBitmapThumpnailFromExternalFile("/DCIM/CAMERA/", f.getName(), quality, this);

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
        isLayoutPictureShown = false;
        view.hideLayoutPicture();
    }

    @Override
    public void onButtonBackClicked() {
        view.finishView();
    }

    @Override
    public void onPostActivitySuccess() {
        view.finishViewReturnResult(RESULT_OK);
    }

    @Override
    public void onPostActivityFail(Exception ex) {
        view.notifyFail(ex.getMessage());
        view.finishViewReturnResult(RESULT_CANCELED);
    }

    @Override
    public void onRateTourSuccess() {
        view.closeWaitDialog();
        view.finishViewReturnResult(RESULT_OK);
    }

    @Override
    public void onRateTourFail(Exception ex) {
        view.notifyFail(ex.getMessage());
        view.closeWaitDialog();
        view.finishViewReturnResult(RESULT_CANCELED);
    }

    @Override
    public void onLoadImageThumpnailSuccess(String fileName, Bitmap image) {
        view.addPicture(fileName, image);
    }

}
