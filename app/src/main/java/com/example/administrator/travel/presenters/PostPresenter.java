package com.example.administrator.travel.presenters;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

import com.example.administrator.travel.OnPostActivityFinishedListener;
import com.example.administrator.travel.adapter.PictureItem;
import com.example.administrator.travel.models.OnLoadImageFinishedListener;
import com.example.administrator.travel.models.PostInteractor;
import com.example.administrator.travel.models.entities.Activity;
import com.example.administrator.travel.views.PostView;
import com.example.administrator.travel.views.activities.PostActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 31/12/2018.
 */

public class PostPresenter implements OnLoadImageFinishedListener,OnPostActivityFinishedListener {
    boolean isActivty;
    PostView view;
    PostInteractor interactor;
    boolean firstLoad=true,showPicture=false;
    Location location;
    List<Bitmap> lstImage;
    String tourStartId;
    public PostPresenter(PostView view)
    {
        this.view=view;
        interactor=new PostInteractor();
        lstImage=new ArrayList<>();
    }
    public void onViewLoad(){
        isActivty=true;
    }
    public void onBtnPictureClicked(){
        if (firstLoad) {
            interactor.loadExternalPictures(this);
            firstLoad=false;
        }
        if(showPicture) {
            showPicture=false;
            view.hideLayoutPicture();
        }
        else {
            showPicture=true;
            view.showLayoutPicture();
        }
    }
    public void onPitureItemClicked(PictureItem view){
        if(view.isChosen())
            lstImage.add(view.getImageBitmap());
        else
            lstImage.remove(view.getImageBitmap());

        Log.e("list files", lstImage.size()+"" );
        this.view.showFileCount(lstImage.size());
    }

    public void onBtnPostClicked(String tourStartId, String content){

        interactor.postActivity(tourStartId,false,content,lstImage,location,((PostActivity)view).getApplicationContext(),this);
    }

    public void onBtnBackClicked(){
        view.close();
    }

    @Override
    public void onGetImageCoutnSuccess(int length) {
        view.showFramePictures(length);
    }

    @Override
    public void onGetImageCountFailure(Exception ex) {

    }

    @Override
    public void onGetImageSuccess(int index, Bitmap bitmap, String path) {
        view.addPicture(index,bitmap,path);
    }

    @Override
    public void onGetImageFailure(Exception ex) {

    }

    @Override
    public void onPostSuccess() {
        Log.e( "onPostSuccess: ", "");
        view.close();
    }

    @Override
    public void onPostFailure(Exception ex) {

    }
}
