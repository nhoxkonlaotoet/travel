package com.example.administrator.travel.presenters.bases;

/**
 * Created by Admin on 5/6/2019.
 */

public interface NewsFeedPresenter {
    void onViewCreated();

    void onItemCityClicked(String cityId);

    void onItemTourClicked(String tourId, String owner);

    void onItemCompanyClick(String companyId);
}
