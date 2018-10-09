package com.example.administrator.travel.activities;

import android.content.Context;
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
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.activities.TourActivity;
import com.example.administrator.travel.fragments.MapFragment;
import com.example.administrator.travel.fragments.SelectTourFragment;
import com.example.administrator.travel.fragments.TourDetailFragment;

public class TourActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager vpTourImage, vpContainer;
    TextView txt;
    TabLayout tablayoutTour;

    SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        toolbar = findViewById(R.id.toolbar);
        vpTourImage = findViewById(R.id.vpTourImage);
        txt = findViewById(R.id.txtTourImage);
        toolbar.bringToFront();
        tablayoutTour = findViewById(R.id.tablayoutTour);

        vpContainer = findViewById(R.id.vpTabContainer);
     //   tablayoutTour.setupWithViewPager(vpTabContainer);

        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        SlideTourImageAdapter adapter = new SlideTourImageAdapter(this);
        vpTourImage.setAdapter(adapter);



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

    void setSize(View view, int height)
    {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if(height>0)
            params.height -=height;
        view.setLayoutParams(params);

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
                case 1 :
                    SelectTourFragment fragment = new SelectTourFragment();
                    return fragment;
                case 3:
                    MapFragment fragment3 = new MapFragment();
                    return fragment3;
                default :
                    TourDetailFragment fragment2 = new TourDetailFragment();
                    return fragment2;
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
        Context context;
        ImageView imgv;
        TextView txt;
        public SlideTourImageAdapter(Context context)
        {
            this.context=context;
        }
        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (RelativeLayout) object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_tour_image, null);

            // imgv=container.findViewById(R.id.imgvTourImage);
            txt = view.findViewById(R.id.txtTourImage);
            Log.e("pos",position+"");
            txt.setText(position+"");

            container.addView(view);
            return view;
        }
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout)object);
        }
    }

}
