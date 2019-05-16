package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.listeners.Listener;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/15/2019.
 */

public interface PicassoInteractor {
    void load(Context context, int pos ,String url, Listener.OnPicassoLoadFinishedListener listener);

    void cleanGarbages();
}
