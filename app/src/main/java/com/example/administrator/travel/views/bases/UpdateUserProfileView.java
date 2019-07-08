package com.example.administrator.travel.views.bases;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.administrator.travel.models.entities.UserInformation;

public interface UpdateUserProfileView {
    void showUserInfor(UserInformation user);

    void showUserAvatar(Bitmap avatar);

    void setEditTextUserNameText(String userName);

    void setEdittextPhoneNumberText(String phoneNumber);

    void setTextViewDOBText(String dob);

    void setImageViewAvatarBitmap(Bitmap avatar);

    void showDialogChooseCameraOrGallery();

    void closeDialogChooseCameraOrGallery();

    void openCamera(int cameraCode);

    void openGallery();

    void notify(String message);

    Context getContext();

    void close(int resultCode);
}
