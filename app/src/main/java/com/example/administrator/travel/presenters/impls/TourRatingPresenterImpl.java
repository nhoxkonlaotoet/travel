package com.example.administrator.travel.presenters.impls;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.travel.models.bases.ParticipantInteractor;
import com.example.administrator.travel.models.bases.RatingInteractor;
import com.example.administrator.travel.models.bases.TourInteractor;
import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.Tour;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.impls.ParticipantInteractorImpl;
import com.example.administrator.travel.models.impls.RatingInteractorImpl;
import com.example.administrator.travel.models.impls.TourInteractorImpl;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.TourRatingPresenter;
import com.example.administrator.travel.views.bases.ReviewView;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.facebook.appevents.AppEventsLogger;

import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 25/12/2018.
 */

public class TourRatingPresenterImpl implements TourRatingPresenter, Listener.OnGetReviewsTourFinishedListener,
        Listener.OnRateTourFinishedListener, Listener.OnGetUserInforFinishedListener,
        Listener.OnGetUserAvatarFinishedListener, Listener.OnCheckJoinedTourFinishedListener,
        Listener.OnGetReviewTourFinishedListener, Listener.OnGetTourFinishedListener {
    final static int REQUEST_POST = 101;
    ReviewView view;
    RatingInteractor ratingInteractor;
    UserInteractor userInteractor;
    ParticipantInteractor participantInteractor;
    TourInteractor tourInteractor;
    boolean firstChange = true, joinedTour;
    String tourId, myId;
    float rating = 0f;

    public TourRatingPresenterImpl(ReviewView view) {
        this.view = view;
        ratingInteractor = new RatingInteractorImpl();
        userInteractor = new UserInteractorImpl();
        participantInteractor = new ParticipantInteractorImpl();
        tourInteractor = new TourInteractorImpl();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        tourId = bundle.getString("tourId");
        view.disableRatingBar();
        if (userInteractor.isLogged()) {
            myId = userInteractor.getUserId();
            participantInteractor.checkJoinedTour(myId, tourId, this);
            view.hideLayoutRateTour();
            view.hideLayoutMyReview();
        } else {
            view.hideLayoutRateTour();
            view.hideLayoutMyReview();
        }
        tourInteractor.getTour(tourId, this);
        ratingInteractor.getReviews(tourId, this);
    }

    @Override
    public void OnRatingBarChanged(float value) {
        if (firstChange) {
            firstChange = false;
            return;
        }
        this.rating = value;
        view.gotoPostActivity(tourId,value,REQUEST_POST);
    }

    @Override
    public void onReviewItemClicked(String tourId, String reviewId) {
        view.gotoReviewDetailActivity(tourId, reviewId);
    }

    @Override
    public void onButtonShareFacebookClicked() {
        ShareLinkContent shareContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://travel-76809.web.app/u/reivew/tour/cmt?id=" + tourId + "&uid=" + userInteractor.getUserId()))
                .build();
        ShareDialog shareDialog = new ShareDialog((Activity) view.getContext());
        shareDialog.show(shareContent, ShareDialog.Mode.AUTOMATIC);

    }


    @Override
    public void onViewResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_POST) {
            if (resultCode == RESULT_OK) {
                tourInteractor.getTour(tourId,this);
                ratingInteractor.getReview(tourId,myId,this);
                view.disableRatingBar();
            }
        }
    }

    @Override
    public void onButtonLikeReviewItemClicked(String reviewId, boolean pressed) {
        if (userInteractor.isLogged()) {
            String myId = userInteractor.getUserId();
            if (pressed)
                ratingInteractor.reactReview(tourId, reviewId, myId, true);
            else
                ratingInteractor.removeReactReview(tourId, reviewId, myId);
        }
    }

    @Override
    public void onButtonDislikeReviewItemClicked(String reviewId, boolean pressed) {
        if (userInteractor.isLogged()) {
            String myId = userInteractor.getUserId();
            if (pressed)
                ratingInteractor.reactReview(tourId, reviewId, myId, false);
            else
                ratingInteractor.removeReactReview(tourId, reviewId, myId);
        }
    }

    @Override
    public void onGetReviewsTourSuccess(List<Rating> ratingList) {
        if (userInteractor.isLogged()) {
            String myId = userInteractor.getUserId();
            for (int i = 0; i < ratingList.size(); i++)
                if (ratingList.get(i).ratingPeopleId.equals(myId))
                    ratingList.remove(i);
        }
        view.showReviews(tourId, ratingList);
    }

    @Override
    public void onGetReviewsTourFail(Exception ex) {
        view.notify(ex.getMessage());
    }

    @Override
    public void onRateTourSuccess() {
    }

    @Override
    public void onRateTourFail(Exception ex) {
        view.notify(ex.getMessage());
    }

    @Override
    public void onGetUserInforSuccess(UserInformation user) {
        view.updateMyName(user.name);
        userInteractor.getUserAvatar(user.id, user.urlAvatar, this);

    }

    @Override
    public void onGetUserAvatarSuccess(String userId, Bitmap avatar) {
        view.updateMyAvatar(avatar);
    }

    @Override
    public void onCheckJoinedTourSuccess(Boolean joined) {
        Log.e("checked", myId+ " "+joined);

        joinedTour = joined;
        if (!joined) {
           view.disableRatingBar();
           view.hideLayoutMyReview();
           view.hideLayoutRateTour();
        }
        else {
            view.enableRatingBar();
            view.showLayoutRateTour();
            ratingInteractor.getReview(tourId, myId, this);
        }

    }

    @Override
    public void onCheckJoinedTourFail(Exception ex) {
        Log.e("fail", myId+ " "+ex);

    }

    @Override // my review
    public void onGetReviewTourSuccess(Rating rating) {
        //haven't reviewed yet return null
        if(rating==null || rating.rating==0)
            firstChange=false;
        if (rating == null)
            if (joinedTour) // joined tour and haven't reviewed{
            {
                view.enableRatingBar();
                view.hideLayoutMyReview();
            } else {
                view.disableRatingBar();
                view.hideLayoutMyReview();
                view.hideLayoutRateTour();
            }
        else {
            view.disableRatingBar();
            view.showMyReview(rating);
            view.showLayoutMyReview();
            view.hideLayoutRateTour();
            userInteractor.getUserInfor(userInteractor.getUserId(), this);
        }
    }

    @Override
    public void onGetReviewTourFail(Exception ex) {

    }

    @Override
    public void onGetTourSuccess(Tour tour) {
        if (tour.ratings == null) {
            firstChange = false;
            return;
        }
        HashMap<Integer, Integer> startCountMap = new HashMap<>();
        int oneCount = 0, twoCount = 0, threeCount = 0, fourCount = 0, fiveCount = 0;
        for (Double rating : tour.ratings.values())
            if (rating == 1.0)
                oneCount++;
            else if (rating == 2.0)
                twoCount++;
            else if (rating == 3.0)
                threeCount++;
            else if (rating == 4.0)
                fourCount++;
            else if (rating == 5.0)
                fiveCount++;
        startCountMap.put(1, oneCount);
        startCountMap.put(2, twoCount);
        startCountMap.put(3, threeCount);
        startCountMap.put(4, fourCount);
        startCountMap.put(5, fiveCount);
        view.showTourRating(startCountMap);
    }

    @Override
    public void onGetTourFail(Exception ex) {

    }
}
