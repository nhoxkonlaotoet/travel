package com.example.administrator.travel.presenters.bases;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Admin on 5/26/2019.
 */

public interface BookTourPresenter {
    void onViewCreated(Bundle bundle);

    void onEtxtNumberOfAdultTypingStoped(int numberOfAdult);

    void onEtxtNumberOfChildrenTypingStoped(int numberOfChildren);

    void onEtxtNumberOfBabyTypingStoped(int numberOfBaby);

    void onButtonNextClicked();

    void onViewResult(int requestCode, int resultCode, Intent data);

}
