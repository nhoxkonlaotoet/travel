package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.example.administrator.travel.adapter.Pager;
import com.example.administrator.travel.adapter.SectionsPagerAdapter;
import com.example.administrator.travel.adapter.SlideTourImageAdapter;
import com.example.administrator.travel.models.OnTransmitMyLocationFinishedListener;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.bases.TourPresenter;
import com.example.administrator.travel.presenters.impls.TourPresenterImpl;
import com.example.administrator.travel.views.TourView;

public class TourActivity extends AppCompatActivity implements TourView{
    Toolbar toolbar;
    ViewPager vpTourImage, vpContainer;
    TabLayout tablayoutTour;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ActionBar actionBar;
    TourPresenter presenter;
    Pager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        mapping();
        presenter = new TourPresenterImpl(this);
        Bundle bundle = getIntent().getExtras();

        setSupportActionBar(toolbar);
        actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        presenter.onViewCreated(bundle);

    }

    void mapping(){
        toolbar = findViewById(R.id.toolbar);
        vpTourImage = findViewById(R.id.vpTourImage);
        toolbar.bringToFront();
        tablayoutTour = findViewById(R.id.tablayoutTour);
        vpContainer = findViewById(R.id.vpTabContainer);
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
    public void addTabLayoutTab(String tabText){
        tablayoutTour.addTab(tablayoutTour.newTab().setText(tabText));
    }


    @Override
    public void showTourImages(Bitmap[] images) {
        int n= images.length;
        SlideTourImageAdapter adapter = new SlideTourImageAdapter(images, n, this);
        vpTourImage.setAdapter(adapter);
    }

    @Override
    public void closebyTourFinished() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void hideImagePanel() {
        vpTourImage.setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.toolbar);
        tablayoutTour.setLayoutParams(params);
    }

    @Override
    public void setActionbarTransparent() {
        actionBar.setBackgroundDrawable(getResources().getDrawable (R.color.transparent));
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public void initVpContainer(int tabCount, boolean isMyTour, boolean isCompany) {
        vpContainer.setOffscreenPageLimit(1);
        //   tablayoutTour.setupWithViewPager(vpTabContainer);
        pager = new Pager(getSupportFragmentManager(), tabCount,isMyTour,isCompany);
        vpContainer.setAdapter(pager);
        setOnVpContainerChangePage();
        setOnTabLayoutSelected();
    }

    @Override
    public Context getContext(){
        return this;
    }

    @Override
    public void notifyTourFinished() {
        Toast.makeText(this, getResources().getString(R.string.notify_tour_finished), Toast.LENGTH_SHORT).show();
    }

    void setOnVpContainerChangePage(){
        vpContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tablayoutTour.setScrollPosition(position, 0, true);
                tablayoutTour.setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    void setOnTabLayoutSelected(){
        tablayoutTour.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpContainer.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
