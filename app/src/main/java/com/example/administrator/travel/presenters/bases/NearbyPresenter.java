package com.example.administrator.travel.presenters.bases;

/**
 * Created by Admin on 4/25/2019.
 */

public interface NearbyPresenter {
    void onViewCreated();

    void onListViewNearbyScrollBottom();

    void onSelectItemSpinnerPlaceType(int index);
}
