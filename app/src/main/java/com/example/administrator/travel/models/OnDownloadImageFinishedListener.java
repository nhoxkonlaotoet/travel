package com.example.administrator.travel.models;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 30/12/2018.
 */

public interface OnDownloadImageFinishedListener {
    void onDownloadImageSuccess(int index, Bitmap bitmap);
    void onDownloadImageFailure(Exception ex);
}
