package com.example.administrator.travel.presenters.bases;

import android.os.Bundle;

public interface AddTourStartDatePresenter {
    void onViewCreated(Bundle bundle);

    void onButtonAddTourStartDateClick();

    void onDateClick(Long time, int year, int month, int day);
}
