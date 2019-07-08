package com.example.administrator.travel.presenters.impls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;

import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.impls.UserInteractorImpl;
import com.example.administrator.travel.models.listeners.Listener;
import com.example.administrator.travel.presenters.bases.UpdateUserProfilePresenter;
import com.example.administrator.travel.views.bases.UpdateUserProfileView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class UpdateUserProfilePresenterImpl implements UpdateUserProfilePresenter, Listener.OnGetUserInforFinishedListener, Listener.OnGetUserAvatarFinishedListener, Listener.OnUpdateAvatarFinishedListener, Listener.OnUpdateUserInforFinishedListener {
    private final static int REQUEST_CODE_CAMERA = 897;


    UpdateUserProfileView view;
    UserInteractor userInteractor;

    UserInformation user;

    public UpdateUserProfilePresenterImpl(UpdateUserProfileView view) {
        this.view = view;
        userInteractor = new UserInteractorImpl();
        user = new UserInformation();
    }

    @Override
    public void onViewCreated(Bundle bundle) {
        String myId = userInteractor.getUserId();
        userInteractor.getUserInfor(myId, this);
    }

    @Override
    public void onImageViewChangeAvatarClicked() {
        view.showDialogChooseCameraOrGallery();
    }

    @Override
    public void onTextViewOpenCameraClicked() {
        view.closeDialogChooseCameraOrGallery();
        view.openCamera(REQUEST_CODE_CAMERA);
    }

    @Override
    public void onTextViewOpenGalleryClicked() {
        view.closeDialogChooseCameraOrGallery();
        view.openGallery();
    }

    @Override
    public void onEditTextUserNameTypingStoped(String userName) {
        user.name = userName.trim();
        view.setEditTextUserNameText(user.name);
    }

    @Override
    public void onEditTextPhoneNumberTypingStoped(String phoneNumber) {
        String rightPhone = "";
        for (int i = 0; i < phoneNumber.length(); i++) {
            char charAti = phoneNumber.charAt(i);
            if (charAti == '+' || (charAti >= '0' && charAti <= '9'))
                rightPhone += charAti;
        }
        user.sdt = rightPhone;
        view.setEdittextPhoneNumberText(user.sdt);
    }

    @Override
    public void onRadioButtonMaleChecked() {
        user.isMale = true;
    }

    @Override
    public void onRadioButtonFemaleChecked() {
        user.isMale = false;
    }

    @Override
    public void onButtonUpdateUserInforClicked() {
        userInteractor.updateUserInfor(user, this);
    }

    @Override
    public void onViewResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode==RESULT_OK) {
            Bitmap avatar = (Bitmap) data.getExtras().get("data");
            userInteractor.updateAvatar(userInteractor.getUserId(), avatar, this);
            view.setImageViewAvatarBitmap(avatar);
        }
    }

    @Override
    public void onButtonBackClicked() {
        view.close(RESULT_CANCELED);
    }

    @Override
    public void onGetUserInforSuccess(UserInformation user) {
        this.user = user;
        userInteractor.getUserAvatar(user.id, user.urlAvatar, this);
        view.showUserInfor(user);
    }

    @Override
    public void onGetUserAvatarSuccess(String userId, Bitmap avatar) {
        view.showUserAvatar(avatar);
    }

    @Override
    public void onUpdateAvatarSuccess(String avatarUrl) {
        user.urlAvatar = avatarUrl;
    }

    @Override
    public void onUpdateAvatarFail(Exception ex) {

    }

    @Override
    public void onUpdateUserInforSuccess() {
       view.close(RESULT_OK);
    }

    @Override
    public void onUpdateUserInforFail(Exception ex) {

    }
}
