package com.example.administrator.travel.presenters.bases;

import android.os.Bundle;

/**
 * Created by Admin on 4/5/2019.
 */

public interface TourDetailPresenter {
    void onViewCreated(Bundle bundle);
    void onSelectItemSpinnerDays(String day);
    void onScheduleItemClicked(String scheduleId);

    void onButtonAddTourStartClick();
}
