package com.example.administrator.travel.presenters.bases;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Admin on 5/26/2019.
 */

public interface ActivityPresenter {
    void onViewCreated(Bundle bundle);
    void onButtonMapClicked();
    void onEditTextContentClicked();
    void onGetViewResult(Intent intent);

}
