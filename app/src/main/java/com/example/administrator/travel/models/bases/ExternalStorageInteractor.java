package com.example.administrator.travel.models.bases;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 5/30/2019.
 */

public interface ExternalStorageInteractor {
    boolean isExistFile(String path, String fileName);
    void saveBitmapToExternalFile(String path, String fileName, Bitmap bitmap);
    void loadBitmapFromExternalFile(String path, String fileName, Listener.OnLoadImageFinishedListener listener);
}
