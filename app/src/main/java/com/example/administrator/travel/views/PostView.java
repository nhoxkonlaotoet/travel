package com.example.administrator.travel.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Administrator on 31/12/2018.
 */

public interface PostView {
    void showLayoutPicture();
    void hideLayoutPicture();
    void showFramePictures(int length);
    void addPicture(int index, Bitmap bitmap);
    void showFileCount(int count);
    void gotoMapActivity(Intent intent, int requestCode);
    void viewOnPost();
    void viewOnReview();
    void finishView();
    void finishViewReturnResult(int resultCode);

    void notifyFail(String message);
    Context getContext();
}
