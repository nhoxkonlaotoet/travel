package com.example.administrator.travel.models.impls;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.administrator.travel.models.bases.PicassoInteractor;
import com.example.administrator.travel.models.listeners.Listener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/15/2019.
 */

public class PicassoInteractorImpl implements PicassoInteractor {
    List<Target> targetList = new ArrayList<>();


    @Override
    public void load(Context context, final int pos, String url, final Listener.OnPicassoLoadFinishedListener listener) {
        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                listener.onPicassoLoadSuccess(pos,bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        targetList.add(target);
        Picasso.with(context).load(url).into(target);
    }
    public void cleanGarbage(){
        targetList.clear();
    }
}
