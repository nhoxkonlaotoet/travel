package com.example.administrator.travel.presenters.bases;

/**
 * Created by Admin on 5/6/2019.
 */

public interface NewsFeedPresenter {
    void onViewCreated();

    void onViewResumed();

    void onViewPaused();

    void onTourItemClicked(String tourId, String owner);

    void onItemSpinnerOriginSelected(int pos);

    void onItemSpinnerDestinationSelected(int pos);
}
