package com.example.administrator.travel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.travel.views.fragments.ContactFragment;
import com.example.administrator.travel.views.fragments.MapFragment;
import com.example.administrator.travel.views.fragments.NearbyFragment;
import com.example.administrator.travel.views.fragments.ReviewFragment;
import com.example.administrator.travel.views.fragments.StatusCommunicationFragment;
import com.example.administrator.travel.views.fragments.TourDetailFragment;
import com.example.administrator.travel.views.fragments.TourStartFragment;

/**
 * Created by Administrator on 24/12/2018.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;
    Boolean isMyTour;
    public Pager(FragmentManager fm, int tabCount, Boolean isMyTour){
        super(fm);
        this.tabCount=tabCount;
        this.isMyTour=isMyTour;
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
                ReviewFragment fragment3 = new ReviewFragment();
                return fragment3;


        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}