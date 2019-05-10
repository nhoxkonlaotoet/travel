package com.example.administrator.travel.views.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.adapter.ReviewAdapter;
import com.example.administrator.travel.models.entities.Rating;
import com.example.administrator.travel.presenters.impls.TourRatingPresenterImpl;
import com.example.administrator.travel.views.ReviewView;
import com.example.administrator.travel.views.activities.ReviewDetailActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment implements ReviewView {
    final static int REQUEST_IMAGE = 102;
    //view
    TextView txtRating, txtNumberofRating;
    RatingBar ratingBarReview;
    ListView lstvReview;
    Button btnCollapse;
    RelativeLayout layoutImage;
    Dialog dialog;
    LinearLayout layoutRating;
    ImageView imgvAdd;

    TourRatingPresenterImpl presenter;
    ReviewAdapter adapter;

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
        setBtnCollapseClick();
        setListviewReviewItemClick();
        presenter = new TourRatingPresenterImpl(this);
        Bundle bundle = getActivity().getIntent().getExtras();
        presenter.onViewCreated(bundle);
        initDialog();
        setOnRatingbarReviewChange();

    }

    void setOnRatingbarReviewChange() {
        ratingBarReview.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                presenter.OnRatingBarChanged(ratingBarReview.getRating());
            }
        });
    }

    void initDialog() {
        {
            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_create_review);
            dialog.setTitle("Viết dánh giá tour");
            layoutImage = dialog.findViewById(R.id.layoutImage);
            final EditText txtContent = dialog.findViewById(R.id.edittxtContent);
            Button btnSendReview = dialog.findViewById(R.id.btnSendReview);
            Button btnCancel =  dialog.findViewById(R.id.btnCancel);
            imgvAdd = dialog.findViewById(R.id.imgvAdd);
            btnSendReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onBtnSendReviewClicked();
                }
            });
            imgvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onImageAddClicked();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.cancel();
                }
            });
            txtContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.onEditTextContentClicked();
                }
            });
        }
    }

    void mapping() {
        txtRating = getActivity().findViewById(R.id.txtRating);
        txtNumberofRating = getActivity().findViewById(R.id.txtNumberofRating);
        ratingBarReview = getActivity().findViewById(R.id.ratingBarReview);
        lstvReview = getActivity().findViewById(R.id.lstvReview);
        btnCollapse = getActivity().findViewById(R.id.btnCollapse);
        layoutRating = getActivity().findViewById(R.id.layoutRating);
    }

    void setListviewReviewItemClick() {
        lstvReview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.onListviewReviewItemClicked(view.getTag().toString());
            }
        });
    }

    void setBtnCollapseClick() {
        btnCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBtnCollapseClicked();
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
       presenter.onViewResult(requestCode, resultCode, data);
    }

    @Override
    public void showDialog() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }



    @Override
    public void closeDialog() {
        if (dialog.isShowing())
            dialog.cancel();
    }

    @Override
    public void showRating(float rating, long numberofRating) {
        ratingBarReview.setRating(rating);
        txtRating.setText(String.valueOf(rating));
        txtNumberofRating.setText("(" + String.valueOf(numberofRating) + ") lượt");
    }

    @Override
    public void notifyGetRatingFailure(Exception ex) {
        if (getContext() != null)
            Toast.makeText(getContext(), "Không thể lấy được đánh giá  " + ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showReviews(List<Rating> lstReview) {
        if (getContext() != null) {
            adapter = new ReviewAdapter(getContext(), lstReview);
            lstvReview.setAdapter(adapter);
        }
    }

    @Override
    public void notifyGetReviewsFailure(Exception ex) {
        if (getContext() != null)
            Toast.makeText(getContext(), "Không thể lấy được bài đánh giá " + ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateUserName(String username, int pos) {
        if (adapter != null)
            adapter.updateUserName(username, pos);
    }

    @Override
    public void updateUserAvatar(Bitmap avatar, int pos) {
        if (adapter != null)
            adapter.updateUserAvatar(avatar, pos);
    }

    @Override
    public void notifyRateSuccess() {
        Toast.makeText(getContext(), "Cảm ơn bạn đã đánh giá", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyRateFailure(Exception ex) {
        if (getContext() != null)
            Toast.makeText(getContext(), "Đánh giá tour thất bại " + ex.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRatingBar() {
        layoutRating.setVisibility(View.VISIBLE);
        btnCollapse.setText("^");
    }

    @Override
    public void hideRatingBar() {
        layoutRating.setVisibility(View.GONE);
        btnCollapse.setText(">");
    }

    @Override
    public void gotoReviewDetailActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void enableRatingBar() {
        ratingBarReview.setEnabled(true);
    }

    @Override
    public void disableRatingBar() {
        ratingBarReview.setEnabled(false);

    }

    @Override
    public void gotoPostActivity(Intent intent, int requestCode) {
        startActivityForResult(intent,requestCode);

    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}
