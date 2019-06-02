package com.example.administrator.travel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.administrator.travel.views.fragments.ActivityFragment;
import com.example.administrator.travel.views.fragments.CompanyContactFragment;
import com.example.administrator.travel.views.fragments.NearbyFragment;
import com.example.administrator.travel.views.fragments.ReviewFragment;
import com.example.administrator.travel.views.fragments.TourDetailFragment;
import com.example.administrator.travel.views.fragments.TourStartFragment;

/**
 * Created by Administrator on 24/12/2018.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;
    Boolean isMyTour, isCompany;

    public Pager(FragmentManager fm, int tabCount, Boolean isMyTour, Boolean isCompany) {
        super(fm);
        this.tabCount = tabCount;
        this.isMyTour = isMyTour;
        this.isCompany = isCompany;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TourDetailFragment fragment = new TourDetailFragment();
                return fragment;
            case 1:
                if (isMyTour && !isCompany) {
                    ActivityFragment fragment1 = new ActivityFragment();
                    return fragment1;
                } else {
                    TourStartFragment fragment1 = new TourStartFragment();
                    return fragment1;
                }


            case 2:

                if (isMyTour) {
                    if (isCompany) {
                        CompanyContactFragment fragment2 = new CompanyContactFragment();
                        return fragment2;
                    } else {
                        NearbyFragment fragment2 = new NearbyFragment();
                        return fragment2;
                    }
                } else {
                    if (isCompany) {
                        ReviewFragment fragment2 = new ReviewFragment();
                        return fragment2;
                    } else {
                        CompanyContactFragment fragment2 = new CompanyContactFragment();
                        return fragment2;
                    }
                }
            default:

                ReviewFragment fragment3 = new ReviewFragment();
                return fragment3;

        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}