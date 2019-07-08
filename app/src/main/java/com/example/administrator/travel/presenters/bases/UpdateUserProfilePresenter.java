package com.example.administrator.travel.presenters.bases;

import android.content.Intent;
import android.os.Bundle;

public interface UpdateUserProfilePresenter {
    void onViewCreated(Bundle bundle);

    void onImageViewChangeAvatarClicked();

    void onTextViewOpenCameraClicked();

    void onTextViewOpenGalleryClicked();

    void onEditTextUserNameTypingStoped(String userName);

    void onEditTextPhoneNumberTypingStoped(String phoneNumber);

    void onRadioButtonMaleChecked();

    void onRadioButtonFemaleChecked();

    void onButtonUpdateUserInforClicked();

    void onViewResult(int requestCode, int resultCode, Intent data);

    void onButtonBackClicked();
}
