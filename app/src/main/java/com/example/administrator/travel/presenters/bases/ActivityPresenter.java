package com.example.administrator.travel.presenters.bases;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Admin on 5/26/2019.
 */

public interface ActivityPresenter {
    void onViewCreated(Bundle bundle);

    void onViewResult(int requestCode, int resultCode, Intent data);

    void onTextContentClicked();
    void onGetViewResult(Intent intent);

}
