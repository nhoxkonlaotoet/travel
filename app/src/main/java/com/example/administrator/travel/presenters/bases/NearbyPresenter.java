package com.example.administrator.travel.presenters.bases;

import com.example.administrator.travel.models.entities.place.nearby.Nearby;

/**
 * Created by Admin on 4/25/2019.
 */

public interface NearbyPresenter {
    void onViewCreated();

    void onListViewNearbyScrollBottom();

    void onSelectItemSpinnerPlaceType(int index);

    void onNearbyItemClicked(Nearby nearby);
}
