package com.example.administrator.travel.presenters.bases;

import android.os.Bundle;

/**
 * Created by Admin on 5/26/2019.
 */

public interface BookTourPresenter {
    void onViewCreated(Bundle bundle);

    void onButtonAcceptClicked();

    void onButtonCancelClicked();

    void onButtonDecreaseAdultClicked();

    void onButtonIncreaseAdultClick();

    void onButtonDecreaseChildrenClicked();

    void onButtonIncreaseChildrenClicked();

    void onButtonDecreaseBabyClicked();

    void onButtonIncreaseBabyClicked();
}
