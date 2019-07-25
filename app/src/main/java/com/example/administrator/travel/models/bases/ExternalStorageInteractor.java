package com.example.administrator.travel.models.bases;

import android.graphics.Bitmap;

import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 5/30/2019.
 */

public interface ExternalStorageInteractor {
    boolean isExistFile(String path, String fileName);
    void saveBitmapToExternalFile(String path, String fileName, Bitmap bitmap, int quality);
    void getBitmapFromExternalFile(String path, String fileName, Listener.OnLoadImageFinishedListener listener);

    void getBitmapThumpnailFromExternalFile(String path, String fileName, Listener.OnLoadImageThumpnailFinishedListener listener);

    void getBitmapThumpnailFromExternalFile(String path, String fileName, int quality, Listener.OnLoadImageThumpnailFinishedListener listener);
}
