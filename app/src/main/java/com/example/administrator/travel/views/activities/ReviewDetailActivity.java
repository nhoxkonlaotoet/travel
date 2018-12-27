package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;

public class ReviewDetailActivity extends AppCompatActivity {
    RelativeLayout layoutImageContainer;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getApplicationContext();
        setContentView(R.layout.activity_review_detail);
        layoutImageContainer =  findViewById(R.id.layoutImageContainer);
        showReviewImages(null,7);
    }

    public void showReviewImages(Bitmap[] bitmaps, int n){
        int screenWidth;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int imgvHeight, imgvWidth;
        imgvHeight=screenWidth/4-10;
        imgvWidth=screenWidth/3-10;


        ViewGroup.LayoutParams params = layoutImageContainer.getLayoutParams();
        params.height =( imgvHeight+10) * ((n/3)+1);
        layoutImageContainer.setLayoutParams(params);
        ImageView imgv[] = new ImageView[n];
        for(int i=0;i<n;i++) {
            imgv[i]=new ImageView(context);
            imgv[i].setScaleType(ImageView.ScaleType.FIT_XY);
            imgv[i].setImageResource(R.drawable.tour);
            layoutImageContainer.addView(imgv[i],imgvWidth,imgvHeight);
            imgv[i].setX((screenWidth/3)*(i%3));
            imgv[i].setY(((imgvHeight+10)*(i/3)));
        }
    }
}
