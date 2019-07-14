package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.travel.models.bases.RatingInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.impls.RatingInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.ReviewDetailPresenter;
import com.example.administrator.travel.views.bases.ReviewDetailView;

public class ReviewDetailPresenterImpl implements ReviewDetailPresenter, Listener.OnGetUserInforFinishedListener, Listener.OnGetUserAvatarFinishedListener, Listener.OnGetReviewTourFinishedListener {
    private ReviewDetailView view;
    private RatingInteractor ratingInteractor;
    private UserInteractor userInteractor;

    private String tourId,reviewerId;
    public ReviewDetailPresenterImpl(ReviewDetailView view) {
        this.view = view;
        ratingInteractor = new RatingInteractorImpl();
        userInteractor = new UserInteractorImpl();
    }

    @Override
    public void onViewCreated(Intent intent) {
        Bundle bundle = intent.getExtras();
        tourId = bundle.getString("tourId");
        //review ID = people review ID
        reviewerId = bundle.getString("reviewId");
        userInteractor.getUserInfor(reviewerId,this);
        ratingInteractor.getReview(tourId,reviewerId,this);
    }

    @Override
    public void onButtonLikeClicked() {

    }

    @Override
    public void onButtonDislikeClicked() {

    }

    @Override
    public void onGetUserInforSuccess(UserInformation user) {
        view.showReviewerName(user.name);
        userInteractor.getUserAvatar(user.id,user.urlAvatar,this);
    }

    @Override
    public void onGetUserAvatarSuccess(String userId, Bitmap avatar) {
        view.showReviewerAvatar(avatar);
    }

    @Override
    public void onGetReviewTourSuccess(Rating rating) {
        view.showReview(tourId,rating);
    }

    @Override
    public void onGetReviewTourFail(Exception ex) {
        view.notify("Tải dữ liệu thất bại vui lòng thử lại");
    }
}
