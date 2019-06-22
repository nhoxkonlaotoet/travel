package com.example.administrator.travel.views.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.ReviewAdapter;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.presenters.impls.TourRatingPresenterImpl;
import com.example.administrator.travel.views.activities.PostActivity;
import com.example.administrator.travel.views.activities.ReviewDetailActivity;
import com.example.administrator.travel.views.bases.ReviewView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment implements ReviewView, ReviewAdapter.ItemClickListener {
    //view
    TextView txtRating, txtNumberofRating, txtMyName, txtMyRatingDate, txtMyReviewContent, txtEditMyReview;
    RatingBar ratingBarRating, ratingBarRateTour, ratingBarMyRating;
    ProgressBar progressBar5Star, progressBar4Star, progressBar3Star, progressBar2Star, progressBar1Star;
    LinearLayout layoutRateTour, layoutMyReview;
    RecyclerView recyclerViewReview;
    RelativeLayout btnShareFacebook;
    ImageView imgvMyAvatar;
    TourRatingPresenterImpl presenter;
    ReviewAdapter reviewAdapter;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapping();
        setOnRatingbarReviewChange();
        setButtonShareFacebookClick();
        presenter = new TourRatingPresenterImpl(this);
        Bundle bundle = getActivity().getIntent().getExtras();
        presenter.onViewCreated(bundle);

    }

    private void mapping() {
        txtRating = getActivity().findViewById(R.id.txtRating);
        txtNumberofRating = getActivity().findViewById(R.id.txtNumberofRating);
        txtMyName = getActivity().findViewById(R.id.txtMyName);
        txtMyRatingDate = getActivity().findViewById(R.id.txtMyRatingDate);
        txtMyReviewContent = getActivity().findViewById(R.id.txtMyReviewContent);
        txtEditMyReview = getActivity().findViewById(R.id.txtEditMyReview);
        ratingBarRating = getActivity().findViewById(R.id.ratingBarRating);
        ratingBarRateTour = getActivity().findViewById(R.id.ratingBarRateTour);
        ratingBarMyRating = getActivity().findViewById(R.id.ratingBarMyRating);
        progressBar5Star = getActivity().findViewById(R.id.progressBar5Star);
        progressBar4Star = getActivity().findViewById(R.id.progressBar4Star);
        progressBar3Star = getActivity().findViewById(R.id.progressBar3Star);
        progressBar2Star = getActivity().findViewById(R.id.progressBar2Star);
        progressBar1Star = getActivity().findViewById(R.id.progressBar1Star);
        layoutRateTour = getActivity().findViewById(R.id.layoutRateTour);
        layoutMyReview = getActivity().findViewById(R.id.layoutMyReview);
        btnShareFacebook = getActivity().findViewById(R.id.btnShareFacebook);
        imgvMyAvatar = getActivity().findViewById(R.id.imgvMyAvatar);
        recyclerViewReview = getActivity().findViewById(R.id.recyclerViewReview);
    }

    private void setOnRatingbarReviewChange() {
        ratingBarRateTour.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                presenter.OnRatingBarChanged(ratingBarRateTour.getRating());
            }
        });
    }


    private void setButtonShareFacebookClick() {
        btnShareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonShareFacebookClicked();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        presenter.onViewResult(requestCode, resultCode, data);
    }


    @Override
    public void showTourRating(HashMap<Integer, Integer> startCountMap) {
        float rating = 0;
        int numberOfRating = 0;
        for (int i = 1; i <= 5; i++) {
            rating += i * startCountMap.get(i);
            numberOfRating += startCountMap.get(i);
        }
        rating /= numberOfRating;
        ratingBarRating.setRating(rating);
        txtRating.setText(String.valueOf(rating));
        txtNumberofRating.setText(String.valueOf(numberOfRating));
        int progress = (startCountMap.get(1) / numberOfRating) * 100;
        progressBar1Star.setProgress(progress);
        progress = (startCountMap.get(2) / numberOfRating) * 100;
        progressBar2Star.setProgress(progress);
        progress = (startCountMap.get(3) / numberOfRating) * 100;
        progressBar3Star.setProgress(progress);
        progress = (startCountMap.get(4) / numberOfRating) * 100;
        progressBar4Star.setProgress(progress);
        progress = (startCountMap.get(5) / numberOfRating) * 100;
        progressBar5Star.setProgress(progress);
    }


    @Override
    public void showReviews(List<Rating> reviewList) {
        reviewAdapter = new ReviewAdapter(getActivity(), reviewList);
        reviewAdapter.setClickListener(this);
        recyclerViewReview.setAdapter(reviewAdapter);

    }

    @Override
    public void showMyReview(Rating myRating) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(myRating.ratingTime);
        txtMyRatingDate.setText(dateFormat.format(date));
        if (myRating.content != null)
            txtMyReviewContent.setText(myRating.content);
        ratingBarMyRating.setRating(myRating.rating);

    }

    @Override
    public void showLayoutMyReview() {
        layoutMyReview.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayoutMyReview() {
        layoutMyReview.setVisibility(View.GONE);
    }

    @Override
    public void updateMyName(String name) {
        txtMyName.setText(name);
    }

    @Override
    public void updateMyAvatar(Bitmap avatar) {
        imgvMyAvatar.setImageBitmap(avatar);
    }


    @Override
    public void showLayoutRateTour() {
        layoutRateTour.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLayoutRateTour() {
        layoutRateTour.setVisibility(View.GONE);
    }

    @Override
    public void gotoReviewDetailActivity(String reviewId) {
        Intent intent = new Intent(getActivity(), ReviewDetailActivity.class);
        intent.putExtra("reviewId", reviewId);
        startActivity(intent);
    }

    @Override
    public void enableRatingBar() {
        ratingBarRateTour.setEnabled(true);
    }

    @Override
    public void disableRatingBar() {
        ratingBarRateTour.setEnabled(false);
    }

    @Override
    public void gotoPostActivity(String tourId, float rating, int requestCode) {
        Intent intent = new Intent(getActivity(), PostActivity.class);
        intent.putExtra("rating", rating);
        intent.putExtra("tourId", tourId);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void notify(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public Context getAppContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void onReviewItemClick(View view, String reviewId) {
        presenter.onReviewItemClicked(reviewId);
    }

    @Override
    public void onButtonLikeReviewItemClick(View view, String reviewId, boolean pressed) {
        presenter.onButtonLikeReviewItemClicked(reviewId, pressed);
    }

    @Override
    public void onButtonDislikeReviewItemClick(View view, String reviewId, boolean pressed) {
        presenter.onButtonDislikeReviewItemClicked(reviewId, pressed);
    }
}
