package com.example.administrator.travel.presenters;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.administrator.travel.models.NewFeedInteractorImpl;
import com.example.administrator.travel.models.OnGetTourImagesFinishedListener;
import com.example.administrator.travel.models.TourInteractor;
import com.example.administrator.travel.views.NewFeedView;
import com.example.administrator.travel.views.TourView;

/**
 * Created by Administrator on 10/11/2018.
 */

public class TourPresenter implements OnGetTourImagesFinishedListener{
    TourView view;
    TourInteractor interactor;
    public TourPresenter(TourView view)
    {
        this.view=view;
        interactor=new TourInteractor();

    }
    public void getTourImages(String tourId)
    {
        interactor.getImages(tourId,this);
    }
    @Override
    public void onSuccess(Bitmap[] bitmaps, Integer numberofImages) {
        for(int i=0;i<numberofImages;i++)
        {
            Log.e("presenter tour",bitmaps[i].getByteCount()+"" );
        }
        view.showImages(bitmaps,numberofImages);

    }

    @Override
    public void onFailure() {

    }
}
