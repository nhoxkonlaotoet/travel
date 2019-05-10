package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Admin on 5/8/2019.
 */

public interface ZoomView {
    void showPhoto(Bitmap photo);

    void close();

    Context getContext();
}
