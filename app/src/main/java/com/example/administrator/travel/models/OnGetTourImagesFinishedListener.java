package com.example.administrator.travel.models;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 10/11/2018.
 */

public interface OnGetTourImagesFinishedListener {
    public void onSuccess(Bitmap[] bitmaps, Integer numberofImages);
    public void onFailure();
}
