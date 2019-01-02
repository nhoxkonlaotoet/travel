package com.example.administrator.travel.views;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Administrator on 31/12/2018.
 */

public interface PostView {
    void showLayoutPicture();
    void hideLayoutPicture();
    void showFramePictures(int length);
    void addPicture(int index, Bitmap bitmap, String path);
    int getScreenWidth();
    void showFileCount(int count);
    void gotoMapActivity();
    void viewOnPost();
    void viewOnReview();
    void close();
}
