package com.example.administrator.travel.presenters.impls;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.bases.RatingInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.impls.RatingInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.TourRatingPresenter;
import com.example.administrator.travel.views.ReviewView;
import com.example.administrator.travel.views.activities.ReviewDetailActivity;
import com.example.administrator.travel.views.fragments.ReviewFragment;

import java.util.List;

/**
 * Created by Administrator on 25/12/2018.
 */

public class TourRatingPresenterImpl implements TourRatingPresenter,
        Listener.OnCheckRatedFinishedListener,
        Listener.OnGetReviewsTourFinishedListener, Listener.OnGetRatingTourFinishedListener,
        Listener.OnRateTourFinishedListener, Listener.OnGetUserNameFinishedListener, Listener.OnGetUserAvatarFinishedListener {
    ReviewView view;
    RatingInteractor ratingInteractor;
    UserInteractor userInteractor;

    Boolean isCollapsed = true, rated = false;

    String tourId;
    float rating = 0f;

    public TourRatingPresenterImpl(ReviewView view) {
        this.view = view;
        ratingInteractor = new RatingInteractorImpl();
        userInteractor = new UserInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        tourId = bundle.getString("tourId");
        view.disableRatingBar();
        String userId = userInteractor.getUserId(view.getContext());
        Boolean isMyTour = bundle.getBoolean("mytour");
        if (!isMyTour)
            view.disableRatingBar();

        ratingInteractor.checkRated(tourId, userId, this);
        ratingInteractor.getRating(tourId, this);
        ratingInteractor.getReviews(tourId, this);
    }

    @Override
    public void onBtnCollapseClicked() {
        if (isCollapsed) {
            isCollapsed = false;
            view.hideRatingBar();
        } else {
            isCollapsed = true;
            view.showRatingBar();
        }
    }

    @Override
    public void onBtnSendReviewClicked(float rate, String content, List<Bitmap> lstImage) {
        Context context = ((ReviewFragment) view).getActivity().getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("dataLogin", context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("AuthID", "none");
        Rating rating = new Rating(rate, "", lstImage.size(), content);
        rating.ratingPeopleId = userId;
        ratingInteractor.rateTour(tourId, rating, lstImage, this);

    }

    @Override
    public void OnRatingBarTouched(float value) {
        this.rating = value;
        view.showDialog();
    }

    @Override
    public void onGetImageResult() {
        view.addImage();
    }

    @Override
    public void onBtnCancelClicked() {

    }

    @Override
    public void onImageAddClicked() {
        view.gotoGallary();
    }

    @Override
    public void onListviewReviewItemClicked(String reviewId) {
        if (view.getContext() != null) {
            Intent intent = new Intent(view.getContext(), ReviewDetailActivity.class);
            intent.putExtra("reviewId", reviewId);
            view.gotoReviewDetailActivity(intent);
        }
    }

    @Override
    public void onGetReviewTourSuccess(List<Rating> ratingList) {
        view.showReviews(ratingList);
        for (int i = 0; i < ratingList.size(); i++) {
            userInteractor.getUserName(ratingList.get(0).ratingPeopleId, this, i);
            userInteractor.getUserAvatar(ratingList.get(0).ratingPeopleId, this, i);
        }
    }

    @Override
    public void onGetReviewTourFail(Exception ex) {
        view.notifyGetReviewsFailure(ex);
    }

    @Override
    public void onCheckRatedTrue() {
        view.disableRatingBar();
    }

    @Override
    public void onCheckRatedFalse() {
        view.enableRatingBar();
    }

    @Override
    public void onCheckRatedError(Exception ex) {
        view.notifyGetRatingFailure(ex);
        view.disableRatingBar();
    }

    @Override
    public void onRateTourSuccess() {
        view.notifyRateSuccess();
        view.closeDialog();
    }

    @Override
    public void onRateTourFail(Exception ex) {
        view.notifyRateFailure(ex);
        view.closeDialog();
    }

    @Override
    public void onGetUserNameSuccess(String userId, String name, int pos) {
        view.updateUserName(name, pos);
    }

    @Override
    public void onGetUserAvatarFinishedListener(String userId, Bitmap avatar, int pos) {
        view.updateUserAvatar(avatar, pos);
    }

    @Override
    public void onGetRatingTourSuccess(float value, long count) {
        view.showRating(value, count);
    }

    @Override
    public void onGetRatingTourFail(Exception ex) {
        view.notifyGetRatingFailure(ex);
    }
}
