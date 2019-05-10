package com.example.administrator.travel.presenters;

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
//    public void onViewCreated(String tourId,String tourStartId, Boolean isMyTour){
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
