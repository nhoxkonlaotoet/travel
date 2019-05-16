package com.example.administrator.travel.presenters.bases;

import android.os.Bundle;

/**
 * Created by Admin on 4/11/2019.
 */

public interface TourStartPresenter {
    void onViewCreated(Bundle bundle);
    void onTourStartItemClick(String tourStartId);
}
