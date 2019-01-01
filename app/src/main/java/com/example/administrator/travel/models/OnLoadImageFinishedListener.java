package com.example.administrator.travel.models;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 31/12/2018.
 */

public interface OnLoadImageFinishedListener {
    void onGetImageCoutnSuccess(int length);
    void onGetImageCountFailure(Exception ex);
    void onGetImageSuccess(int index, Bitmap bitmap,String path);
    void onGetImageFailure(Exception ex);
}
