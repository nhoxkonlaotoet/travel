package com.example.administrator.travel.presenters;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.example.administrator.travel.models.OnGetTourImagesFinishedListener;
import com.example.administrator.travel.models.OnTourFinishedListener;
import com.example.administrator.travel.models.TourInteractor;
import com.example.administrator.travel.views.NewFeedView;
import com.example.administrator.travel.views.TourView;
import com.example.administrator.travel.views.activities.TourActivity;
import com.google.android.gms.common.ConnectionResult;

/**
 * Created by Administrator on 10/11/2018.
 */

public class TourPresenter{// implements OnGetTourImagesFinishedListener,OnTourFinishedListener{
//    TourView view;
//    TourInteractor interactor;
//    Boolean isShareLocation;
//    String tourStartId;
//    public TourPresenter(TourView view)
//    {
//        this.view=view;
//        interactor=new TourInteractor();
//    }
//
//    public void onViewLoad(String tourId,String tourStartId, Boolean isMyTour){
//        if(!isMyTour)
//            interactor.getImages(tourId,this);
//        else
//        {
//            this.tourStartId=tourStartId;
//        }
//        view.addTab(isMyTour);
//        interactor.setTourFinishListener(((TourActivity)view).getApplicationContext(),this);
//        isShareLocation=interactor.isShareLocation((TourActivity)view);
//    }
//
//
//    @Override
//    public void onSuccess(Bitmap[] bitmaps, Integer numberofImages) {
//        for(int i=0;i<numberofImages;i++)
//        {
//            Log.e("presenter tour",bitmaps[i].getByteCount()+"" );
//        }
//        view.showImages(bitmaps,numberofImages);
//
//    }
//
//    @Override
//    public void onFailure() {
//
//    }
//
//    @Override
//    public void onTourFinished() {
//        view.closebyTourFinished();
//    }
}
