package com.example.administrator.travel.views.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.presenters.TourPresenter;
import com.example.administrator.travel.views.TourView;
import com.example.administrator.travel.views.fragments.ContactFragment;
import com.example.administrator.travel.views.fragments.MapFragment;
import com.example.administrator.travel.views.fragments.NearbyFragment;
import com.example.administrator.travel.views.fragments.StatusCommunicationFragment;
import com.example.administrator.travel.views.fragments.TourDetailFragment;
import com.example.administrator.travel.views.fragments.TourStartFragment;

public class TourActivity extends AppCompatActivity implements TourView {
    Toolbar toolbar;
    ViewPager vpTourImage, vpContainer;
    TabLayout tablayoutTour;
    Boolean isMyTour=false;
    SectionsPagerAdapter mSectionsPagerAdapter;
    TourPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        toolbar = findViewById(R.id.toolbar);
        vpTourImage = findViewById(R.id.vpTourImage);
        toolbar.bringToFront();
        tablayoutTour = findViewById(R.id.tablayoutTour);
        presenter = new TourPresenter(this);
        Bundle bundle = getIntent().getExtras();
        String tourId=bundle.getString("tourId");
        presenter.getTourImages(tourId);
        if(bundle.getBoolean("mytour"))
        {
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Chi tiết"));
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Hoạt động"));
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Gần đây"));
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Bản đồ"));
            vpTourImage.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.toolbar);
            tablayoutTour.setLayoutParams(params);
            isMyTour=true;
        }
        else
        {
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Chi tiết"));
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Đặt tour"));
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Liên hệ"));
            tablayoutTour.addTab(tablayoutTour.newTab().setText("Bản đồ"));
        }

        vpContainer = findViewById(R.id.vpTabContainer);
     //   tablayoutTour.setupWithViewPager(vpTabContainer);

        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);




        Pager pager=new Pager(getSupportFragmentManager(),4);

        vpContainer.setAdapter(pager);

        vpContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tablayoutTour.setScrollPosition(position, 0, true);

                tablayoutTour.setSelected(true);

                Log.e( "onPageSelected: ", position+"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    void setSize(View view, int height){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if(height>0)
            params.height -=height;
        view.setLayoutParams(params);

    }

    @Override
    public void showImages(Bitmap[] images, Integer numberofImages) {

        SlideTourImageAdapter adapter = new SlideTourImageAdapter(images,numberofImages,this);
        vpTourImage.setAdapter(adapter);
    }


    public class Pager extends FragmentStatePagerAdapter {

        int tabCount;

        public Pager(FragmentManager fm, int tabCount){
            super(fm);
            this.tabCount=tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    TourDetailFragment fragment = new TourDetailFragment();
                    return fragment;
                case 1 :
                    if(isMyTour) {
                        StatusCommunicationFragment fragment1 = new StatusCommunicationFragment();
                        return fragment1;
                    }else{
                    TourStartFragment fragment1 = new TourStartFragment();
                    return fragment1;}
                case 2:
                    if(isMyTour) {
                        NearbyFragment fragment2 = new NearbyFragment();
                        return fragment2;
                    }
                    else
                    {
                        ContactFragment fragment2 = new ContactFragment();
                        return fragment2;
                    }
                default :
                    MapFragment fragment3 = new MapFragment();
                    return fragment3;


            }

        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tour_detail, container, false);
            return rootView;
        }
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
    public class SlideTourImageAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;
        Bitmap[] images;
        Integer n;
        Context context;
        ImageView imgv;

        public SlideTourImageAdapter(Bitmap[] images,Integer numberofImages,Context context)
        {
            this.images=images;
            this.context=context;
            n=numberofImages;
        }
        @Override
        public int getCount() {
            return n;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (RelativeLayout) object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_tour_image, null);
            imgv=view.findViewById(R.id.imgvTourImage);
            Log.e( "instantiateItem: ",position+"   "+(images[position]==null) );
            imgv.setImageBitmap(images[position]);
            Log.e("pos",position+"");
            container.addView(view);
            return view;
        }
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout)object);
        }
    }

}
