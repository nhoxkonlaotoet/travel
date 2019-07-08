package com.example.administrator.travel.presenters.bases;

import android.content.Intent;
import android.os.Bundle;

public interface UserProfilePresenter {
    void onViewCreated(Bundle bundle);

    void onButtonEditInforClicked();

    void onViewResult(int requestCode, int resultCode, Intent data);
}
