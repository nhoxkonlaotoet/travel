package com.example.administrator.travel.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.example.administrator.travel.models.OnGetRatingFinishedListener;
import com.example.administrator.travel.models.ReviewInteractor;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.views.ReviewView;
import com.example.administrator.travel.views.fragments.ReviewFragment;

import java.util.List;

/**
 * Created by Administrator on 25/12/2018.
 */

public class ReviewPresenter implements OnGetRatingFinishedListener{
    ReviewView view;
    ReviewInteractor reviewInteractor;
    Boolean isCollapsed = true, rated=false;
    public ReviewPresenter(ReviewView view){
        this.view=view;
        reviewInteractor=new ReviewInteractor();
    }
    public void onViewLoad(String tourId){
        view.disableRatingBar();
        reviewInteractor.checkRated(tourId,((ReviewFragment)view).getActivity(),this);
        reviewInteractor.getRating(tourId,this);
        reviewInteractor.getReviews(tourId,this);
    }

    public void onBtnCollapseClicked(){
        if(isCollapsed)
        {
            isCollapsed=false;
            view.hideRatingBar();
        }
        else
        {
            isCollapsed=true;
            view.showRatingBar();
        }
    }
    public void onBtnSendReviewClicked(String tourId,float rate, String content, List<Bitmap> lstImage){
        Context context = ((ReviewFragment)view).getActivity().getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("dataLogin",context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("AuthID","none");
        Rating rating = new Rating(rate,"",0L,lstImage.size(),content);
        rating.ratingPeopleId=userId;
        reviewInteractor.rate(tourId,rating,lstImage,this);

    }
    public void onRatingBarTouched(){
        view.showDialog();
    }
    public void onGetImageResult(){
        view.addImage();
    }
    public void onBtnCancelClicked(){

    }
    public void onImageAddClicked(){
        view.gotoGallary();
    }
    public void onListviewReviewItemClicked(String reviewId){
        view.gotoReviewActivity(reviewId);
    }

    @Override
    public void onCheckRatedSuccess(boolean rated) {
        this.rated=rated;
        if(rated)
            view.disableRatingBar();
        else
            view.enableRatingBar();

    }

    @Override
    public void onCheckRatedFailure(Exception ex) {

    }

    @Override
    public void onGetRatingSuccess(float rating, long numberofRating) {
        view.showRating(rating,numberofRating);
    }

    @Override
    public void onGetRatingFailure(Exception ex) {
        view.notifyGetRatingFailure(ex);
    }

    @Override
    public void onGetReviewsSuccess(List<Rating> lstReview) {
        view.showReviews(lstReview);
    }

    @Override
    public void onGetReviewsFailure(Exception ex) {

    }


    @Override
    public void onRateSuccess() {
        view.notifyRateSuccess();
        view.closeDialog();
    }

    @Override
    public void onRateFailure(Exception ex) {
        view.notifyRateFailure(ex);
        view.closeDialog();
    }
}
