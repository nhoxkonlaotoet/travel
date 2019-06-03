package com.example.administrator.travel.presenters.bases;

import android.os.Bundle;

/**
 * Created by Admin on 6/2/2019.
 */

public interface SearchTourPresenter {
    void onViewCreated(Bundle bundle);

    void onTextSearchChanged(String keyword);

    void onButtonSearchClicked();

    void onItemCityClicked(String cityId);

    void onItemTourClicked(String tourId, String owner);

}
