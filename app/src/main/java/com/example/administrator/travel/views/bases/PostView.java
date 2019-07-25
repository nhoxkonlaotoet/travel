package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 31/12/2018.
 */

public interface PostView {
    void showLayoutPicture();
    void hideLayoutPicture();
    void showFramePictures(int length, File[] filenameList);
    void addPicture(String filename, Bitmap bitmap);
    void showFileCount(int count);
    void gotoMapActivity(Intent intent, int requestCode);
    void viewOnPost();
    void viewOnReview();
    void finishView();
    void finishViewReturnResult(int resultCode);

    void notifyFail(String message);
    Context getContext();

    void showWaitDialog();

    void closeWaitDialog();
}
