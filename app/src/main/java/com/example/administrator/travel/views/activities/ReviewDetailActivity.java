package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.ReviewImageAdapter;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.models.entities.place.detail.Review;
import com.example.administrator.travel.presenters.bases.ReviewDetailPresenter;
import com.example.administrator.travel.presenters.impls.ReviewDetailPresenterImpl;
import com.example.administrator.travel.views.bases.ReviewDetailView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewDetailActivity extends AppCompatActivity implements ReviewDetailView, ReviewImageAdapter.ItemClickListener {
    private RecyclerView recyclerViewReviewImage;
    private TextView txtReviewerName, txtReviewContent, txtReviewTime;
    private RatingBar ratingBarReview;
    private Toolbar toolbar;
    private ImageView imgvReviewerAvatar;

    private ReviewDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        mapping();
        setActionbar();

        presenter = new ReviewDetailPresenterImpl(this);
        presenter.onViewCreated(getIntent());
    }

    private void mapping() {
        recyclerViewReviewImage = findViewById(R.id.recyclerViewReviewImage);
        txtReviewerName = findViewById(R.id.txtReviewerName);
        txtReviewContent = findViewById(R.id.txtReviewContent);
        txtReviewTime = findViewById(R.id.txtReviewTime);
        toolbar = findViewById(R.id.toolbar);
        imgvReviewerAvatar = findViewById(R.id.imgvReviewerAvatar);
        ratingBarReview = findViewById(R.id.ratingBarReview);
    }

    private void setActionbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar;
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);

    }

    @Override
    public void showReviewerName(String name) {
        if (name != null)
            txtReviewerName.setText(name);
    }

    @Override
    public void showReviewerAvatar(Bitmap avatar) {
        if (avatar != null)
            imgvReviewerAvatar.setImageBitmap(avatar);
    }

    @Override
    public void showReview(String tourId, Rating review) {
        txtReviewContent.setText(review.content);
        Date date = new Date(review.ratingTime);
        ratingBarReview.setRating(review.rating);
        ReviewImageAdapter adapter = new ReviewImageAdapter(tourId,review.ratingPeopleId,review.numberOfImages);
        adapter.setClickListener(this);
        recyclerViewReviewImage.setAdapter(adapter);

    }



    @Override
    public void notify(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onReviewImageItemClick(View view, String tourId, String reviewerId, int index) {

    }
}
