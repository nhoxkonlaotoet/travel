package com.example.administrator.travel.presenters.bases;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Admin on 4/15/2019.
 */

public interface PostPresenter {
    void onViewCreated(Bundle bundle);

    void onButtonSendClicked(String content);

    void onButtonPictureClicked();

    void onButtonMarkLocationClicked();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onPictureItemClicked(View view, Bitmap image);

    void onEditTextContentClicked();

    void onButtonBackClicked();
}
